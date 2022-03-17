package contact.messager.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import contact.messager.R
import contact.messager.util.api.BlockUserFire
import contact.messager.util.clas.App
import kotlinx.android.synthetic.main.activity_block.*
import kotlin.system.exitProcess

class BlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        if( intent.getStringExtra("action").toString() == "block") { title = resources.getString(R.string.blockThisUser); reportAbuse.text = resources.getString(R.string.block)
        }  else { title = resources.getString(R.string.reportUserBehavior); reportAbuse.text = resources.getString(R.string.report) }

        val blockThisId = intent.getStringExtra("userId").toString()

        reportAbuse.setOnClickListener {
            if( intent.getStringExtra("action").toString() == "block"){
                // aqui guardo el id para que a este usuario no le vea yo
                 var usersBlocked = App.sharedPreferences.getString("block", "").toString()
                       usersBlocked += " , $blockThisId "
                 App.editor.putString("block", usersBlocked).apply()

                BlockUserFire().block(blockThisId){ closeThisActivity() }
            }
            val messageReport = resources.getString(R.string.infoSented)
            Toast.makeText(this, messageReport, Toast.LENGTH_LONG).show()
            FirebaseDatabase.getInstance().reference.child("badUsers").child(blockThisId).setValue(textAbuse.text.toString()).addOnSuccessListener { closeThisActivity() }
        }
    }

    private fun closeThisActivity(){
        Thread{
            Thread.sleep(1500)
            runOnUiThread {
                exitProcess(0)
            }
        }.start()
    }
}