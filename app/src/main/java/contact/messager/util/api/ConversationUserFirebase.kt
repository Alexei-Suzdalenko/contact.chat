package contact.messager.util.api
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import contact.messager.util.classes.ChatEnganchedUser
import contact.messager.util.classes.User

class ConversationUserFirebase {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid
    val refConvers = FirebaseDatabase.getInstance().reference.child("enganched_chat/$miId")
    val listIdsConvers = ArrayList<ChatEnganchedUser>()
    val refUsers = FirebaseDatabase.getInstance().reference.child("user")
    val listDataConvers:  MutableList<User> = mutableListOf()

    fun getListConversation(onComplete: (res: MutableList<User>) -> Unit){
        listIdsConvers.clear()

        refConvers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {;}
            override fun onDataChange(snapshot: DataSnapshot) {

                for (ds in snapshot.children) {
                        val conversation: ChatEnganchedUser? = ds.getValue(ChatEnganchedUser::class.java)
                        if (conversation != null ) {
                            conversation.key = ds.key.toString()
                            listIdsConvers.add(conversation)
                        }
                }
                if(listIdsConvers.size > 0){
                    getUserInfoFromEnganchedChannels{
                        onComplete(it)
                    }
                }
            }
        })
    }

    private fun getUserInfoFromEnganchedChannels(onComplete: (res: MutableList<User>) -> Unit){
        var userKey = ""
        listDataConvers.clear()

        for(position in 0 until listIdsConvers.size){
            userKey = listIdsConvers[position].key

            refUsers.child(userKey)
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {; }
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val user: User? = snapshot.getValue(User::class.java)
                        if(user != null){
                            user.id = snapshot.key.toString()
                            listDataConvers.add(user)
                        }
                        onComplete(listDataConvers)
                    }
            })
        }
    }
}




















