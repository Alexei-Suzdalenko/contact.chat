package contact.messager.util.components.ChatConversation
import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.ChatConversation
import contact.messager.ChatConversation.Companion.miUID
import contact.messager.ChatConversation.Companion.realChannelId
import contact.messager.R
import contact.messager.util.assets.Message
import contact.messager.util.repository.AddNewMessageFirestore.addNewMessageFirestore
import java.text.SimpleDateFormat
import java.util.*

class SaveNewMessageFuncionality(val context: Context, val activity: ChatConversation) {
    val editTextConvers = activity.findViewById<EditText>(R.id.inputMessage)
    val sentMessage = activity.findViewById<ImageView>(R.id.messageBtn)


    @SuppressLint("SimpleDateFormat")
    fun initChatConversationActivity() {

        sentMessage.setOnClickListener {
            val textMessage = editTextConvers.text.toString()
            if (realChannelId != null && textMessage.isNotBlank() && textMessage.isNotEmpty()) {
                addNewMessageFirestore(textMessage, "text", activity)
            }
        }

    }
}