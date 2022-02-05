package contact.messager.util.api
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.util.classes.App
import contact.messager.util.classes.App.Companion.editor
import contact.messager.util.classes.App.Companion.sharedPreferences
import contact.messager.util.classes.App.Companion.typeUserImagePlaceholderFragment
import contact.messager.util.classes.User
import contact.messager.util.classes.UserInfo
import kotlin.collections.HashMap

object SaveDataImageUserFirebase {
    /*
    fun saveNewUserInDatabase(id: String) {
        editor.putString("id", id)
        editor.putString("name", "")
        editor.putString("image",  "https://alexei-suzdalenko.github.io/r-radio/user.png")
        editor.putString("backImage", "https://alexei-suzdalenko.github.io/r-radio/backgorund.png")
        editor.apply()
    }
     */

    fun GetVisitProfileData(idVisitUser: String, onComplete:(userInfo: UserInfo) -> Unit){
         FirebaseDatabase.getInstance().reference.child("userInfo").child(idVisitUser)
             .addListenerForSingleValueEvent(object: ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                         val userInfo: UserInfo? = snapshot.getValue(UserInfo::class.java)
                         if (userInfo != null) onComplete(userInfo)
                 }
                 override fun onCancelled(error: DatabaseError) {}
             })
    }

    fun GetListUsers(onComplete:(listSearchedUsers: ArrayList<User>) -> Unit){
        val country = sharedPreferences.getString("country", "").toString()
        val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val listUsersSearched = ArrayList<User>()
        Firebase.database.reference.child("user").orderByChild("country").equalTo(country)
             // select * from user where age < 30
            // .orderByChild("age").endAt("29")
            // select * from user where name = "p%"
            // .orderByChild("name").startAt("p").endAt("p\uf8ff")
            // .startAt(inputEditTextString)
            // .orderByChild("email")
            //  .limitToFirst(11)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children) {
                        val user: User? = ds.getValue(User::class.java)
                        if (user != null && miId != ds.key.toString()) {
                            user.id = ds.key.toString()
                            if(user.image.length < 11) user.image = "https://alexei-suzdalenko.github.io/r-radio/user.png"
                            if(user.age.toInt() < 1) user.age = "..."
                            listUsersSearched.add(user)
                        }
                    }
                    onComplete(listUsersSearched)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }


    fun SaveUserInfo( name: String,  age: String, status: String, onComlete:(result: String) -> Unit){
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val refUser = FirebaseDatabase.getInstance().reference.child("user")
        val refUserInfo = FirebaseDatabase.getInstance().reference.child("userInfo")
        val user = HashMap<String, Any>()
              user["name"] = name
              user["age"] = age
        val userInfo = HashMap<String, Any>()
              userInfo["status"] = status
        refUserInfo.child(userId).updateChildren(userInfo)
        refUser.child(userId).updateChildren(user).addOnCompleteListener {
            if(it.isSuccessful) onComlete("ok")
        }
    }


    // guardar imagen de usuario perfil o de fondo
    fun saveImageProfileUser(imageUri: Uri?, context: Context, fragment: FragmentProfileBinding) {
        val email = App.sharedPreferences.getString("email", "").toString()
        var pathString = ""
        if (imageUri != null) {
            Toast.makeText(context, "UPLOADING IMAGE", Toast.LENGTH_LONG).show()
            val fileRef = FirebaseStorage.getInstance().reference.child("perfil").child("$email++$typeUserImagePlaceholderFragment++.jpg")

            fileRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) { task.exception?.let { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() } }
                fileRef.downloadUrl }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val map = HashMap<String, Any>()
                    if (typeUserImagePlaceholderFragment == "image") {
                        pathString = "user"
                        map["image"] = downloadUri
                        editor.putString("image", downloadUri)
                        Glide.with( context ).load( downloadUri ).into( fragment.userImageProfile )
                    } else {
                        pathString = "userInfo"
                        map["backImage"] = downloadUri
                        editor.putString("backImage", downloadUri)
                        Glide.with( context ).load( downloadUri ).into( fragment.imageBackground )
                    }
                    editor.apply()
                    Firebase.database.reference.child(pathString).child(Firebase.auth.currentUser!!.uid).updateChildren(map).addOnCompleteListener { it ->
                        if( it.isSuccessful){
                            Toast.makeText(context, "FILE UPLOAD SUCCESS", Toast.LENGTH_LONG).show()

                        } else { Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show() }
                    }
                } else { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() }
            }
        } else { Toast.makeText(context, "ERROR IMAGE", Toast.LENGTH_LONG).show() }
       }

    }