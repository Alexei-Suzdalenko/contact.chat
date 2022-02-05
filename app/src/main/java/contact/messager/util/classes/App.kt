package contact.messager.util.classes
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
class App: Application() {
    companion object{
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        var userConversation: User? = null
        var typeUserImagePlaceholderFragment = ""
        var realChannelId: String? = null
        var listenerDatabaseChagesActivated = "no"
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("contact-chat", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}


/*
firebase:        desarrollador.web.cantabria.penagos@gmail.com
play console: saron.alexei@gmail.com
password:      alexeis
key :              key0

https://medium.com/new-story mi accoutn medium loj.rus@gmail.com

 */