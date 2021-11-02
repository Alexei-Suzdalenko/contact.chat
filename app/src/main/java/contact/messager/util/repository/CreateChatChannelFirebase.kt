package contact.messager.util.repository
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.util.assets.ChatChannel

class CreateChatChannelFirebase(val context: Context) {
    private val firebaseReference: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun createOrGetChatChannle(otherUserId: String, onComplete: (channelId: String) -> Unit) {

        firebaseReference.child("enganchedChannels/$miId/$otherUserId").get().addOnSuccessListener  {
            if(it.exists()) {
                val idChat = it.child("channelId").value.toString()
                onComplete( idChat )
                return@addOnSuccessListener
            } else {
                // 1. create new chatChannel
                val newChatId = System.currentTimeMillis().toString()
                // firebaseReference.child("chats/$newChatId").setValue(ChatChannel(mutableListOf(miId, otherUserId)))

                // 2. save channel Id en mi user and in other user
                firebaseReference.child("enganchedChannels/$miId/$otherUserId").setValue(mapOf("channelId" to newChatId))
                firebaseReference.child("enganchedChannels/$otherUserId/$miId").setValue(mapOf("channelId" to newChatId))

                onComplete(newChatId)
            }
        }
    }
}