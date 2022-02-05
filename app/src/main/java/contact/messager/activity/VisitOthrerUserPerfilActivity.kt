package contact.messager.activity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.api.SaveDataImageUser.GetVisitProfileData

import kotlinx.android.synthetic.main.perfil_conversation_user.*

class VisitOthrerUserPerfilActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_conversation_user)

        GetVisitProfileData(userConversation?.id.toString()){ userInfo ->

            Glide.with(this).load(userInfo.backImage).into(backImageConversationUser)
            cityVisitUser.text = userInfo.locality.toString()
            textViewStatus.text = userInfo.status.toString()
        }

        Glide.with(this).load(userConversation?.image).into(perfilImageConversationUser)

        emailProfileConversationUser.text = userConversation?.name + " " + userConversation?.age.toString()
        userNameConversationUser.text = userConversation?.name
        textViewTextAge.text = userConversation?.age.toString()

        perfilChatUserInfo.setOnClickListener {
            startActivity(Intent(this, ChatConversationActivity::class.java))
        }
    }
}