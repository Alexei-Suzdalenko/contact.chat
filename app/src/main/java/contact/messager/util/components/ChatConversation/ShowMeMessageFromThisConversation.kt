package contact.messager.util.components.ChatConversation
import android.content.Context
import contact.messager.ChatConversationActivity
import contact.messager.ChatConversationActivity.Companion.realChannelId
import contact.messager.util.repository.CreateChatChannelFirestore.getOrCreateChatChannel

class ShowMeMessageFromThisConversation(val context: Context, val activity: ChatConversationActivity) {

    fun showMeMessages(){
        getOrCreateChatChannel("otherUserId"){ channelId ->
            realChannelId = channelId

        }
    }
}