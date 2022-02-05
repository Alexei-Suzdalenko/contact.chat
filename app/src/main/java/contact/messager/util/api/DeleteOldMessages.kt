package contact.messager.util.api
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DeleteOldMessages(val context: Context) {
    fun deleteMessagesFromThisConversation(realChannelIdA: String){

        // eliminamos todos los mensages menos  ultimos limitMessages
        FirebaseFirestore
            .getInstance()
            .collection("conversation")
            .document("conversation")
            .collection(realChannelIdA)
            .orderBy("time", Query.Direction.ASCENDING)
            .limit(333)
            .get()
            .addOnSuccessListener { it ->

                val numberMessages = it?.documents?.size?.toInt()

                if( numberMessages != null && numberMessages > 222){
                    for(i in 0..numberMessages - 222){
                        val idDoc = it.documents[ i.toInt() ].id
                        FirebaseFirestore.getInstance().collection("conversation").document("conversation").collection(realChannelIdA).document(idDoc).delete()
                    }
                }
            }
    }
}