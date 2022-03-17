package contact.messager.util.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.util.clas.App
import contact.messager.util.clas.App.Companion.userConversation
import de.hdodenhof.circleimageview.CircleImageView
class SearchedUsersAdapter(val context: Context): BaseAdapter() {
    override fun getCount(): Int = App.usersSearched.size
    override fun getItem(position: Int): Any { return App.usersSearched[position] }
    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutView = LayoutInflater.from(context).inflate(R.layout.searched_item  , parent, false)

        layoutView.findViewById<TextView>(R.id.searchNameUser).text = App.usersSearched[position].name
        layoutView.findViewById<TextView>(R.id.ageNameUser).text = App.usersSearched[position].age
        Glide.with(context).load(App.usersSearched[position].image).into(layoutView.findViewById<CircleImageView>(R.id.userImageProfile))

        layoutView.setOnClickListener {
            userConversation = App.usersSearched[position];
            context.startActivity(Intent(context, VisitOthrerUserPerfilActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        return layoutView
    }



}