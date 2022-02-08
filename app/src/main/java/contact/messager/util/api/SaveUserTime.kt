package contact.messager.util.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SaveUserTime {
    val userid =  FirebaseAuth.getInstance().currentUser!!.uid
    val refUserInfo = Firebase.firestore.collection("userInfo").document(userid)

    fun saveUserTimeOnline(){
        val time = System.currentTimeMillis().toString()
        val savedData = HashMap<String, Any>()
              savedData["userOnline"] = time
              refUserInfo.update(savedData)
    }
}