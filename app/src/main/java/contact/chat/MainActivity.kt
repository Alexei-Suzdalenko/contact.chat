package contact.chat
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import contact.chat.ui.main.SectionsPagerAdapter
import contact.chat.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val asdf = 11

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }


    //   // Write a message to the database
    //   val database = Firebase.database
    //   val myRef = database.getReference("message")
    //   myRef.setValue("Hello, World!")


    //   val db = Firebase.firestore
    //   val user = hashMapOf("first" to "Ada", "last" to "Lovelace", "born" to 1815)

    //   db.collection("users").add(user).addOnSuccessListener { documentReference ->
    //           Log.d("tag", "DocumentSnapshot added with ID: ${documentReference.id}")
    //       }
    //       .addOnFailureListener { e ->
    //           Log.w("tag", "Error adding document", e)
    //       }

    }
}