package contact.chat.util.components.LoginActivity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.chat.LoginAcivity
import contact.chat.util.assets.App

class EstateLoginActivityComponent(val context: Context, val activity: LoginAcivity) {

    fun initCurrentStateActivity(){
        val auth: FirebaseAuth = Firebase.auth
        val user = auth.currentUser

        Log.d("alexei", "EstateLoginActivityComponent EstateLoginActivityComponent EstateLoginActivityComponent")
   try {
       Log.d("alexei", "name "  + user!!.displayName.toString())
       Log.d("alexei", "email "  + user!!.email.toString())
       Log.d("alexei", "zze "  + user!!.zze().toString())
       Log.d("alexei", "uid "  + user!!.uid.toString())
       Log.d("alexei", "phone "  + user!!.phoneNumber.toString())


       Log.d("alexei", "photo "  + user!!.photoUrl.toString())
       Log.d("alexei", "provider "  + user!!.providerId.toString())
   }catch (e: Exception){}

        val userName = App.sharedPreferences.getString("userName", "none").toString()
        val userEmail = App.sharedPreferences.getString("userEmail", "none").toString()

        if(userEmail != "none" || userEmail != "none"){

        }
    }
}