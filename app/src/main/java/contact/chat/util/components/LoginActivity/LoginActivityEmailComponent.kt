package contact.chat.util.components.LoginActivity
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.chat.Chat
import contact.chat.LoginAcivity
import contact.chat.R
import contact.chat.util.repository.SaveNewUserRepository.saveNewUserInDatabase

class LoginActivityEmailComponent(val context: Context, val activity: LoginAcivity) {
    val auth = Firebase.auth
    val userNameEdit = activity.findViewById<EditText>(R.id.userNameText).text.toString().trim()
    val userEmailEdit = activity.findViewById<EditText>(R.id.textEmail)
    val passwordEdit  = activity.findViewById<EditText>(R.id.textPassword)

    fun instanceLoginEmailPassword(){
        val btnEmailLogin = activity.findViewById<Button>(R.id.btn_email_login)

        val currentUser = auth.currentUser
        if(currentUser != null){
            // si estamos logeados vamos a activity de chat
            Toast.makeText(context, currentUser.displayName + "user name",  Toast.LENGTH_LONG).show()
        }

        btnEmailLogin.setOnClickListener {
            if( userNameEdit.isEmpty()  ) return@setOnClickListener
            val email = userEmailEdit.text.toString().trim()
            val password =  passwordEdit.text.toString().trim()

            auth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "createUserWithEmail:success.", Toast.LENGTH_SHORT).show()
                        signInEmailAndPassword(userNameEdit, email, password)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

    private fun signInEmailAndPassword(name: String, email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                if( currentUser != null){
                    saveNewUserInDatabase(currentUser.uid, name, email)
                    context.startActivity(Intent(context, Chat::class.java)); activity.finish()
                }
            } else { Toast.makeText(context, "signInEmailAndPassword failed.", Toast.LENGTH_SHORT).show() }
        }
    }


}