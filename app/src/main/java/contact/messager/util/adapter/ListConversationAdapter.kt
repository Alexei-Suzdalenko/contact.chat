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
import contact.messager.util.clas.User
import de.hdodenhof.circleimageview.CircleImageView

class ListConversationAdapter(val context: Context, val listInfo: MutableList<User>): BaseAdapter() {
    override fun getCount(): Int = listInfo.size
    override fun getItem(position: Int): Any = listInfo[position]
    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.conversation_item, parent, false)

        layoutView.findViewById<TextView>(R.id.conversationName).text = listInfo[position].name
        layoutView.findViewById<TextView>(R.id.conversationEmail).text = listInfo[position].age

        if(listInfo[position].image.length > 22) {
            Glide.with(context).load(listInfo[position].image).into(layoutView.findViewById<CircleImageView>(R.id.userImageProfile))
        }
        return layoutView
    }
}