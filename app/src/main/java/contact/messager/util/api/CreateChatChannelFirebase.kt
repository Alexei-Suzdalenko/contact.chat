package contact.messager.util.api
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class CreateChatChannelFirebase() {
    private val firestore = FirebaseFirestore.getInstance().collection("enganched_chat")
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()


    fun createChannel(otherUserId: String, onComplete: (channelId: String) -> Unit){
            val searchChatId = System.currentTimeMillis().toString()
            firestore.document(miId).set(mapOf(otherUserId to searchChatId))
            firestore.document(otherUserId).set(mapOf(miId to searchChatId)).addOnCompleteListener { if(it.isSuccessful) onComplete(searchChatId); }
    }


    fun createOrGetChatChannle(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        var chatId = ""
        firestore.document(miId).get().addOnSuccessListener { doc ->
            if(doc.exists()){
                for(d in doc.data!!.entries.iterator()){
                    if(otherUserId == d.key) {
                        chatId = d.value.toString()
                        onComplete(chatId)
                    }
                }
                if(chatId.isBlank() || chatId.isEmpty()) createChannel(otherUserId){it-> onComplete(it)}
            } else {
                createChannel(otherUserId){it-> onComplete(it)}
            }
       }

/*
        FirebaseFirestore.getInstance().collection("enganched_chat").document("Ej8rRLCWNkVVZjt3k62SHnnuDOe2").get().addOnSuccessListener { document ->
            Log.d("newChatId1", "result: " + document.data)
            Log.d("newChatId1", "result: " + document.data!!.entries.iterator())
            for( d in document.data!!.entries.iterator()){
                Log.d("newChatId1", "result: " + d.key.toString()  + " --- "  + d.value.toString())
            }
        }
       val newChatId1 = System.currentTimeMillis().toString()
       firestore.collection("enganched_chat").document(miId).set(mapOf(otherUserId to newChatId1))
       firestore.collection("enganched_chat").document(otherUserId).set(mapOf(miId to newChatId1))
       Log.d("newChatId", "newChatId == > " + newChatId1)
*/
        /*
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
         */

    }

}