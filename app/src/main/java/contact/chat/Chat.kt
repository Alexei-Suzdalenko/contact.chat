package contact.chat

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.chat.ui.main.SectionsPagerAdapter
import contact.chat.databinding.ActivityChatBinding
import contact.chat.util.assets.App.Companion.editor

class Chat : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab



        fab.setOnClickListener { view ->
            Firebase.auth.signOut()
            val currentUser = Firebase.auth.currentUser
           // editor.putString("name", "none"); editor.putString("email", "none"); editor.putString("source", "none"); editor.apply()
            if( currentUser == null ){
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }
    }
}