package contact.messager.util.repository
import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.ChatConversationActivity
import contact.messager.util.assets.App.Companion.userConversation
import contact.messager.util.assets.Message
import contact.messager.zConverationSearchProfileFragments.SearchFragment
import kotlinx.android.synthetic.main.activity_chat_conversation.*

object AddNewMessageFirestore {
    @SuppressLint("SimpleDateFormat")
    fun addNewMessageFirestore(textMessage: String, typeMessage: String, activity: ChatConversationActivity){

        if(typeMessage == "text"){
            FirebaseFirestore.getInstance()
                .collection("conversation")
                .document("conversation")
                .collection(ChatConversationActivity.realChannelId!!)
                .document()
                .set(Message(System.currentTimeMillis().toString(), textMessage, "", ChatConversationActivity.miUID, userConversation?.id!!))
                .addOnCompleteListener { if(it.isSuccessful) activity.inputMessage.setText("") }
        }
    }
}