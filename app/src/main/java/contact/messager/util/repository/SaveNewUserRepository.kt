package contact.messager.util.repository
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.ui.main.PlaceholderFragment
import contact.messager.ui.main.PlaceholderFragment.Companion.typeUserImagePlaceholderFragment
import contact.messager.util.assets.App
import contact.messager.util.assets.App.Companion.editor
import contact.messager.util.assets.User
import java.util.*
import kotlin.collections.HashMap

object SaveNewUserRepository {

    /*
    fun saveNewUserInDatabase(id: String) {
        editor.putString("id", id)
        editor.putString("name", "")
        editor.putString("image",  "https://alexei-suzdalenko.github.io/r-radio/user.png")
        editor.putString("backImage", "https://alexei-suzdalenko.github.io/r-radio/backgorund.png")
        editor.apply()
    }
     */

    // guardar imagen de usuario perfil o de fondo
    fun saveImageProfileUser(imageUri: Uri?, context: Context, fragment: FragmentProfileBinding) {
        val email = App.sharedPreferences.getString("email", "email").toString()
        if (imageUri != null) {
            Toast.makeText(context, "UPLOADING IMAGE", Toast.LENGTH_LONG).show()
            val fileRef = FirebaseStorage.getInstance().reference.child("users").child("$email$typeUserImagePlaceholderFragment.jpg")
            fileRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) { task.exception?.let { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() } }
                fileRef.downloadUrl }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val map = HashMap<String, Any>()
                    if (typeUserImagePlaceholderFragment == "image") {
                        map["image"] = downloadUri
                        editor.putString("image", downloadUri)
                        Glide.with( context ).load( downloadUri ).into( fragment.userImageProfile )
                    } else {
                        map["backImage"] = downloadUri
                        editor.putString("backImage", downloadUri)
                        Glide.with( context ).load( downloadUri ).into( fragment.imageBackground )
                    }
                    editor.apply()
                    Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid).updateChildren(map).addOnCompleteListener { it ->
                        if( it.isSuccessful){
                            Toast.makeText(context, "FILE UPLOAD SUCCESS", Toast.LENGTH_LONG).show()

                        } else { Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show() }
                    }
                } else { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() }
            }
        } else { Toast.makeText(context, "ERROR IMAGE", Toast.LENGTH_LONG).show() }
       }

    }