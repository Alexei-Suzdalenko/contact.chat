package contact.messager.util.repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.util.assets.ChatChannel

object CreateChatChannelFirestore {
  // private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
  // private val currentUserDocRef: DocumentReference get() = firestoreInstance.document("users/" + FirebaseAuth.getInstance().currentUser?.uid )

    fun getOrCreateChatChannel(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        // recojemos conversacion ID de mi y otro usuarion si exsiste
        FirebaseFirestore.getInstance()
            .document("users/" + FirebaseAuth.getInstance().currentUser?.uid )
            .collection("engagedChatChannels").document(otherUserId)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }
                // si no tenemos conversacion creamos una con su id y guardamos el ID CONVERSACION en mi y en otro usuarion
                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                // creamos conversacion
                val newChannel = FirebaseFirestore.getInstance().collection("chatChannels").document()
                      newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))
                // guardamos conversacion ID en mi usuarion
                FirebaseFirestore.getInstance()
                    .document("users/" + FirebaseAuth.getInstance().currentUser?.uid )
                    .collection("engagedChatChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))
                // guardamos conversacion ID en otro usuario
                FirebaseFirestore.getInstance().collection("users").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))

                onComplete(newChannel.id)
            }
    }
}