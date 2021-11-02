package contact.messager.util.repository
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import contact.messager.ChatConversation

class DeleteOldMessages(val context: Context) {
    fun deleteMessagesFromThisConversation(conversation: String){
        // eliminamos todos los mensages menos  ultimos limitMessages
        FirebaseFirestore.getInstance()
            .collection("conversation")
            .document("conversation")
            .collection(conversation)
            .orderBy("time", Query.Direction.ASCENDING)
            .limit(1111)
            .get()
            .addOnSuccessListener{ it ->
                val numberMessages = it?.documents?.size?.toInt()
                if( numberMessages!= null && numberMessages > ChatConversation.limitMessages){
                    for(i in 0..numberMessages - ChatConversation.limitMessages){
                        val idDoc = it.documents[i.toInt()].id
                        FirebaseFirestore.getInstance().collection("conversation").document("conversation").collection("1635800733962").document(idDoc).delete()
                    }
                }
            }
    }
}