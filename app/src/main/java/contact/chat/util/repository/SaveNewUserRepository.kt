package contact.chat.util.repository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import contact.chat.util.assets.App.Companion.editor

object SaveNewUserRepository {
    data class User(val name: String, val email: String)

    fun saveNewUserInDatabase(id: String, name: String, email: String){
        editor.putString("name", name); editor.putString("email", email); editor.apply()

        Firebase.database.reference
            .child("users")
            .child(id)
            .setValue(User(name, email))
    }
}