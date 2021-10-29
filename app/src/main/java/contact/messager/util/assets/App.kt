package contact.messager.util.assets
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
class App: Application() {
    companion object{
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("contact-chat", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}