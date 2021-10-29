package contact.messager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.ui.main.SectionsPagerAdapter
import contact.messager.databinding.ActivityChatBinding
import contact.messager.util.components.LoginActivity.LoginActivityGoogleComponent
import contact.messager.util.repository.SaveNewUserRepository

class Chat : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
  //  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

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
            if (currentUser == null) {
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }

    }


}