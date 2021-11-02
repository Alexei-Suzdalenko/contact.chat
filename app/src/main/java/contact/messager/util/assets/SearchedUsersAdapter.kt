package contact.messager.util.assets
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import contact.messager.R
import de.hdodenhof.circleimageview.CircleImageView
class SearchedUsersAdapter(val context: Context, val searchedUsers: MutableList<User>): BaseAdapter() {
    override fun getCount(): Int = searchedUsers.size
    override fun getItem(position: Int): Any { return searchedUsers[position] }
    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.searched_item  , parent, false)

        layoutView.findViewById<TextView>(R.id.searchNameUser).text = searchedUsers[position].email
        Glide.with(context)
            .load(searchedUsers[position].image)
            .into(layoutView.findViewById<CircleImageView>(R.id.userImageProfile))

        return layoutView
    }

}