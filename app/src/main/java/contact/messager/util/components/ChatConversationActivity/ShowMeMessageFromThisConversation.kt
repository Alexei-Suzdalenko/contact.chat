package contact.messager.util.components.ChatConversationActivity
import android.content.Context
import contact.messager.activity.ChatConversationActivity
import contact.messager.util.api.CreateChatChannelFirestore.getOrCreateChatChannel

class ShowMeMessageFromThisConversation(val context: Context, val activity: ChatConversationActivity) {

    fun showMeMessages(){
        getOrCreateChatChannel("otherUserId"){ channelId ->
          //  realChannelId = channelId

        }
    }
}