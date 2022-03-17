package contact.messager.util.api
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import contact.messager.util.clas.App
import contact.messager.util.notification.ServiceNotification

class BlockUserFire {
    val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    data class UserBlock(var id: String = "")

    fun block(blockId: String, onComlete:(res: String)->Unit){
        ServiceNotification().sentNotification (
            "block",
            App.userConversation!!.id,
            miId,
            App.userConversation!!.name,
            "block",
            App.sharedPreferences.getString("image", "").toString(),
            App.userConversation!!.token,
            App.userConversation!!.age
        )


       val dataUser             = HashMap<String, Any>()
            dataUser[miId]     = blockId
            dataUser[blockId] = miId
       FirebaseDatabase.getInstance().reference.child("block/$miId").setValue(dataUser)
       FirebaseDatabase.getInstance().reference.child("block/$blockId").setValue(dataUser).addOnSuccessListener { onComlete("ok") }
    }

}