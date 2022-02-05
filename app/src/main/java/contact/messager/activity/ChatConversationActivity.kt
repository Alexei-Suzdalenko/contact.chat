package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.api.CreateChatChannelFirebase
import contact.messager.util.adapter.MessageFirestoreAdapter
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import contact.messager.R
import contact.messager.util.`class`.App.Companion.realChannelId
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.api.AddNewMessageFirestore

class ChatConversationActivity : AppCompatActivity() {
    lateinit var messageFirestoreAdapter: MessageFirestoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        realChannelId = null

        // set other user image, age, name
        ChatConversationObject(this, this).initChatConversationFunction()

        messageBtnChat.setOnClickListener {
            val textMessage = inputMessage.text.toString()
            if (textMessage.isNotBlank() && textMessage.isNotEmpty()) {           Log.d("realChannelId", "realChannelId" + textMessage)
                if(realChannelId == null ){
                    // create or get channel id from current users conversation
                    CreateChatChannelFirebase(this).createOrGetChatChannle(userConversation?.id!!){ id ->
                        realChannelId = id
                        AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                            if(it == "ok")  inputMessage.setText("")
                        }
                    }
                } else {
                    AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                        if(it == "ok")  inputMessage.setText("")
                    }
                }
            }
        }

     //   // create conversation if no exsist and set realChannelId  FIRESTORE, no lo uso, uso el firebase !!!
     //   // ShowMeMessageFromThisConversation(this, this).showMeMessages()
//


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
        var miUID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val limitMessages: Long = 111
    }


    override fun onStop() {
        super.onStop()
       // messageFirestoreAdapter.stopListening()
    }
}