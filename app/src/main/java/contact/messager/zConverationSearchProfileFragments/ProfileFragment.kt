package contact.messager.zConverationSearchProfileFragments
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.ui.main.PlaceholderFragment.Companion.typeUserImagePlaceholderFragment
import contact.messager.util.assets.App
import contact.messager.util.assets.App.Companion.editor

class ProfileFragment(val fragment: FragmentProfileBinding, val context: Context, val activity: FragmentActivity, val resultLauncher: ActivityResultLauncher<Intent>) {
    val imageBackground = fragment.imageBackground
    val userImageProfile = fragment.userImageProfile
    val textViewEmail = fragment.userEmailProfile
    val editTextName = fragment.editTextName

    // inits states in profile activity
    fun initProfileFragment(){
            textViewEmail.text = App.sharedPreferences.getString("email", "").toString()
            editTextName.setText( App.sharedPreferences.getString("name", "").toString())
            val image = App.sharedPreferences.getString("image", "").toString()
        Glide.with( context ).load( image).into( userImageProfile )
            val imageBack = App.sharedPreferences.getString("backImage", "").toString()
        Glide.with( context ).load( imageBack).into( imageBackground )

        // save user images profile image and background image
        imageBackground.setOnClickListener { typeUserImagePlaceholderFragment = "backImage"; prepareIntentLauncher() }
        userImageProfile.setOnClickListener { typeUserImagePlaceholderFragment = "image"; prepareIntentLauncher() }

        // save new name if changed name in profile activity
        editTextName.addTextChangedListener {
            val newUserName = editTextName.text.toString()
            if ( newUserName.isEmpty()) return@addTextChangedListener
            val map = HashMap<String, Any>()
            map["name"] = newUserName
            Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful ){ editor.putString("name", newUserName); editor.apply() }}
        }
    }


    fun prepareIntentLauncher(){ val intent = Intent(); intent.type = "image/"; intent.action = Intent.ACTION_GET_CONTENT; resultLauncher.launch(intent)}
}