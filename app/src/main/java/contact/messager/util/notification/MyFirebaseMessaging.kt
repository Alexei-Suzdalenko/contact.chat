package contact.messager.util.notification
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import contact.messager.R
import contact.messager.activity.ChatConversationActivity
import contact.messager.util.classes.App
import contact.messager.util.classes.User
import java.lang.Math.random

class MyFirebaseMessaging: FirebaseMessagingService() {
    var notId:         Int = 0
    var chatId:       String = ""
    var sender:      String = ""
    var receiver:    String = ""
    var userName: String = ""
    var message:   String = ""
    var image:       String = ""
    lateinit var intent: Intent
    var firebaseUser: FirebaseUser? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        intent = Intent(applicationContext, ChatConversationActivity::class.java); intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        chatId = remoteMessage.data["chatId"].toString()
        sender = remoteMessage.data["sender"].toString()
        receiver = remoteMessage.data["receiver"].toString()
        userName = remoteMessage.data["userName"].toString()
        image = remoteMessage.data["image"].toString()
        message = remoteMessage.data["message"].toString()

        intent.putExtra("chatId", chatId)
        intent.putExtra("sender", sender)
        intent.putExtra("receiver", receiver)
        intent.putExtra("userName", userName)
        intent.putExtra("image", image)

        notId = App.getIdNoification(sender)
        Log.d("datadatadata", "data " + remoteMessage.data.toString() )

        if( firebaseUser != null && sender == firebaseUser!!.uid){
                if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O) sendOreoNotificatioin(remoteMessage)
                else sendNotificatioin(remoteMessage)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotificatioin(remoteMessage: RemoteMessage) {
        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this)
               .setContentIntent(pendingIntent)
               .setContentTitle(userName)
               .setContentText(message)
               .setSmallIcon(R.drawable.message_icon)
               .setAutoCancel(true)
        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        noti.notify(notId, builder.build())
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendOreoNotificatioin(remoteMessage: RemoteMessage) {
        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val oreoNotification = Oreonotification(this)
        val builder: Notification.Builder = oreoNotification.getOreoNotification(userName, message, pendingIntent  )

        oreoNotification.getManager!!.notify(notId, builder.build())
    }

    override fun onNewToken(token: String) {
        if(FirebaseAuth.getInstance().currentUser != null) {
            NotificationWork().saveUserToken()
        }
    }
}



































