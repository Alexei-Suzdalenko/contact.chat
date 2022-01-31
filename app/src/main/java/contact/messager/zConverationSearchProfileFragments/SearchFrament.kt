package contact.messager.zConverationSearchProfileFragments;
import android.content.Context;
import android.content.Intent
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import contact.messager.ChatConversationActivity
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.util.assets.App
import contact.messager.util.assets.App.Companion.userConversation
import contact.messager.util.assets.SearchedUsersAdapter
import contact.messager.util.assets.User

class SearchFragment (val fragment: FragmentSearchBinding, val context:Context){
    val emailSearch = fragment.emailSearch
    val listSearched = fragment.listviewSearch
    lateinit var listUsers: MutableList<User>
    var inputEditTextString = ""
    val miEmail = App.sharedPreferences.getString("email", "none").toString()
    var userSearched: User? = null

    fun initSearchFragment(){
        listUsers = mutableListOf()
        // listado de usuarios buscando por email en edit text
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listUsers.clear()
                for (ds in dataSnapshot.children) {
                    userSearched = ds.getValue(User::class.java)
                    if( userSearched?.email != "none" && miEmail != userSearched?.email ){
                        listUsers.add(userSearched!!)
                    //  if( userSearched!!.email.contains( inputEditTextString, true )){ listUsers.add(userSearched!!) }
                    }
                }
                listSearched.adapter = SearchedUsersAdapter(context, listUsers)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }

        // la hora de introducir un email en editText traemos 111 emails
        emailSearch.addTextChangedListener{
            inputEditTextString = emailSearch.text.toString().trim()
            if(inputEditTextString.isEmpty() || inputEditTextString.isBlank()) return@addTextChangedListener

            Firebase.database.reference
                .child("users")
                .startAt(inputEditTextString)
                .orderByChild("email")
                .limitToFirst(11)
                .addListenerForSingleValueEvent(valueEventListener)
        }


        listSearched.setOnItemClickListener { _, _, position, _ ->
            userConversation = listUsers[position]
            context.startActivity(Intent(context, ChatConversationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }
}
