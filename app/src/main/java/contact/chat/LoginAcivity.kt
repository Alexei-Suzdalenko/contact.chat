package contact.chat
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.chat.util.components.LoginActivity.EstateLoginActivityComponent
import contact.chat.util.components.LoginActivity.LoginActivityEmailComponent
import contact.chat.util.components.LoginActivity.LoginActivityGoogleComponent
import contact.chat.util.components.LoginActivity.LoginActivityGoogleComponent.Companion.firebaseAuthWithGoogle

class LoginAcivity : AppCompatActivity() { // eliminar sha y sha 1 de ordenador envenedado
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_acivity)
        supportActionBar?.hide()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(this, account.idToken!!, this)
                    Toast.makeText(this, "User registered " + account.idToken, Toast.LENGTH_LONG).show()
                } catch (e: ApiException) {
                    Toast.makeText(this, "Error resultlauncher comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show()
                }
            }
        }
        // entrar con google
        LoginActivityGoogleComponent(this, this, resultLauncher).instanceGoogleLoginActivity()
        // entrar con email y contraseña
        LoginActivityEmailComponent(this, this).instanceLoginEmailPassword()
        // estamos pendiente de estado registarse o
        EstateLoginActivityComponent(this, this).initCurrentStateActivity()

      /////  Firebase.auth.signOut()
    }

    override fun onStart() {
        super.onStart()
        // val auth: FirebaseAuth = Firebase.auth
        // val currentUser = auth.currentUser
        // Toast.makeText(this, "You are registered onStart() " + auth.currentUser?.uid , Toast.LENGTH_LONG).show()
    }

}