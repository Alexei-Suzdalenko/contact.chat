package contact.messager.util.components.ChatConversation
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.bumptech.glide.Glide
import contact.messager.ChatConversationActivity
import contact.messager.PerfilUserActivity
import contact.messager.R
import contact.messager.util.assets.App.Companion.userConversation
import de.hdodenhof.circleimageview.CircleImageView

class ChatConversationObject(val context: Context, val activity: ChatConversationActivity) {
    val emailChatUser = activity.findViewById<TextView>(R.id.emailChatUser)
    val nameChatUser = activity.findViewById<TextView>(R.id.nameChatUser)
    val userImage = activity.findViewById<CircleImageView>(R.id.userImageConverationAct)

    fun initChatConversationFunction(){
        emailChatUser.text = userConversation?.email
        nameChatUser.text = userConversation?.name
        Glide.with( context ).load( userConversation?.image ).into( userImage )

        userImage.setOnClickListener { context.startActivity(Intent(context, PerfilUserActivity::class.java)) }
    }
}