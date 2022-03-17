package contact.messager.activity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.components.VisitOtherUserPerfilActivity.VisitOtherUserActivityFunctionalityBlock


import kotlinx.android.synthetic.main.perfil_conversation_user.*

class VisitOthrerUserPerfilActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_conversation_user)

     //   GetVisitProfileData(userConversation?.id.toString()){ userInfo ->
     //       if(userInfo.backImage.length < 22) userInfo.backImage = "https://alexei-suzdalenko.github.io/r-radio/backgorund.png"
     //       Glide.with(this).load(userInfo.backImage).into(backImageConversationUser)
     //       cityVisitUser.text = userInfo.locality.toString()
     //       textViewStatus.text = userInfo.status.toString()
     //   }

        if(userConversation?.backImage!!.length < 22) userConversation?.backImage = "https://alexei-suzdalenko.github.io/r-radio/backgorund.png"
        Glide.with(this).load(userConversation?.backImage).into(backImageConversationUser)
        Glide.with(this).load(userConversation?.image).into(perfilImageConversationUser)

        emailProfileConversationUser.text = userConversation?.name + " " + userConversation?.age.toString()
        userNameConversationUser.text    = userConversation?.name
        cityVisitUser.text                          = userConversation?.locality
        textViewTextAge.text                    = userConversation?.age.toString()
        textViewStatus.text                       = userConversation?.status

        perfilChatUserInfo.setOnClickListener { startActivity(Intent(this, ChatConversationActivity::class.java)); finish(); }

        VisitOtherUserActivityFunctionalityBlock(this, userConversation?.id.toString()).prepareFunctinality()
    }
}