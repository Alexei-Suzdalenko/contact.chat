package contact.messager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.databinding.ActivityChatBinding
import contact.messager.ui.main.SectionsPagerAdapter
import contact.messager.util.assets.App
import contact.messager.util.assets.User

class ConversSearchProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
   private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            // editor.putString("name", "none"); editor.putString("email", "none"); editor.putString("source", "none"); editor.apply()
            if (currentUser == null) {
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }


    }

    override fun onStart() {
        super.onStart()
        val userName = App.sharedPreferences.getString("name", "").toString()
        if(userName.isEmpty()) {
            viewPager.currentItem = 2
        }
    }

}