package contact.messager.util.assets
import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import contact.messager.ChatConversationActivity
import contact.messager.R
import java.text.SimpleDateFormat
import java.util.*

class MessageFirestoreAdapter(val options: FirestoreRecyclerOptions<Message>, val activity: ChatConversationActivity, val recyclerViewMessages: RecyclerView): FirestoreRecyclerAdapter<Message, MessageFirestoreAdapter.viewInner>(options) {
    val miUserUID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var listMessages : MutableList<Message> = mutableListOf()

    // dicidimos typo de layout left o right
    override fun getItemViewType(position: Int): Int {
        if(miUserUID == options.snapshots[position].sender) return 0
        return 1
    }

    class viewInner(view: View) : RecyclerView.ViewHolder(view) {
        val textViewM = view.findViewById<TextView>(R.id.textView_message_text)
        val textViewT = view.findViewById<TextView>(R.id.textView_message_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): viewInner {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.mes_item, parent, false)
        if (i == 0){
            view.findViewById<RelativeLayout>(R.id.message_root).apply {
                setBackgroundResource(R.drawable.background_left)
                val lParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.END)
                this.layoutParams = lParams
            }
        }
        return viewInner(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: viewInner, i: Int, model: Message) {
        holder.textViewM.text = options.snapshots[i].text // asReversed
        holder.textViewT.text = SimpleDateFormat("HH:mm").format(Date( options.snapshots[i].time.toLong() )).toString() + " " +  SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format( options.snapshots[i].time.toLong()  )
    }

    override fun onDataChanged() {
        recyclerViewMessages.scrollToPosition(0)
    }

    override fun onError(e: FirebaseFirestoreException) {}



}