package contact.messager.activity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import contact.messager.R
import contact.messager.activity.view_fragment.main.SectionsPagerAdapter
import contact.messager.databinding.ActivityChatBinding
import contact.messager.util.adapter.SearchedUsersAdapter
import contact.messager.util.api.Adds
import contact.messager.util.api.BlockUserFire
import contact.messager.util.clas.App
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.api.SaveUserLocationFirestore
import contact.messager.util.api.SaveUserTime
import contact.messager.util.clas.App.Companion.usersSearched
import contact.messager.util.notification.NotificationWork
import kotlinx.android.synthetic.main.activity_chat.*
import kotlin.system.exitProcess
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var viewPager: ViewPager
    lateinit var context: MainActivity
    var usersBlockedMe = ""; val miId = FirebaseAuth.getInstance().currentUser!!.uid; private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        // App.editor.putString("block", "").apply()
        FirebaseDatabase.getInstance().reference.child("block/$miId").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for( snaps in snapshot.children ){ val valueData = snaps.value; usersBlockedMe += ", $valueData" }
                App.editor.putString("block", usersBlockedMe).apply()
            }
        })

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userConversation = null
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)


        Adds.smartAdd(this)

    }


    override fun onStart() {
        super.onStart()
        val name = App.sharedPreferences.getString("name", "").toString()
        val image = App.sharedPreferences.getString("image", "").toString()
        if( name.isEmpty() || image.isEmpty() ) {
            startActivity(Intent(this, MyProfileActivity::class.java)); finish()
            Toast.makeText(this, resources.getString(R.string.fillThePrefil), Toast.LENGTH_LONG).show()
        } else {
            SaveUserLocationFirestore().saveUserLocation(this)
            NotificationWork().saveUserToken()
            SaveUserTime().saveUserTimeOnline()
        }
    }


}