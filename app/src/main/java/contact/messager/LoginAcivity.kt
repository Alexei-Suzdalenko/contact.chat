package contact.messager
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import contact.messager.util.components.LoginActivity.EstateLoginActivityComponent
import contact.messager.util.components.LoginActivity.LoginActivityEmailComponent
import contact.messager.util.components.LoginActivity.LoginActivityGoogleComponent
import contact.messager.util.components.LoginActivity.LoginActivityGoogleComponent.Companion.firebaseAuthWithGoogle

class LoginAcivity : AppCompatActivity() { // eliminar sha y sha 1 de ordenador envenedado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_acivity)
        supportActionBar?.hide()

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(this, account.idToken!!, this)
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

    }
}