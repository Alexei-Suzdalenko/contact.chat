package contact.messager.util.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import contact.messager.util.clas.User

class SaveUserTime {
    val userid =  FirebaseAuth.getInstance().currentUser!!.uid
    val refUserInfo = Firebase.firestore.collection("user").document(userid)

    fun saveUserTimeOnline(){
        val time = System.currentTimeMillis().toString()
        val savedData = HashMap<String, Any>()
              savedData["online"] = time
              refUserInfo.update(savedData)
    }

    fun intentGetUserDataIfExsistEnDataBase(onComplete:(user: User? ) -> Unit){
        var user: User? = null
        val source = Source.CACHE
        refUserInfo.get().addOnSuccessListener {
            if(it.exists()){
                user = User("", it["age"].toString(), it["country"].toString(), it["image"].toString(), it["locality"].toString(), it["name"].toString(), it["online"].toString(), it["postal"].toString(), it["status"].toString(), "", it["backImage"].toString()      )
            }
            onComplete(user)
        }
    }
}