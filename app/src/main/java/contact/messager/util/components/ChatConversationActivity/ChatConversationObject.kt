package contact.messager.util.components.ChatConversationActivity
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.bumptech.glide.Glide
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.R
import contact.messager.util.clas.App.Companion.userConversation
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class ChatConversationObject(val context: Context, val activity: ChatConversationActivity) {
    val nameChatUser = activity.findViewById<TextView>(R.id.nameChatUser)
    val ageChatUser = activity.findViewById<TextView>(R.id.ageChatUser)
    val userImage = activity.findViewById<CircleImageView>(R.id.userImageConverationAct)

    fun initChatConversationFunction(){

        try {
            nameChatUser.text = userConversation?.name
            ageChatUser.text = userConversation?.age
            if(userConversation?.image.toString().length < 22) userConversation!!.image = "https://alexei-suzdalenko.github.io/r-radio/user.png"
            Glide.with( context ).load( userConversation?.image ).into( userImage )
        } catch (e: Exception){ }

        userImage.setOnClickListener { context.startActivity(Intent(context, VisitOthrerUserPerfilActivity::class.java)); activity.finish() }
    }
}