package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.api.CreateChatChannelFirebase
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import contact.messager.R
import contact.messager.util.clas.App.Companion.listenerDatabaseChagesActivated
import contact.messager.util.clas.App.Companion.realChannelId
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.api.AddNewMessageFirestore
import contact.messager.util.api.AddNewMessageFirestore.initializaceFirestoreListenerMessager
import contact.messager.util.clas.App
import contact.messager.util.clas.User
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
            val user = User(
                intent.getStringExtra("receiver").toString(),
                intent.getStringExtra("age").toString(), "",
                intent.getStringExtra("image").toString(), "",
                intent.getStringExtra("userName").toString(), "", "", "",
                intent.getStringExtra("token").toString(),
            )
            userConversation = user
        }

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
                        if(listenerDatabaseChagesActivated == "no"){ listenerDatabaseChagesActivated = "yes"
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
                 if(listenerDatabaseChagesActivated == "no"){ listenerDatabaseChagesActivated = "yes"
                     initializaceFirestoreListenerMessager(realChannelId!!, this)
                 }
             }


    }

    private fun sendNotification(textMessage: String){
     //   Log.d("notificationSataA",
     //       "realChannelId=" +realChannelId!! + ", " +
     //       "userConversation!!.id="+userConversation!!.id + ", " +
     //       "firebaseUserId="+firebaseUserId + ", " +
     //       "userConversation!!.name="+userConversation!!.name + ", " +
     //       "textMessage="+textMessage + ", " +
     //       "image="+App.sharedPreferences.getString("image", "").toString() + ", " +
     //       "token="+userConversation!!.token + ", " +
     //       "age="+userConversation!!.age
     //   )

        ServiceNotification().sentNotification (
            realChannelId!!,
            userConversation!!.id,
            firebaseUserId,
            userConversation!!.name,
            textMessage,
            App.sharedPreferences.getString("image", "").toString(),
            userConversation!!.token,
            userConversation!!.age
        )
    }


}