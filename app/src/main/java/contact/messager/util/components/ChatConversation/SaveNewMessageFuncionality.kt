package contact.messager.util.components.ChatConversation
import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import contact.messager.ChatConversationActivity
import contact.messager.ChatConversationActivity.Companion.realChannelId
import contact.messager.R
import contact.messager.util.repository.AddNewMessageFirestore.addNewMessageFirestore

class SaveNewMessageFuncionality(val context: Context, val activity: ChatConversationActivity) {
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