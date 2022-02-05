package contact.messager.util.api
import android.annotation.SuppressLint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.activity.ChatConversationActivity
import contact.messager.util.`class`.App
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.`class`.Message
import kotlinx.android.synthetic.main.activity_chat_conversation.*

object AddNewMessageFirestore {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid.toString()

    @SuppressLint("SimpleDateFormat")
    fun addNewMessageFirestore(textMessage: String, activity: ChatConversationActivity, onComplete: (res: String) -> Unit){

           FirebaseFirestore.getInstance()
               .collection("conversation")
               .document("conversation")
               .collection(App.realChannelId!!)
               .document()
               .set(Message(System.currentTimeMillis().toString(), textMessage, "", miId)).addOnCompleteListener {
                   if(it.isSuccessful) onComplete("ok")
               }
    }
}
