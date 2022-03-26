package contact.messager.activity.view_fragment;
import android.content.Context;
import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import contact.messager.R
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.MainActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.util.clas.App.Companion.userConversation
import contact.messager.util.adapter.SearchedUsersAdapter
import contact.messager.util.api.BlockUserFire
import contact.messager.util.clas.User
import contact.messager.util.api.SaveDataImageUserFirebase.GetListUsers
import contact.messager.util.clas.App

class SearchFragment (val fr: FragmentSearchBinding, val context: Context, val activity: MainActivity){

    fun initSearchFragment(){
        App.usersSearched.clear()
        GetListUsers{ it ->
            App.usersSearched = it
            /* listado de usuarios en searched tab */
            fr.listviewSearch.layoutManager = LinearLayoutManager(context)
            fr.listviewSearch.setHasFixedSize(true)
            fr.listviewSearch.adapter = SearchedUsersAdapter(context)
        }
    }

}
