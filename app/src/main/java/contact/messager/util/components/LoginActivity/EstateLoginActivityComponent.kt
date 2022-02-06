package contact.messager.util.components.LoginActivity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.activity.MianActivity
import contact.messager.activity.LoginAcivity

class EstateLoginActivityComponent(val context: Context, val activity: LoginAcivity) {

    fun initCurrentStateActivity(){
        val auth: FirebaseAuth = Firebase.auth
        val currentUser = auth.currentUser
        if( currentUser != null ){ context.startActivity(Intent(context, MianActivity::class.java)); activity.finish() }

        /*
        val userNameRegisred = App.sharedPreferences.getString("name", "none").toString()
        val emailRegitered = App.sharedPreferences.getString("email", "none").toString()
        val password = App.sharedPreferences.getString("password", "none").toString()

        if( userNameRegisred != "none" && emailRegitered != "none" && password != "none" ){
            activity.findViewById<TextView>(R.id.activity_description).text = context.getString(R.string.enter)
            activity.findViewById<TextView>(R.id.userNameText).setText( userNameRegisred )
            activity.findViewById<TextView>(R.id.textEmail).setText( emailRegitered )
            activity.findViewById<TextView>(R.id.textPassword).setText( password )
            activity.findViewById<TextView>(R.id.btn_email_login).text = context.getString(R.string.enter)
        }

         */

    }
}