package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.api.CreateChatChannelFirebase
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import contact.messager.R
import contact.messager.util.classes.App.Companion.listenerDatabaseChagesActivated
import contact.messager.util.classes.App.Companion.realChannelId
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.api.AddNewMessageFirestore
import contact.messager.util.api.AddNewMessageFirestore.initializaceFirestoreListenerMessager

class ChatConversationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        realChannelId = null
        listenerDatabaseChagesActivated = "no"

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
                            if ( it == "ok" ) inputMessage.setText("")
                        }
                        if(listenerDatabaseChagesActivated == "no"){
                            listenerDatabaseChagesActivated = "yes"
                            initializaceFirestoreListenerMessager(realChannelId!!, this)
                        }
                    }
                } else {
                    AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                        if ( it == "ok" )  inputMessage.setText("")
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



}