package contact.messager.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.util.clas.App
import kotlinx.android.synthetic.main.activity_show_images.*

class ShowImages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_images)
        title = App.userConversation?.name.toString()

        if(intent.getStringExtra("image").toString() == "back"){
            if(App.userConversation?.backImage.toString().length>11) Glide.with(this).load(App.userConversation?.backImage.toString()).into(showImage)
        } else {
            if(App.userConversation?.image.toString().length>11) Glide.with(this).load(App.userConversation?.image.toString()).into(showImage)
        }
    }
}