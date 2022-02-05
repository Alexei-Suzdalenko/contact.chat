package contact.messager.util.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import contact.messager.R
import contact.messager.util.classes.User
import de.hdodenhof.circleimageview.CircleImageView
class SearchedUsersAdapter(val context: Context, val searchedUsers: MutableList<User>): BaseAdapter() {
    override fun getCount(): Int = searchedUsers.size
    override fun getItem(position: Int): Any { return searchedUsers[position] }
    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.searched_item  , parent, false)

        layoutView.findViewById<TextView>(R.id.searchNameUser).text = searchedUsers[position].name
        layoutView.findViewById<TextView>(R.id.ageNameUser).text = searchedUsers[position].age

        Glide.with(context).load(searchedUsers[position].image)
            .into(layoutView.findViewById<CircleImageView>(R.id.userImageProfile))

        return layoutView
    }

}