package contact.messager.util.components.LoginActivity
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.activity.ConversSearchProfileActivity
import contact.messager.activity.LoginAcivity
import contact.messager.R
import contact.messager.util.`class`.App


class LoginActivityEmailComponent(val context: Context, val activity: LoginAcivity) {
    val auth = Firebase.auth
    val userEmailEdit = activity.findViewById<EditText>(R.id.textEmail)
    val passwordEdit  = activity.findViewById<EditText>(R.id.textPassword)

    fun instanceLoginEmailPassword(){
        val btnEmailLogin = activity.findViewById<Button>(R.id.btn_email_login)

        btnEmailLogin.setOnClickListener {
            val email = userEmailEdit.text.toString().trim()
            val password =  passwordEdit.text.toString().trim()
            if( email.length < 5 || password.length < 6 ) { Toast.makeText(context, context.getString(R.string.fill_data_correctly), Toast.LENGTH_LONG).show(); return@setOnClickListener }

            App.editor.putString("email", email); App.editor.putString("password", password); App.editor.apply()

            auth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener(activity) {  task ->
                if (task.isSuccessful) {
                    signInEmailAndPassword(email, password)
                } else {
                    signInEmailAndPassword(email, password)
                }
            }
        }
    }

    private fun signInEmailAndPassword(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                if( currentUser != null){
                    context.startActivity(Intent(context, ConversSearchProfileActivity::class.java)); activity.finish()
                }
            }
        }
    }


}