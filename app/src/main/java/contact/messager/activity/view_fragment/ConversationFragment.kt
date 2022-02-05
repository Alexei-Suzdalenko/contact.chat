package contact.messager.activity.view_fragment
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import contact.messager.activity.ChatConversationActivity
import contact.messager.databinding.FragmentConversationBinding
import contact.messager.util.adapter.ListConversationAdapter
import contact.messager.util.classes.ChatEnganchedUser
import contact.messager.util.api.ConversationUserFirebase
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.classes.User

class ConversationFragment(val fr: FragmentConversationBinding, val context: Context) {
    var listInfoUser: MutableList<User> = mutableListOf()
    val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    // get mi converacion with users

    fun initConversationFragment() {
        userConversation = null

        ConversationUserFirebase().getListConversation{
            listInfoUser = it
            Log.d("initConversationFragment", "initConversationFragment 000 " + it.toString())
            fr.listviewConversation.adapter = ListConversationAdapter(context, it)


            fr.listviewConversation.setOnItemClickListener { _, _, i, _ ->
                userConversation = listInfoUser[i]
                context.startActivity(Intent(context, ChatConversationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }
    }

}
