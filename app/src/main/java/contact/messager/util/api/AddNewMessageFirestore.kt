package contact.messager.util.api
import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import contact.messager.activity.ChatConversationActivity
import contact.messager.util.clas.App
import contact.messager.util.clas.Message
import contact.messager.util.adapter.MessageFirestoreAdapter
import kotlinx.android.synthetic.main.activity_chat_conversation.*

object AddNewMessageFirestore {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    lateinit var messageFirestoreAdapter: MessageFirestoreAdapter

    @SuppressLint("SimpleDateFormat")
    fun addNewMessageFirestore(textMessage: String, activity: ChatConversationActivity, onComplete: (res: String) -> Unit){
           FirebaseFirestore.getInstance()
               .collection("conversation")
               .document("conversation")
               .collection(App.realChannelId!!)
               .document()
               .set(Message(System.currentTimeMillis().toString(), textMessage, "", miId)).addOnCompleteListener {
                   if(it.isSuccessful) onComplete("ok")
               }
    }


    fun initializaceFirestoreListenerMessager(channelId: String, activity: ChatConversationActivity){
        val query: Query = FirebaseFirestore.getInstance()
            .collection("conversation")
            .document("conversation")
            .collection(channelId)
            .orderBy("time", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()

        val lM = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        activity.recyclerViewMessages.setHasFixedSize(true)
        lM.stackFromEnd = true
        activity.recyclerViewMessages.layoutManager = lM
        messageFirestoreAdapter = MessageFirestoreAdapter(options, activity, activity.recyclerViewMessages)
        activity.recyclerViewMessages.adapter = messageFirestoreAdapter
        messageFirestoreAdapter.startListening()

        // eliminamos todos los mensages menos  ultimos limitMessages
        DeleteOldMessages(activity).deleteMessagesFromThisConversation(channelId)
    }
}
