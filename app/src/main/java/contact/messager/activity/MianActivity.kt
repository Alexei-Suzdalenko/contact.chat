package contact.messager.activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import contact.messager.R
import contact.messager.activity.view_fragment.main.SectionsPagerAdapter
import contact.messager.databinding.ActivityChatBinding
import contact.messager.util.classes.App
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.api.SaveUserLocationFirestore
import contact.messager.util.api.SaveUserTime
import contact.messager.util.notification.NotificationWork


class MianActivity : AppCompatActivity() {
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

        /// delete this
        /*
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener { view ->
            Firebase.auth.signOut()
            val currentUser = Firebase.auth.currentUser
            if (currentUser == null) { App.editor.putString("name", "").apply()
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }
         */

        /*
        ServiceNotification().sentNotification(sender, receiver, token, messageText, image, name)

        ServiceNotification().sentNotification(
            "",
            "ZVSY0mlq6CNiTHI3j3Pd662zzm33",
            "",
            "user ",
            "message",
            "",
              "c5Vi15dARxCBJ4w2KxUCy-:APA91bH-hz_n7ei3zYAqPbJcvOshvDniZRUswpOijictHLoJf5q9l5leJr05e5bCWz-dIZXtDmVdenuCsBQHSz9fkKZ9ZZGx8rVXC0iglUcrNMbzZOM2cQ7MOqDqy8a2YSbiG_979so9",
            "9"
            )
       */
    }

    override fun onStart() {
        super.onStart()
        val userName = App.sharedPreferences.getString("name", "").toString()
        if(userName.isEmpty()) {
            viewPager.currentItem = 2
            Toast.makeText(this, resources.getString(R.string.fillThePrefil), Toast.LENGTH_LONG).show()
        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            SaveUserLocationFirestore().saveUserLocation(this)
            NotificationWork().saveUserToken()
            SaveUserTime().saveUserTimeOnline()
        }

    }


}