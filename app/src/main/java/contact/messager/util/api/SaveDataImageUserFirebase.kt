package contact.messager.util.api
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import contact.messager.R
import contact.messager.activity.MyProfileActivity
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.util.clas.App
import contact.messager.util.clas.App.Companion.editor
import contact.messager.util.clas.App.Companion.sharedPreferences
import contact.messager.util.clas.App.Companion.typeUserImagePlaceholderFragment
import contact.messager.util.clas.User
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlin.collections.HashMap

object SaveDataImageUserFirebase {
    // uso para no mostrarme a mi usuarios que he bloqueado
    val usersBlocked = App.sharedPreferences.getString("block", "").toString()


    fun GetListUsers(onComplete:(listSearchedUsers: ArrayList<User>) -> Unit){
        Log.d("snapshot", "usersBlocked="+ usersBlocked.toString())

        val country = sharedPreferences.getString("country", "").toString();
        val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val listUsersSearched = ArrayList<User>()
        val source = Source.CACHE
        FirebaseFirestore.getInstance().collection("user")
            // .whereEqualTo("country", country)
            // .orderBy("online", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener  {
            for (d in it) {
                if(miId != d.id && !usersBlocked.contains(d.id, ignoreCase = true) && d.data["name"].toString() != "null" && d.data["image"].toString() != "null"){
                    val data = d.data
                    if(data["name"] == null) data["name"] = ""
                    if(data["age"] == null) data["age"] = ""
                    val user = User(d.id, data["age"].toString(),  data["country"].toString(), data["image"].toString(), data["locality"].toString(), data["name"].toString(), data["online"].toString(), data["postal"].toString(), data["status"].toString(), data["token"].toString(), data["backImage"].toString())

                    listUsersSearched.add(user)
                }
            }
            onComplete(listUsersSearched)
        }
    }


    fun SaveUserInfo( name: String,  age: String, status: String, onComlete:(result: String) -> Unit){
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val refUser = Firebase.firestore.collection("user").document(userId)
        val user = HashMap<String, Any>()
              user["email"] = App.sharedPreferences.getString("email", "").toString()
              user["name"]  = name
              user["age"]     = age
              user["status"] = status
        refUser.update(user).addOnCompleteListener {  if(it.isSuccessful) onComlete("ok"); }
    }


    // guardar imagen de usuario perfil o de fondo
    fun saveImageProfileUser(imageUri: Uri?, context: Context, fragment: FragmentProfileBinding? = null) {
        val partOfTheWay = sharedPreferences.getString("email", System.currentTimeMillis().toString()).toString()
        fragment!!.progressBarProfile.visibility = View.VISIBLE
        if (imageUri != null) {
            Toast.makeText(context, context.resources.getString(R.string.uploadingImage), Toast.LENGTH_LONG).show()
            val fileRef = FirebaseStorage.getInstance().reference.child("perfil").child("$partOfTheWay-$typeUserImagePlaceholderFragment.jpg")

            fileRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) { task.exception?.let {
                    fragment.progressBarProfile.visibility = View.GONE
                    Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() } }
                fileRef.downloadUrl }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val map = HashMap<String, Any>()
                    if (typeUserImagePlaceholderFragment == "image") {

                        map["image"] = downloadUri
                        editor.putString("image", downloadUri);  editor.apply()
                        Glide.with( context ).load( downloadUri ).into( fragment.userImageProfile )
                    } else {
                        map["backImage"] = downloadUri
                        editor.putString("backImage", downloadUri);  editor.apply()
                        Glide.with( context ).load( downloadUri ).into( fragment.imageBackground )
                    }
                    FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).update(map).addOnCompleteListener { it ->
                        if( it.isSuccessful){
                            Toast.makeText(context, context.resources.getString(R.string.imageUploaded), Toast.LENGTH_LONG).show()
                        } else { Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show() }
                        fragment.progressBarProfile.visibility = View.GONE

                    }
                } else { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() }
            }
        } else {
            fragment.progressBarProfile.visibility = View.GONE
            Toast.makeText(context, "ERROR IMAGE", Toast.LENGTH_LONG).show() }
       }

    // guardar imagen de usuario perfil o de fondo
    fun saveImageProfileUserMyProfile(imageUri: Uri?, context: Context, fragment: MyProfileActivity) {
        val partOfTheWay = sharedPreferences.getString("email", "").toString()
        fragment.progressBarProfile.visibility = View.VISIBLE
        if (imageUri != null) {
            Toast.makeText(context, context.resources.getString(R.string.uploadingImage), Toast.LENGTH_LONG).show()
            val fileRef = FirebaseStorage.getInstance().reference.child("perfil").child("$partOfTheWay-$typeUserImagePlaceholderFragment.jpg")

            fileRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) { task.exception?.let {
                    fragment.progressBarProfile.visibility = View.GONE
                    Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() } }
                fileRef.downloadUrl }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val map = HashMap<String, Any>()
                    if (typeUserImagePlaceholderFragment == "image") {
                        map["image"] = downloadUri
                        editor.putString("image", downloadUri);  editor.apply()
                        Glide.with( context ).load( downloadUri ).into( fragment.userImageProfileMyProfile )
                    } else {
                        map["backImage"] = downloadUri
                        editor.putString("backImage", downloadUri);  editor.apply()
                        Glide.with( context ).load( downloadUri ).into( fragment.imageBackgroundMyProfile )
                    }
                    FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).update(map).addOnCompleteListener { it ->
                        if( it.isSuccessful){
                            Toast.makeText(context, context.resources.getString(R.string.imageUploaded), Toast.LENGTH_LONG).show()
                        } else { Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show() }
                        fragment.progressBarProfile.visibility = View.GONE
                    }
                } else { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() }
            }
        } else {
            fragment.progressBarProfile.visibility = View.GONE
            Toast.makeText(context, "ERROR IMAGE", Toast.LENGTH_LONG).show() }
    }

    }