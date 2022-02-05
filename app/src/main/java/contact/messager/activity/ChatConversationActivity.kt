package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.api.CreateChatChannelFirebase
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import contact.messager.R
import contact.messager.util.classes.App.Companion.listenerDatabaseChagesActivated
import contact.messager.util.classes.App.Companion.realChannelId
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.api.AddNewMessageFirestore
import contact.messager.util.api.AddNewMessageFirestore.initializaceFirestoreListenerMessager
import contact.messager.util.notification.ServiceNotification

class ChatConversationActivity : AppCompatActivity() {
    var firebaseUserId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
        realChannelId = null
        listenerDatabaseChagesActivated = "no"

        if(intent.getStringExtra("chatId").toString().length > 7){
            realChannelId = intent.getStringExtra("chatId").toString()
            userConversation!!.id = intent.getStringExtra("receiver").toString()
            userConversation!!.name = intent.getStringExtra("userName").toString()
            userConversation!!.name = intent.getStringExtra("image").toString()
            userConversation!!.age = intent.getStringExtra("age").toString()
        }
      // intent.getExtra("chatId", chatId)
      // intent.getExtra("sender", sender)
      // intent.getExtra("receiver", receiver)
      // intent.getExtra("userName", userName)
      // intent.getExtra("image", image)

        // set other user image, age, name
        ChatConversationObject(this, this).initChatConversationFunction()

        messageBtnChat.setOnClickListener {
            val textMessage = inputMessage.text.toString()
            if (textMessage.isNotBlank() && textMessage.isNotEmpty()) {
                if(realChannelId == null ){
                    // create or get channel id from current users conversation
                    CreateChatChannelFirebase().createOrGetChatChannle(userConversation?.id!!) { id ->
                        realChannelId = id
                        AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                            if ( it == "ok" ) {
                                inputMessage.setText("")
                                sendNotification(textMessage)
                            }
                        }
                        if(listenerDatabaseChagesActivated == "no"){
                            listenerDatabaseChagesActivated = "yes"
                            initializaceFirestoreListenerMessager(realChannelId!!, this)
                        }
                    }
                } else {
                    AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                        if ( it == "ok" ) {
                            inputMessage.setText("")
                            sendNotification(textMessage)
                        }
                    }
                }
            }
        }


        CreateChatChannelFirebase().createOrGetChatChannle(userConversation?.id!!) { id ->
            realChannelId = id
            if(listenerDatabaseChagesActivated == "no"){
                listenerDatabaseChagesActivated = "yes"
                initializaceFirestoreListenerMessager(realChannelId!!, this)
            }
        }

     //   // create conversation if no exsist and set realChannelId  FIRESTORE, no lo uso, uso el firebase !!!
     //   // ShowMeMessageFromThisConversation(this, this).showMeMessages()
    }

    private fun sendNotification(textMessage: String){
        ServiceNotification().sentNotification(
            realChannelId!!,
            userConversation!!.id,
            firebaseUserId,
            userConversation!!.name,
            textMessage,
            userConversation!!.image,
            userConversation!!.token,
            userConversation!!.age
        )
    }


}