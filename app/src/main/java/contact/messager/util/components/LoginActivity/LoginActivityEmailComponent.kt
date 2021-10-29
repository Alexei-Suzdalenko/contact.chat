package contact.messager.util.components.LoginActivity
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.Chat
import contact.messager.LoginAcivity
import contact.messager.R
import contact.messager.util.repository.SaveNewUserRepository.saveNewUserInDatabase

class LoginActivityEmailComponent(val context: Context, val activity: LoginAcivity) {
    val auth = Firebase.auth
    val userNameEdit = activity.findViewById<EditText>(R.id.userNameText)
    val userEmailEdit = activity.findViewById<EditText>(R.id.textEmail)
    val passwordEdit  = activity.findViewById<EditText>(R.id.textPassword)

    fun instanceLoginEmailPassword(){
        val btnEmailLogin = activity.findViewById<Button>(R.id.btn_email_login)

        btnEmailLogin.setOnClickListener {
            val name = userNameEdit.text.toString().trim()
            val email = userEmailEdit.text.toString().trim()
            val password =  passwordEdit.text.toString().trim()
            if( name.isEmpty() || email.length < 5 || password.length < 6 ) { Toast.makeText(context, context.getString(R.string.fill_data_correctly), Toast.LENGTH_LONG).show(); return@setOnClickListener }

            auth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener(activity) { signInEmailAndPassword(name, email, password) }
        }
    }

    private fun signInEmailAndPassword(name: String, email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                if( currentUser != null){
                    saveNewUserInDatabase(currentUser.uid, name, email, password, "emai")
                    context.startActivity(Intent(context, Chat::class.java)); activity.finish()
                }
            } else { Toast.makeText(context, "sign In Email And Password Failed", Toast.LENGTH_SHORT).show() }
        }
    }


}