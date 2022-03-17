package contact.messager.activity
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import contact.messager.R
import contact.messager.util.api.SaveDataImageUserFirebase
import contact.messager.util.api.SaveUserLocationFirestore
import contact.messager.util.api.SaveUserTime
import contact.messager.util.clas.App
import contact.messager.util.notification.NotificationWork
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity() {
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        title = resources.getString(R.string.myProfile)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                SaveDataImageUserFirebase.saveImageProfileUserMyProfile(result.data!!.data, this, this); }
        }

        val utlBackImage = App.sharedPreferences.getString("backImage", "").toString()
        if(utlBackImage.length > 22) Glide.with(this).load(utlBackImage).into(imageBackgroundMyProfile)
        imageBackgroundMyProfile.setOnClickListener { App.typeUserImagePlaceholderFragment = "backImage"; prepareIntentLauncher(); }

        val profImage = App.sharedPreferences.getString("image", "").toString()
        if(profImage.length > 22) Glide.with(this).load(profImage).into(userImageProfileMyProfile)
        userImageProfileMyProfile.setOnClickListener { App.typeUserImagePlaceholderFragment = "image"; prepareIntentLauncher(); }

        userNameProfileMyProfile.text = App.sharedPreferences.getString("name", "").toString() + " " + App.sharedPreferences.getString("age", "").toString()

        val userNameS = App.sharedPreferences.getString("name", "").toString()
        if(userNameS.length > 2) editTextNameMyProfile.setText(userNameS)

        editTextCityMyProfile.text = App.sharedPreferences.getString("locality", "...").toString()

        val userAgeS = App.sharedPreferences.getString("age", "").toString()
        editTextAgeMyProfile.setText(userAgeS)

        val userStatusS = App.sharedPreferences.getString("status", "").toString()
        if(userStatusS.length > 2) editTextStatusMyProfile.setText(userStatusS)


        perfilSaveUserInfoMyProfile.setOnClickListener {
            val userName = editTextNameMyProfile.text.toString()
            val userAge = editTextAgeMyProfile.text.toString()
            val userStatus = editTextStatusMyProfile.text.toString()

            if(userName.length > 3 && userAge.isNotEmpty() && userStatus.length > 3){
                SaveDataImageUserFirebase.SaveUserInfo(userName, userAge, userStatus) { result ->
                    if (result == "ok") {
                        App.editor.putString("name", userName); App.editor.putString("age", userAge); App.editor.putString("status", userStatus); App.editor.apply(); }
                    startActivity(Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ); finish()
                }
            } else Toast.makeText(this, resources.getString(R.string.fillUserData), Toast.LENGTH_LONG).show()
        }

        writeYourCommentMyProfile.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=contact.messager"))); }

        deleteUserProfileMyProfile.setOnClickListener {}
    }

    fun prepareIntentLauncher(){ val intent = Intent(); intent.type = "image/"; intent.action = Intent.ACTION_GET_CONTENT; resultLauncher.launch(intent)}

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            SaveUserLocationFirestore().saveUserLocation(this)
            NotificationWork().saveUserToken()
            SaveUserTime().saveUserTimeOnline()
        }
        /*      si el telefono es nuevo o entra usuarion que tiene datos en la base pero no en el telefono                                           */
        val userName = App.sharedPreferences.getString("name", "").toString()
        if(userName.isEmpty() || userName.isBlank()){
            SaveUserTime().intentGetUserDataIfExsistEnDataBase{ user ->
                if(user != null ) {
                    if(user.backImage.length > 22) {
                        Glide.with(this).load(user.backImage).into(imageBackgroundMyProfile)
                        App.editor.putString("backImage", user.backImage); App.editor.apply()
                    }
                    if(user.image.length > 22) {
                        Glide.with(this).load(user.image).into(userImageProfileMyProfile)
                        App.editor.putString("image", user.image); App.editor.apply()
                    }
                    if(user.name !=  "null" && user.age != "null") userNameProfileMyProfile.text = user.name + " " + user.age
                    if(user.name != "null") editTextNameMyProfile.setText(user.name)
                    if(user.locality != "null") editTextCityMyProfile.text = user.locality
                    if(user.age != "null") editTextAgeMyProfile.setText(user.age)
                    if(user.status != "null") editTextStatusMyProfile.setText(user.status)
                }
            }
        }
    }
}