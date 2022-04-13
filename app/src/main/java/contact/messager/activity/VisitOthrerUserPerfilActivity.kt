package contact.messager.activity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import contact.messager.R
import contact.messager.util.api.Adds
import contact.messager.util.clas.App
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.components.VisitOtherUserPerfilActivity.VisitOtherUserActivityFunctionalityBlock
import kotlinx.android.synthetic.main.perfil_conversation_user.*
class VisitOthrerUserPerfilActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_conversation_user)

        if (userConversation?.backImage!!.length < 22) userConversation?.backImage =
            "https://alexei-suzdalenko.github.io/r-radio/backgorund.png"
        Glide.with(this).load(userConversation?.backImage).into(backImageConversationUser)
        Glide.with(this).load(userConversation?.image).into(perfilImageConversationUser)

        emailProfileConversationUser.text = userConversation?.name
        userNameConversationUser.text = userConversation?.name
        cityVisitUser.text = userConversation?.locality
        textViewTextAge.text = userConversation?.age.toString()
        textViewStatus.text = userConversation?.status

        perfilChatUserInfo.setOnClickListener {
            startActivity(Intent(this, ChatConversationActivity::class.java)); finish();
        }
        VisitOtherUserActivityFunctionalityBlock(this, userConversation?.id.toString()).prepareFunctinality()


        val showImage = Intent(this, ShowImages::class.java)
        backImageConversationUser.setOnClickListener { Adds.start(this); startActivity(showImage.putExtra("image", "back")) }
        perfilImageConversationUser.setOnClickListener { Adds.start(this); startActivity(showImage.putExtra("image", "image")) }


    }
}