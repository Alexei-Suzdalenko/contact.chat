package contact.messager.util.assets
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
class App: Application() {
    companion object{
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        var userConversation: User? = null
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("contact-chat", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}


/*
firebase loj.rus@gmail.com
https://console.firebase.google.com/u/0/project/contact-messanger/overview?hl=es-419

password: alexeis
key : key0

quiero subir a google play desarrollador.web.cantabria.penagos@gmail.com

 */