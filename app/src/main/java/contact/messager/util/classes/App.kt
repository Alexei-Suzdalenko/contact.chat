package contact.messager.util.classes
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class App: Application() {
    /*
    1. cuando acabo de registrar y logear usuario guardo
        -ubicacion
        -time
     */


    companion object{
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        var userConversation: User? = null
        var typeUserImagePlaceholderFragment = ""
        var realChannelId: String? = null
        var listenerDatabaseChagesActivated = "no"
        val textLetters = arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "l", "k", "j", "h", "g", "f", "d", "s", "a", "z", "x", "c", "v", "b", "n", "m")
        val numLetters = arrayOf(1,     2,     3,    4,   5,    9,  10,   11, 12,   13,  14, 15,  16,  17,  18, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 300, 310, 320, 330, 19, 22, 27, 29, 37, 47)

        fun getIdNoification(userKey: String): Int{
            var result = 0
            var letter = ""
            for(i in userKey.indices){
                letter = userKey[i].toString().lowercase(Locale.getDefault())
                for(chatL in textLetters.indices){
                    if(textLetters[chatL].toString() == letter){
                        result += numLetters[chatL]
                    }
                }
            }
            return result
        }
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
C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\pas_alexeis_alias_key0.jks
 */