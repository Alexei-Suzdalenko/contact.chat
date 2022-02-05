package contact.messager.activity.view_fragment
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.activity.ConversSearchProfileActivity
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.util.classes.App
import contact.messager.util.classes.App.Companion.editor
import contact.messager.util.classes.App.Companion.sharedPreferences
import contact.messager.util.classes.App.Companion.typeUserImagePlaceholderFragment
import contact.messager.util.api.SaveDataImageUserFirebase.SaveUserInfo

class ProfileFragment(private val fr: FragmentProfileBinding, val context: Context, val activity: FragmentActivity, val resultLauncher: ActivityResultLauncher<Intent>) {
    /*
        activity: ConversSearchProfileActivity
        tab:        Profile
     */

    @SuppressLint("SetTextI18n")
    fun initProfileFragment(){
        val utlBackImage = App.sharedPreferences.getString("backImage", "").toString()
              if(utlBackImage.length > 22) Glide.with(context).load(utlBackImage).into(fr.imageBackground)
        fr.imageBackground.setOnClickListener { typeUserImagePlaceholderFragment = "backImage"; prepareIntentLauncher(); }

        val profImage = sharedPreferences.getString("image", "").toString()
              if(profImage.length > 22) Glide.with(context).load(profImage).into(fr.userImageProfile)
        fr.userImageProfile.setOnClickListener { typeUserImagePlaceholderFragment = "image"; prepareIntentLauncher(); }

        fr.userNameProfile.text = sharedPreferences.getString("name", "").toString() + " " + sharedPreferences.getString("age", "").toString()
        val userNameS = sharedPreferences.getString("name", "").toString()
              if(userNameS.length > 2) fr.editTextName.setText(userNameS)
        val userAgeS = sharedPreferences.getString("age", "").toString()
              fr.editTextAge.setText(userAgeS)
        val userStatusS = sharedPreferences.getString("status", "").toString()
             if(userStatusS.length > 2) fr.editTextStatus.setText(userStatusS)
        fr.editTextCity.text = sharedPreferences.getString("locality", "...").toString()

        fr.perfilSaveUserInfo.setOnClickListener {
            val userName = fr.editTextName.text.toString()
            val userAge = fr.editTextAge.text.toString()
            val userStatus = fr.editTextStatus.text.toString()

            if(userName.length > 3 && userAge.isNotEmpty() && userStatus.length > 3){
                SaveUserInfo(userName, userAge, userStatus){ result ->
                    if(result == "ok"){ editor.putString("name", userName); editor.putString("age", userAge); editor.putString("status",  userStatus); editor.apply(); }
                    context.startActivity(Intent(context, ConversSearchProfileActivity::class.java).setFlags(FLAG_ACTIVITY_NEW_TASK)); activity.finish()
                }
            } else Toast.makeText(context, activity.resources.getString(R.string.fillUserData), Toast.LENGTH_LONG).show()
        }

        fr.writeYourComment.setOnClickListener {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=contact.messager")))
        }

        fr.deleteUserProfile.setOnClickListener {

        }

    }


    fun prepareIntentLauncher(){ val intent = Intent(); intent.type = "image/"; intent.action = Intent.ACTION_GET_CONTENT; resultLauncher.launch(intent)}
}