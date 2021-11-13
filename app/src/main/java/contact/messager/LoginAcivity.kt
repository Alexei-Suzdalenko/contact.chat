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

class LoginAcivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_acivity)
        supportActionBar?.hide()

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            var jui = result.resultCode
        //   if (result.resultCode == Activity.RESULT_OK) {                                        Toast.makeText(this, "RESULT_OK" , Toast.LENGTH_LONG).show()
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    Toast.makeText(this, "try to log from google " , Toast.LENGTH_LONG).show()
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(this, account.idToken!!, this)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Error " + task.exception , Toast.LENGTH_LONG).show()
                }
         //  } else Toast.makeText(this, "RESULT_NONONO $jui" , Toast.LENGTH_LONG).show()
        }
        // entrar con google
        LoginActivityGoogleComponent(this, this, resultLauncher).instanceGoogleLoginActivity()
        // entrar con email y contraseña
        LoginActivityEmailComponent(this, this).instanceLoginEmailPassword()
        // estamos pendiente de estado registarse o
        EstateLoginActivityComponent(this, this).initCurrentStateActivity()

    }
}

//     cd C:\Program Files\Android\Android Studio\jre\bin
//     C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\pas_alexeis_alias_key0.jks

//    keytool -list -v -keystore C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\pas_alexeis_alias_key0.jks -alias key0