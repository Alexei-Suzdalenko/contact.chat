package contact.messager.activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.R
import contact.messager.activity.view_fragment.main.SectionsPagerAdapter
import contact.messager.databinding.ActivityChatBinding
import contact.messager.util.classes.App
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.api.SaveUserLocationFirestore
import contact.messager.util.api.SaveUserTime
import contact.messager.util.notification.NotificationWork


class MainActivity : AppCompatActivity() {
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

/*
        FirebaseFirestore.getInstance().collection("enganched_chat").document("Ej8rRLCWNkVVZjt3k62SHnnuDOe2").get().addOnSuccessListener { document ->
            Log.d("newChatId1", "result: " + document.data)
            Log.d("newChatId1", "result: " + document.data!!.entries.iterator())
            for( d in document.data!!.entries.iterator()){
                Log.d("newChatId1", "result: " + d.key.toString()  + " --- "  + d.value.toString())
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
        val name = App.sharedPreferences.getString("name", "").toString()
        val image = App.sharedPreferences.getString("image", "").toString()
        if( name.isEmpty() || image.isEmpty() ) {
            startActivity(Intent(this, MyProfileActivity::class.java)); finish()
            Toast.makeText(this, resources.getString(R.string.fillThePrefil), Toast.LENGTH_LONG).show()
        }
    }


}