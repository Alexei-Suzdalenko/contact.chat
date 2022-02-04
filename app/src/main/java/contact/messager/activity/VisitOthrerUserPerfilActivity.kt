package contact.messager.activity
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.util.`class`.App.Companion.userConversation

import kotlinx.android.synthetic.main.perfil_conversation_user.*

class VisitOthrerUserPerfilActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_conversation_user)

   //    Glide.with(this).load(userConversation?.backImage).into(backImageConversationUser)
        Glide.with(this).load(userConversation?.image).into(perfilImageConversationUser)

      emailProfileConversationUser.text = userConversation?.name + " " + userConversation?.age.toString()
       userNameConversationUser.text = userConversation?.name

    }
}