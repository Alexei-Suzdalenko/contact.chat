package contact.messager.util.components.ChatConversation
import android.content.Context
import android.widget.Toast
import contact.messager.ChatConversation
import contact.messager.ChatConversation.Companion.realChannelId
import contact.messager.util.repository.CreateChatChannelFirestore.getOrCreateChatChannel

class ShowMeMessageFromThisConversation(val context: Context, val activity: ChatConversation) {

    fun showMeMessages(){
        getOrCreateChatChannel("otherUserId"){ channelId ->
            realChannelId = channelId
            Toast.makeText(context, channelId, Toast.LENGTH_LONG).show()
        }
    }
}