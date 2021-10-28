package contact.chat.util.components.LoginActivity
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.chat.Chat
import contact.chat.LoginAcivity
import contact.chat.R
import contact.chat.util.repository.SaveNewUserRepository.saveNewUserInDatabase

class LoginActivityGoogleComponent(val context: Context, val activity: LoginAcivity, val resultLauncher: ActivityResultLauncher<Intent>) {

    fun instanceGoogleLoginActivity(){
        val btnGoogleLogin = activity.findViewById<Button>(R.id.btn_google_login)
        btnGoogleLogin.setOnClickListener {  tryLoginUser() }
    }

    private fun tryLoginUser(){ //                                                                                                             getString(R.string.default_web_client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken( "156174194286-d6l242t6avc589bcsc4ht5dg8f0ij7rs.apps.googleusercontent.com" ).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }


    companion object{
        fun firebaseAuthWithGoogle(context: Context, idToken: String, activity: LoginAcivity) {
            val  auth = Firebase.auth
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if( currentUser != null){
                        saveNewUserInDatabase(currentUser.uid, currentUser.displayName.toString(), currentUser.email.toString(), "none")
                        context.startActivity(Intent(context, Chat::class.java)); activity.finish()
                    }
                } else {
                    Toast.makeText(context, "LoginActivityGoogleComponent failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}