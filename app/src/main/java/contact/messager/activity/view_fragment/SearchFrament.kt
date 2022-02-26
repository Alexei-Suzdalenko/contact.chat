package contact.messager.activity.view_fragment;
import android.content.Context;
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import contact.messager.R
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.MainActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.util.classes.App.Companion.userConversation
import contact.messager.util.adapter.SearchedUsersAdapter
import contact.messager.util.classes.User
import contact.messager.util.api.SaveDataImageUserFirebase.GetListUsers
import contact.messager.util.classes.App

class SearchFragment (val fr: FragmentSearchBinding, val context: Context, val activity: MainActivity){
    lateinit var usersSearched: MutableList<User>

    fun initSearchFragment(){
        GetListUsers{ it ->
            usersSearched = it
            /* listado de usuarios en searched tab */
            fr.listviewSearch.adapter = SearchedUsersAdapter(context, usersSearched)

            /* click en algun user buscado en el tab searched */
            fr.listviewSearch.setOnItemClickListener { _, _, position, _ ->
                userConversation = usersSearched[position]

                val dialogBuilder = AlertDialog.Builder(activity)
                    .setMessage(activity.resources.getString(R.string.areYouSure))
                    .setNegativeButton(context.resources.getString(R.string.writeMessageChat)) { dialog, id ->
                        context.startActivity(Intent(context, ChatConversationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        dialog.dismiss() }
                    .setPositiveButton(context.resources.getString(R.string.profile)) { dialog, id ->
                        context.startActivity(Intent(context, VisitOthrerUserPerfilActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        dialog.dismiss() }
                val alert = dialogBuilder.create()
                alert.setTitle(App.sharedPreferences.getString("name", "").toString())
                alert.show()
            }
        }
    }




}
