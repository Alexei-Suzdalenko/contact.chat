package contact.messager.activity.view_fragment;
import android.content.Context;
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import contact.messager.R
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.ConversSearchProfileActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.util.`class`.App.Companion.userConversation
import contact.messager.util.adapter.SearchedUsersAdapter
import contact.messager.util.`class`.User
import contact.messager.util.api.SaveDataImageUserFirebase.GetListUsers

class SearchFragment (val fr: FragmentSearchBinding, val context: Context, val activity: ConversSearchProfileActivity){
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
                alert.setTitle(usersSearched[position].name)
                alert.show()
            }
        }

       //     Firebase.database.reference
       //         .child("user")
       //         // .startAt(inputEditTextString)
       //        // .orderByChild("email")
       //       //  .limitToFirst(11)
       //         .addListenerForSingleValueEvent(valueEventListener)


    //    listSearched.setOnItemClickListener { _, _, position, _ ->
    //        userConversation = listUsersSearched[position]
    //        context.startActivity(Intent(context, ChatConversationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    //    }

    }
}
