package contact.messager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import contact.messager.util.assets.Message
import contact.messager.util.components.ChatConversation.ChatConversationObject
import contact.messager.util.components.ChatConversation.SaveNewMessageFuncionality
import contact.messager.util.repository.CreateChatChannelFirebase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import contact.messager.util.assets.MessageFirestoreAdapter
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import androidx.recyclerview.widget.LinearLayoutManager

import contact.messager.zConverationSearchProfileFragments.SearchFragment
import contact.messager.util.repository.DeleteOldMessages
class ChatConversation : AppCompatActivity() {
    lateinit var messageFirestoreAdapter: MessageFirestoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://medium.com/new-story mi accoutn medium loj.rus@gmail.com
        setContentView(R.layout.activity_chat_conversation)

        // set other user image, email, name
        ChatConversationObject(this, this).initChatConversationFunction()

        // create conversation if no exsist and set realChannelId FIREBASE FIRESTORE, no lo uso, uso el firebase !!!
        // ShowMeMessageFromThisConversation(this, this).showMeMessages()

        // guardamos nuevo mensage
        SaveNewMessageFuncionality(this, this).initChatConversationActivity()

        // create conversation if no exsist and set realChannelId FIREBASE DATABASE
        CreateChatChannelFirebase(this).createOrGetChatChannle(SearchFragment.userConversation?.id!!){ channelId ->

            realChannelId = channelId

            val query: Query = FirebaseFirestore.getInstance()
                .collection("conversation")
                .document("conversation")
                .collection(realChannelId!!)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(limitMessages)

            val options: FirestoreRecyclerOptions<Message> = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()

            val lM = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
            recyclerViewMessages.setHasFixedSize(true)
            lM.stackFromEnd = true
            recyclerViewMessages.layoutManager = lM
            messageFirestoreAdapter =  MessageFirestoreAdapter(options, this, recyclerViewMessages)
            messageFirestoreAdapter.startListening()
            recyclerViewMessages.adapter = messageFirestoreAdapter

            // eliminamos todos los mensages menos  ultimos limitMessages
            DeleteOldMessages(this).deleteMessagesFromThisConversation(realChannelId!!)
        }


    }

    companion object{
        var realChannelId: String? = null
        var miUID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val limitMessages: Long = 111
    }


    override fun onStop() {
        super.onStop()
        messageFirestoreAdapter.stopListening()
    }
}