package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import contact.messager.util.`class`.Message
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.components.ChatConversationActivity.SaveNewMessageFuncionality
import contact.messager.util.api.CreateChatChannelFirebase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import contact.messager.util.adapter.MessageFirestoreAdapter
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import androidx.recyclerview.widget.LinearLayoutManager
import contact.messager.R
import contact.messager.util.`class`.App.Companion.userConversation

import contact.messager.util.api.DeleteOldMessages
class ChatConversationActivity : AppCompatActivity() {
    lateinit var messageFirestoreAdapter: MessageFirestoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)

        // set other user image, age, name
         ChatConversationObject(this, this).initChatConversationFunction()

     //   // create conversation if no exsist and set realChannelId FIREBASE FIRESTORE, no lo uso, uso el firebase !!!
     //   // ShowMeMessageFromThisConversation(this, this).showMeMessages()
//
     //   // guardamos nuevo mensage
     //   SaveNewMessageFuncionality(this, this).initChatConversationActivity()

        // create conversation if no exsist and set realChannelId FIREBASE DATABASE
    //   CreateChatChannelFirebase(this).createOrGetChatChannle(userConversation?.id!!){ channelId ->

    //       realChannelId = channelId

    //       val query: Query = FirebaseFirestore.getInstance()
    //           .collection("conversation")
    //           .document("conversation")
    //           .collection(realChannelId!!)
    //           .orderBy("time", Query.Direction.DESCENDING)
    //           .limit(limitMessages)

    //       val options: FirestoreRecyclerOptions<Message> = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()

    //       val lM = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
    //       recyclerViewMessages.setHasFixedSize(true)
    //       lM.stackFromEnd = true
    //       recyclerViewMessages.layoutManager = lM
    //       messageFirestoreAdapter =  MessageFirestoreAdapter(options, this, recyclerViewMessages)
    //       messageFirestoreAdapter.startListening()
    //       recyclerViewMessages.adapter = messageFirestoreAdapter

    //       // eliminamos todos los mensages menos  ultimos limitMessages
    //       DeleteOldMessages(this).deleteMessagesFromThisConversation(realChannelId!!)
    //   }


    }

    companion object{
        var realChannelId: String? = null
        var miUID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val limitMessages: Long = 111
    }


    override fun onStop() {
        super.onStop()
       // messageFirestoreAdapter.stopListening()
    }
}