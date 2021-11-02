package contact.messager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import contact.messager.zConverationSearchProfileFragments.SearchFragment.Companion.userConversation
import kotlinx.android.synthetic.main.perfil_conversation_user.*

class PerfilConversationUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_conversation_user)

        Glide.with(this).load(userConversation?.backImage).into(backImageConversationUser)
        Glide.with(this).load(userConversation?.image).into(perfilImageConversationUser)

        emailProfileConversationUser.text = userConversation?.email
        userNameConversationUser.text = userConversation?.name

    }
}