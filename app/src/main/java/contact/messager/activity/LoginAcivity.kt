package contact.messager.activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import contact.messager.R
import contact.messager.util.components.LoginActivity.EstateLoginActivityComponent
import contact.messager.util.components.LoginActivity.LoginActivityEmailComponent
import kotlinx.android.synthetic.main.login_acivity.*

class LoginAcivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_acivity)
        supportActionBar?.hide()

        // entrar con email y contraseña
        LoginActivityEmailComponent(this, this).instanceLoginEmailPassword()
        // estamos pendiente de estado registarse o
        EstateLoginActivityComponent(this, this).initCurrentStateActivity()

        aceptTermCond.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://santa-maria-de-cayon.github.io/contact.messager/terms-and-conditions.html")))
        }
    }
}

//     cd C:\Program Files\Android\Android Studio\jre\bin
//     C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\pas_alexeis_alias_key0.jks
//    keytool -list -v -keystore C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\pas_alexeis_alias_key0.jks -alias key0