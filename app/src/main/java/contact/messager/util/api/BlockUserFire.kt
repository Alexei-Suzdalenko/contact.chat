package contact.messager.util.api
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import contact.messager.util.classes.App
import contact.messager.util.classes.ChatEnganchedUser

class BlockUserFire {
    val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    data class UserBlock(var id: String = "")
    var usersBlockedMe = ""

    fun block(blockId: String){
        val dataUser             = HashMap<String, Any>()
             dataUser[miId]     = blockId
             dataUser[blockId] = miId
        FirebaseDatabase.getInstance().reference.child("block/$miId").setValue(dataUser)
        FirebaseDatabase.getInstance().reference.child("block/$blockId").setValue(dataUser)
    }

    fun getListUsersThenBlockedMe(){
        FirebaseDatabase.getInstance().reference.child("block/$miId").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val valueData = ds.value
                    usersBlockedMe += ", " + valueData
                }
                App.editor.putString("block", usersBlockedMe).apply()
            }
        })
    }

}