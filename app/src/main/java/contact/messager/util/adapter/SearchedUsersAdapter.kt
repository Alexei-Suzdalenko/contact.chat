package contact.messager.util.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.util.clas.App
import contact.messager.util.clas.App.Companion.userConversation
import de.hdodenhof.circleimageview.CircleImageView
class SearchedUsersAdapter(val context: Context): RecyclerView.Adapter<SearchedUsersAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
      // val name: TextView = view.findViewById(R.id.searchNameUser)
      // val status: TextView = view.findViewById(R.id.ageNameUser)
        val image: CircleImageView = view.findViewById(R.id.userImageProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.searched_item  , parent, false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      // holder.name.text  = App.usersSearched[position].name
      // holder.status.text = App.usersSearched[position].status
        Glide.with(context).load(App.usersSearched[position].image).into(holder.image)
        holder.image.setOnClickListener {
            userConversation = App.usersSearched[position];
            context.startActivity(Intent(context, VisitOthrerUserPerfilActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun getItemCount(): Int = App.usersSearched.size
}