package contact.messager.util.api
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class CreateChatChannelFirebase {
    private val firestore = FirebaseFirestore.getInstance().collection("enganched_chat")
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun createOrGetChatChannle(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        firestore.document(otherUserId).collection("channel").document(miId).get().addOnSuccessListener  {
            if(it.exists()) {
                val idChat = it["id"].toString()
                // añado idChat a mi convesacion por si he borrado en listado de conversacion
                firestore.document(miId).collection("channel").document(otherUserId).set(mapOf("id" to idChat))
                onComplete( idChat )
            } else {
                // 1. create new chatChannel
                val newChatId = System.currentTimeMillis().toString()
                // firebaseReference.child("chats/$newChatId").setValue(ChatChannel(mutableListOf(miId, otherUserId)))

                // 2. save channel Id en mi user and in other user
                firestore.document(miId).collection("channel").document(otherUserId).set(mapOf("id" to newChatId))
                firestore.document(otherUserId).collection("channel").document(miId).set(mapOf("id" to newChatId))
                onComplete(newChatId)
            }
        }
    }

    fun getConversationChatChannel(otherUserId: String, onComplete: (channelId: String?) -> Unit){
        firestore.document(miId).collection("channel").document(otherUserId).get().addOnSuccessListener  {
            if(it.exists()) {
                val idChat = it["id"].toString()
                onComplete( idChat )
            } else {
                onComplete(null)
            }
        }
    }

    fun deleteUserConversation(otherUserId: String, onComplete: (res: String) -> Unit){
        //   var idChat: String?  = null
        //   // coger id de la conversacion
        //   FirebaseFirestore.getInstance().collection("enganched_chat").document(miId).collection("channel").document(otherUserId).get().addOnSuccessListener {
        //       if(it.exists()) {
        //           idChat = it["id"].toString();
        //           // eliminar mensages de la convesacion
        //           FirebaseFirestore.getInstance().collection("conversation").document("conversation").collection(idChat!!).get().addOnSuccessListener {
        //               for(doc in  it.documents){
        //                   FirebaseFirestore.getInstance().collection("conversation").document("conversation").collection(idChat!!).document(doc.id).delete()
        //               }
        //           }
        //       }
        //   }

        // eliminar enganched chat
        FirebaseFirestore.getInstance().collection("enganched_chat").document(miId).collection("channel").document(otherUserId)
            .delete().addOnSuccessListener { onComplete("ok") }
    }


}











