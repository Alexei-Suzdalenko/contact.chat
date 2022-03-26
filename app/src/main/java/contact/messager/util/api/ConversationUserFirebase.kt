package contact.messager.util.api
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import contact.messager.util.clas.App
import contact.messager.util.clas.User

class ConversationUserFirebase {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid
    val refConvers = FirebaseFirestore.getInstance().collection("enganched_chat").document(miId).collection("channel")
    val listIdsConvers = ArrayList<String>()
    val listDataConvers:  MutableList<User> = mutableListOf()

    // uso para no mostrarme a mi usuarios que he bloqueado
    val usersBlocked = App.sharedPreferences.getString("block", "").toString()

    fun getListConversation(onComplete: (res: MutableList<User>) -> Unit){
        listIdsConvers.clear()
        refConvers.get().addOnSuccessListener {
            for(doc in it.documents){
                if(! usersBlocked.contains(doc.id, ignoreCase = true)) listIdsConvers.add(doc.id)
            }
            if(listIdsConvers.size > 0){ getUserInfoFromEnganchedChannels{ onComplete(it) } }
        }
    }

    private fun getUserInfoFromEnganchedChannels(onComplete: (res: MutableList<User>) -> Unit){
        listDataConvers.clear()

        for(position in 0 until listIdsConvers.size){
            FirebaseFirestore.getInstance().collection("user").document(listIdsConvers[position]).get().addOnSuccessListener {
                var user: User? = null
                if(it.exists()){
                    user = User(listIdsConvers[position], it["age"].toString(), it["country"].toString(), it["image"].toString(), it["locality"].toString(), it["name"].toString(), it["online"].toString(), it["postal"].toString(), it["status"].toString(), it["token"].toString(), it["backImage"].toString()      )
                    listDataConvers.add(user)
                    onComplete(listDataConvers)
                }
            }
        }
    }

}





















