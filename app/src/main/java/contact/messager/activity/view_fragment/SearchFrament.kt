package contact.messager.activity.view_fragment;
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import contact.messager.activity.MainActivity
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.util.adapter.SearchedUsersAdapter
import contact.messager.util.api.PageWidth.GetSizesPageItems
import contact.messager.util.api.SaveDataImageUserFirebase.GetListUsers
import contact.messager.util.clas.App
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment (val fr: FragmentSearchBinding, val context: Context, val activity: MainActivity){

    fun initSearchFragment(){
        App.usersSearched.clear()
        fr.loaderSearchProgressbar.visibility = View.VISIBLE

        GetListUsers { it ->
            App.usersSearched = it

            fr.loaderSearchProgressbar.visibility = View.GONE
            /* listado de usuarios en searched tab */
            val items = GetSizesPageItems(activity)
            fr.listviewSearch.layoutManager = GridLayoutManager(context, items)
            fr.listviewSearch.setHasFixedSize(true)
            fr.listviewSearch.adapter = SearchedUsersAdapter(context)

        }


    }

}
