package contact.messager.util.notification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    @Headers(
        "Content-Type: application/json",
        "Authorization:key=AAAAhecpJ74:APA91bE0rvek81g5y-66WZq3o7j3W1vmYM2eutPWWKpDOo_Idiv0Bs6ictVyFRGLGZa1wv7eiboSYtIPYa0Qq63GVRf8r_FxsIbCFA88sNueAsNajRpuFIQ25O3L5KMb-gnOci39Fr55"
    )

    @POST("fcm/send")
    fun sendNotification(@Body body: Sender): Call<MyResponse>
}