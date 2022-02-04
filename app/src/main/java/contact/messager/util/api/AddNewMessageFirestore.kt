package contact.messager.util.api
import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.activity.ChatConversationActivity
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.`class`.Message
import kotlinx.android.synthetic.main.activity_chat_conversation.*

object AddNewMessageFirestore {
    @SuppressLint("SimpleDateFormat")
    fun addNewMessageFirestore(textMessage: String, typeMessage: String, activity: ChatConversationActivity){

        if(typeMessage == "text"){
        //   FirebaseFirestore.getInstance()
        //       .collection("conversation")
        //       .document("conversation")
        //       .collection(ChatConversationActivity.realChannelId!!)
        //       .document()
        //       .set(Message(System.currentTimeMillis().toString(), textMessage, "", ChatConversationActivity.miUID, userConversation?.id!!))
        //       .addOnCompleteListener { if(it.isSuccessful) activity.inputMessage.setText("") }
        }
    }
}