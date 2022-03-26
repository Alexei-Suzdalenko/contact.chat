package contact.messager.util.api
import android.Manifest
import android.app.Activity
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import contact.messager.R
import contact.messager.util.clas.App
import java.util.*
import kotlin.collections.HashMap

class SaveUserLocationFirestore {
    val miId           = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val refUser       = Firebase.firestore.collection("user").document(miId)
    val dataUser    = HashMap<String, Any>()
    val refCountry  = FirebaseFirestore.getInstance().collection("country")

    fun saveUserLocation(activity: Context){
        try {
            val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCodeValue = tm.networkCountryIso
            val code = countryCodeValue.toString()
            dataUser["country"] = code;                                                             App.editor.putString("country", code)
            refCountry.document(code).set(dataUser)
        } catch (e: Exception){}

     if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
      }

      if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
          val lm = activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
          val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

          try {
              val longitude: Double = location!!.longitude
              val latitude: Double = location.latitude
              val geocoder = Geocoder(activity.applicationContext, Locale.getDefault())
              val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
              if (addresses.isNotEmpty()) {
                  dataUser["locality"] = addresses[0].locality.toString();
                  dataUser["postal"] = addresses[0].postalCode.toString().lowercase(Locale.getDefault());
              }
          } catch (e: Exception){
            //  Toast.makeText(activity, "Address don t localizated", Toast.LENGTH_LONG).show()
          }
      } else {
          Toast.makeText(activity.applicationContext, "error b " + activity.resources.getString(R.string.thisAppNeedLocation), Toast.LENGTH_LONG).show()
      }

        App.editor.apply()
        dataUser["online"] = System.currentTimeMillis().toString()
        dataUser["email"] = App.sharedPreferences.getString("email", "").toString()
        SaveUserTime().intentGetUserDataIfExsistEnDataBase {  user ->
            if(user != null) refUser.update(dataUser)
            else refUser.set(dataUser)
        }

    }
}