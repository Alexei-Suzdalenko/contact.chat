package contact.messager.util.components.VisitOtherUserPerfilActivity
import android.content.Intent
import contact.messager.activity.BlockActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import kotlinx.android.synthetic.main.perfil_conversation_user.*

class VisitOtherUserActivityFunctionalityBlock (val activity: VisitOthrerUserPerfilActivity, val userId: String) {
    val intent = Intent(activity, BlockActivity::class.java)

    fun prepareFunctinality(){
        intent.putExtra("userId", userId)

        activity.blockThisUser.setOnClickListener {
            intent.putExtra("action", "block"); activity.startActivity(intent); activity.finish()
        }

        activity.reportUserBehavior.setOnClickListener {
            intent.putExtra("action", "report"); activity.startActivity(intent); activity.finish()
        }
    }
}