package contact.messager.util.notification
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import contact.messager.util.clas.App

class NotificationWork {
    val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val refUserInfo = Firebase.firestore.collection("user").document(id)


    fun saveUserToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result != null && !TextUtils.isEmpty(task.result)) {
                    val token: String = task.result!!.toString()

                    val tokenUpdate = HashMap<String, Any>()
                          tokenUpdate["token"] = token; App.editor.putString("token", token).apply()
                    refUserInfo.update(tokenUpdate)
                }
            }
        }
    }

















}