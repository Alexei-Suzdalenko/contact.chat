package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.util.components.ChatConversationActivity.ChatConversationObject
import contact.messager.util.api.CreateChatChannelFirebase
import kotlinx.android.synthetic.main.activity_chat_conversation.*

import contact.messager.R
import contact.messager.util.clas.App.Companion.listenerDatabaseChagesActivated
import contact.messager.util.clas.App.Companion.realChannelId
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.api.AddNewMessageFirestore
import contact.messager.util.api.AddNewMessageFirestore.initializaceFirestoreListenerMessager
import contact.messager.util.api.Adds
import contact.messager.util.clas.App
import contact.messager.util.clas.User
import contact.messager.util.notification.ServiceNotification

class ChatConversationActivity : AppCompatActivity() {
    var miId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_conversation)
        miId = FirebaseAuth.getInstance().currentUser!!.uid
        realChannelId = null
        listenerDatabaseChagesActivated = "no"

        if(intent.getStringExtra("chatId").toString().length > 7){
            val user = User(intent.getStringExtra("receiver").toString(), intent.getStringExtra("age").toString(), "", intent.getStringExtra("image").toString(), "", intent.getStringExtra("userName").toString(), "", "", "", intent.getStringExtra("miToken").toString(),)
            userConversation = user
            realChannelId = intent.getStringExtra("chatId").toString()
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
                        if(listenerDatabaseChagesActivated == "no"){ listenerDatabaseChagesActivated = "yes"; initializaceFirestoreListenerMessager(realChannelId!!, this) }
                    }
                } else {
                    AddNewMessageFirestore.addNewMessageFirestore(textMessage, this){
                        if ( it == "ok" ) { inputMessage.setText(""); sendNotification(textMessage);
                            FirebaseFirestore.getInstance().collection("enganched_chat").document(userConversation!!.id).collection("channel").document(miId).set(mapOf("id" to realChannelId))
                        }
                    }
                }
            }
            Adds.startM(this)
        }

        // cuando entro desde la notificaciones
        if(realChannelId != null){
            if(listenerDatabaseChagesActivated == "no"){ listenerDatabaseChagesActivated = "yes"; initializaceFirestoreListenerMessager(realChannelId!!, this) }
        } else {
            // entro desde listado de usuarios o listado de conversaciones
            CreateChatChannelFirebase().getConversationChatChannel(userConversation?.id!!) { id ->
                if(id != null){ realChannelId = id
                    if(listenerDatabaseChagesActivated == "no"){ listenerDatabaseChagesActivated = "yes"; initializaceFirestoreListenerMessager(realChannelId!!, this) }
                }
            }
        }

        //    Log.d("realChannelId", "realChannelId" + realChannelId)                             // 1648896816099
        //    Log.d("realChannelId", "userConversation?.id = " + userConversation?.id) // kyJ3PQ8i09bS4mMzKIsmVBJEnoI2
        //    Log.d("realChannelId", "miId = " + firebaseUserId)                                    // aBnagawRjXYcquKygIpA4EKm0Se2
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
            miId,
            App.sharedPreferences.getString("name", "").toString(),
            textMessage,
            App.sharedPreferences.getString("image", "").toString(),
            userConversation!!.token,
            App.sharedPreferences.getString("age", "").toString(),
            App.sharedPreferences.getString("token", "").toString()
        )
    }


}