package contact.messager.activity.view_fragment
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import contact.messager.activity.ChatConversationActivity
import contact.messager.R
import contact.messager.databinding.FragmentConversationBinding
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.`class`.IdConversacionDataUser
import contact.messager.util.adapter.ListConversationAdapter
import contact.messager.util.`class`.User

class ConversationFragment(val fragment: FragmentConversationBinding, val context: Context) {
    val listviewConversation = fragment.listviewConversation
    var listInfo: MutableList<IdConversacionDataUser> = mutableListOf()
    val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    // get mi converacion with users
    fun initConversationFragment() {
        listInfo.clear()
        Firebase.database.reference.child("enganchedChannels/$miId").orderByKey().get().addOnSuccessListener {
            if(it.exists()){
                for(doc in it.children){
                    val info = IdConversacionDataUser(doc.child("channelId").value.toString(), doc.key.toString(), "1", "2", "1")
                    listInfo.add(info)
                    // Log.d("firebase", listInfo.toString() )
                }
               getMoreInfoFromUsers(listInfo.asReversed()){ listInfo ->
                   // click to item in conversation list users
                   listviewConversation.setOnItemClickListener { _, _, i, _ ->
                   // val user = User( listInfo[i].idUser , listInfo[i].name,  listInfo[i].image)
                   //userConversation = user
                   // context.startActivity(Intent(context, ChatConversationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                   }
               }
            } else {
                Toast.makeText(context, context.getString(R.string.dont_have_conversations), Toast.LENGTH_LONG).show()
            }
        }
    }

    // get more info from user with  user id
    private fun getMoreInfoFromUsers(listInfo: MutableList<IdConversacionDataUser>, onComplete: (listInfo: MutableList<IdConversacionDataUser>) -> Unit){
        var currentUser : User
        for(i in 0 until listInfo.size){
            Firebase.database.reference.child( "users/" + listInfo[i].idUser ).get().addOnSuccessListener {
                if (it.exists()){
                    currentUser = it.getValue(User::class.java)!!
                        listInfo[i].name = currentUser.name
                        listInfo[i].image = currentUser.image
                    listviewConversation.adapter = ListConversationAdapter(context, listInfo)
                }
            }
        }
        onComplete(listInfo)
    }

}