package contact.messager.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import contact.messager.R
import contact.messager.databinding.ActivityChatBinding
import contact.messager.activity.view_fragment.main.SectionsPagerAdapter
import contact.messager.util.`class`.App
import contact.messager.util.api.SaveUserLocationFirebase
import java.util.*


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
            if (currentUser == null) { App.editor.putString("name", "").apply()
                startActivity(Intent(this, LoginAcivity::class.java)); finish()
            }
        }
        SaveUserLocationFirebase().saveUserLocation(this)
    }

    override fun onStart() {
        super.onStart()
        val userName = App.sharedPreferences.getString("name", "").toString()
        Toast.makeText(this, resources.getString(R.string.fillThePrefil), Toast.LENGTH_LONG).show()
        if(userName.isEmpty()) { viewPager.currentItem = 2; }
    }

}