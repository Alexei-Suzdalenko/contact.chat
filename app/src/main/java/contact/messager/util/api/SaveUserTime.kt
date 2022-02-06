package contact.messager.util.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SaveUserTime {
    val userid = FirebaseAuth.getInstance().currentUser!!.uid
    val refUserInfo = FirebaseDatabase.getInstance().reference.child("userInfo").child(userid)

    fun saveUserTimeOnline(){
        val time = System.currentTimeMillis().toString()
        val savedData = HashMap<String, Any>()
              savedData["userOnline"] = time
       refUserInfo.updateChildren(savedData)
    }
}