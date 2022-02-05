package contact.messager.util.notification
import android.annotation.SuppressLint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceNotification {

    @SuppressLint("RestrictedApi")
    fun sentNotification( chatId: String, sender: String,  receiver: String, userName: String,  message: String, image: String, token: String){
        val apiService = Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        val data = Data(chatId, sender,  receiver, userName,  message , image)
        val senderA = Sender(data, token)

        apiService
            .sendNotification(senderA)
            .enqueue(object: Callback<MyResponse> {
                override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
                override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                    if( response.code() == 200 ){ if( response.body()?.success != 1 ){ } }
                }
        })
    }
}