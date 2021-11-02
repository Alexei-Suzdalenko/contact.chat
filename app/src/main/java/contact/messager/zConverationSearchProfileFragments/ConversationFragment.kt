package contact.messager.zConverationSearchProfileFragments
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import contact.messager.R
import contact.messager.databinding.FragmentConversationBinding
import contact.messager.util.assets.IdConversacionDataUser
import contact.messager.util.assets.ListConversationAdapter
import contact.messager.util.assets.User

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
                    val info = IdConversacionDataUser(doc.child("channelId").value.toString(), doc.key.toString(), "", "", "")
                    listInfo.add(info)
                    // Log.d("firebase", listInfo.toString() )
                }
               getMoreInfoFromUsers(listInfo.asReversed()){ listInfo ->
                   // click to item in conversation
                   listviewConversation.setOnItemClickListener { parent, view, position, id ->

                   }
               }
            } else {
                Toast.makeText(context, context.getString(R.string.dont_have_conversations), Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{ Toast.makeText(context, "ERROR OCURRIED CONVERSION FRAGMENT, WRITE TO ALEXEI SUZDALENKO", Toast.LENGTH_LONG).show() }
    }

    // get more info from user with  user id
    private fun getMoreInfoFromUsers(listInfo: MutableList<IdConversacionDataUser>, onComplete: (listInfo: MutableList<IdConversacionDataUser>) -> Unit){
        var currentUser : User
        for(i in 0 until listInfo.size){
            Firebase.database.reference.child( "users/" + listInfo[i].idUser ).get().addOnSuccessListener {
                if (it.exists()){
                    currentUser = it.getValue(User::class.java)!!
                        listInfo[i].email = currentUser.email
                        listInfo[i].name = currentUser.name
                        listInfo[i].image = currentUser.image
                    listviewConversation.adapter = ListConversationAdapter(context, listInfo)
                } else { Toast.makeText(context, "ERROR OCURRIED USER DATA, WRITE TO ALEXEI SUZDALENKO", Toast.LENGTH_LONG).show() }
            }
        }
        onComplete(listInfo)
    }

}