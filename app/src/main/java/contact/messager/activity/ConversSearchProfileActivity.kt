package contact.messager.activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.R
import contact.messager.activity.view_fragment.main.SectionsPagerAdapter
import contact.messager.databinding.ActivityChatBinding
import contact.messager.util.classes.App
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.api.SaveUserLocationFirebase
import contact.messager.util.notification.NotificationWork
import contact.messager.util.notification.ServiceNotification


class ConversSearchProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
   private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userConversation = null

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab


        /// delete this
        fab.setOnClickListener { view ->
            Firebase.auth.signOut()
            val currentUser = Firebase.auth.currentUser
            if (currentUser == null) { App.editor.putString("name", "").apply()
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }
        SaveUserLocationFirebase().saveUserLocation(this)
        NotificationWork().saveUserToken()

        /*
        ServiceNotification().sentNotification(sender, receiver, token, messageText, image, name)
*/
    //    ServiceNotification().sentNotification("sender",
    //        "zud208LtrQWvCkj03RVhSmnuyqG2",
    //        "fT1efkbLQUy7cSv8kz-ZMP:APA91bEoXfa1L63LlCR-SmetcJ4r5oeiSpTji1H-U_TM54xkgaNXY2XvYccNixnmn5Ku7Poa0TL3XgRYKRwW9POvpA7JyGPXdthaEwVkSOK0msobmF0ujNAHSqKvYW4_Hj1MmEhEJL81",
    //        "messageText",
    //        "image",
    //        "name")
    }

    override fun onStart() {
        super.onStart()
        val userName = App.sharedPreferences.getString("name", "").toString()
        if(userName.isEmpty()) {
            viewPager.currentItem = 2
            Toast.makeText(this, resources.getString(R.string.fillThePrefil), Toast.LENGTH_LONG).show()
        }
    }

    fun showUsersOptions(userName: String, onComplete: (result: String) -> Unit) {
       val alert = AlertDialog.Builder(this)
            .setTitle(userName)
            .setMessage("Are you sure?")
            .setPositiveButton(resources.getString(R.string.writeMessageChat)) { _, _-> onComplete("chat") }
            .setNegativeButton(resources.getString(R.string.profile)) { _, _ -> onComplete("perfil") }
       alert.show()
    }

}