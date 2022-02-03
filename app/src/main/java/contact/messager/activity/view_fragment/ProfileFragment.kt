package contact.messager.activity.view_fragment
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import contact.messager.activity.ConversSearchProfileActivity
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.util.`class`.App.Companion.typeUserImagePlaceholderFragment

class ProfileFragment(private val fr: FragmentProfileBinding, val context: Context, val activity: FragmentActivity, val resultLauncher: ActivityResultLauncher<Intent>) {
// val textViewEmail = fragment.userEmailProfile
// val editTextName = fragment.editTextName

    fun initProfileFragment(){
        fr.imageBackground.setOnClickListener { typeUserImagePlaceholderFragment = "backImage"; prepareIntentLauncher(); }

        fr.userImageProfile.setOnClickListener { typeUserImagePlaceholderFragment = "image"; prepareIntentLauncher(); }

        fr.perfilSaveUserInfo.setOnClickListener {
            context.startActivity(Intent(context, ConversSearchProfileActivity::class.java).setFlags(FLAG_ACTIVITY_NEW_TASK)); activity.finish()
        }

        fr.writeYourComment.setOnClickListener {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=contact.messager")))
        }

        fr.deleteUserProfile.setOnClickListener {

        }

     // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        //startActivity(browserIntent);

    //       textViewEmail.text = App.sharedPreferences.getString("email", "").toString()
    //       editTextName.setText( App.sharedPreferences.getString("name", "").toString())
    //       val image = App.sharedPreferences.getString("image", "").toString()
    //   Glide.with( context ).load( image).into( userImageProfile )
    //       val imageBack = App.sharedPreferences.getString("backImage", "").toString()
    //   Glide.with( context ).load( imageBack).into( imageBackground )

    //   // save user images profile image and background image
    //   imageBackground.setOnClickListener { typeUserImagePlaceholderFragment = "backImage"; prepareIntentLauncher() }
    //   userImageProfile.setOnClickListener { typeUserImagePlaceholderFragment = "image"; prepareIntentLauncher() }

    //   // save new name if changed name in profile activity
    //   editTextName.addTextChangedListener {
    //       val newUserName = editTextName.text.toString()
    //       if ( newUserName.isEmpty()) return@addTextChangedListener
    //       val map = HashMap<String, Any>()
    //       map["name"] = newUserName
    //       Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid).updateChildren(map).addOnCompleteListener {
    //           if( it.isSuccessful ){ editor.putString("name", newUserName); editor.apply() }}
    //   }
    }


    fun prepareIntentLauncher(){ val intent = Intent(); intent.type = "image/"; intent.action = Intent.ACTION_GET_CONTENT; resultLauncher.launch(intent)}
}