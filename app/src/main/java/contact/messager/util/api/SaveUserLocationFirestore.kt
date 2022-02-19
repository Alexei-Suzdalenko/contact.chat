package contact.messager.util.api
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import contact.messager.R
import contact.messager.activity.MainActivity
import contact.messager.activity.MyProfileActivity
import contact.messager.util.classes.App
import java.util.*
import kotlin.collections.HashMap

class SaveUserLocationFirestore {
    val miId           = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val refUser       = Firebase.firestore.collection("user").document(miId)
    val refUserInfo  = Firebase.firestore.collection("userInfo").document(miId)
    val dataUser      = HashMap<String, Any>()
    val dataUserInfo = HashMap<String, Any>()

    fun saveUserLocation(activity: MyProfileActivity){
        try {
            val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCodeValue = tm.networkCountryIso
            dataUser["country"] = countryCodeValue.toString();                                                             App.editor.putString("country", countryCodeValue.toString())
        } catch (e: Exception){}

     if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
      }

      if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
          val lm = activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
          val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

          val longitude: Double = location!!.longitude
          val latitude: Double = location.latitude
          val geocoder = Geocoder(activity.applicationContext, Locale.getDefault())
          try {
              val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
              if (addresses.isNotEmpty()) {
                  dataUserInfo["locality"] = addresses[0].locality.toString(); refUserInfo.update(dataUserInfo); App.editor.putString("locality", addresses[0].locality.toString())
                  dataUser      ["postal"] = addresses[0].postalCode.toString().lowercase(Locale.getDefault()); App.editor.putString("postal", addresses[0].postalCode.toString().lowercase(Locale.getDefault()))
              }
          } catch (e: Exception){}
      } else {
          Toast.makeText(activity.applicationContext, activity.resources.getString(R.string.thisAppNeedLocation), Toast.LENGTH_LONG).show()
      }

        App.editor.apply()
        refUser.set(dataUser)
    }
}