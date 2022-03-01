package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import contact.messager.R
import contact.messager.util.classes.App
import kotlinx.android.synthetic.main.activity_block.*

class BlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        if( intent.getStringExtra("action").toString() == "block") { title = resources.getString(R.string.blockThisUser); reportAbuse.text = resources.getString(R.string.block)
        }  else { title = resources.getString(R.string.reportUserBehavior); reportAbuse.text = resources.getString(R.string.report) }

        val userId = intent.getStringExtra("userId").toString()

        reportAbuse.setOnClickListener {
            if( intent.getStringExtra("action").toString() == "block"){
                var usersBlocked = App.sharedPreferences.getString("blockUsers", "").toString()
                usersBlocked += " , $userId "
                App.editor.putString("blockUsers", usersBlocked).apply()

               // Toast.makeText(this, userId, Toast.LENGTH_LONG).show()
            }

            val messageReport = resources.getString(R.string.infoSented)
            Toast.makeText(this, messageReport, Toast.LENGTH_LONG).show()
        }
    }
}