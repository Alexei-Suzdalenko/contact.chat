package contact.messager.util.repository
import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.ChatConversation
import contact.messager.util.assets.Message
import contact.messager.zConverationSearchProfileFragments.SearchFragment
import kotlinx.android.synthetic.main.activity_chat_conversation.*

object AddNewMessageFirestore {
    @SuppressLint("SimpleDateFormat")
    fun addNewMessageFirestore(textMessage: String, typeMessage: String, activity: ChatConversation){

        if(typeMessage == "text"){
            FirebaseFirestore.getInstance()
                .collection("conversation")
                .document("conversation")
                .collection(ChatConversation.realChannelId!!)
                .document()
                .set(Message(System.currentTimeMillis().toString(), textMessage, "", ChatConversation.miUID, SearchFragment.userConversation?.id!!))
                .addOnCompleteListener { if(it.isSuccessful) activity.inputMessage.setText("") }
        }
    }
}