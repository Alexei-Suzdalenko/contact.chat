package contact.messager.util.api
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateChatChannelFirebase(val context: Context) {
    private val firebaseReference: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun createOrGetChatChannle(otherUserId: String, onComplete: (channelId: String) -> Unit) {

        firebaseReference.child("enganched_chat/$miId/$otherUserId").get().addOnSuccessListener  {
            if(it.exists()) {
                val idChat = it.child("id").value.toString()
                onComplete( idChat )
            } else {
                // 1. create new chatChannel
                val newChatId = System.currentTimeMillis().toString()
                // firebaseReference.child("chats/$newChatId").setValue(ChatChannel(mutableListOf(miId, otherUserId)))

                // 2. save channel Id en mi user and in other user
                firebaseReference.child("enganched_chat/$miId/$otherUserId").setValue(mapOf("id" to newChatId))
                firebaseReference.child("enganched_chat/$otherUserId/$miId").setValue(mapOf("id" to newChatId))
                onComplete(newChatId)
            }
        }
    }
}