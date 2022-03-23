package contact.messager.util.api
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.util.clas.App
object Adds {

    fun start(context: VisitOthrerUserPerfilActivity){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, "ca-app-pub-7286158310312043/7949035373", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                App.mInterstitialAd = interstitialAd
                Log.d("realChannelId", "onAdLoaded= ")
                Log.d("realChannelId", "onAdLoaded= " + App.mInterstitialAd.toString())
                App.mInterstitialAd?.show(context)
            }
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.d("realChannelId", "loadAdError.message= " + App.mInterstitialAd.toString())
                Log.d("realChannelId", "loadAdError.message= " + loadAdError.message)
            }
        })
    }


    fun startM(context: ChatConversationActivity){
        var countMessage = App.sharedPreferences.getInt("count", 0).toInt(); countMessage++
        App.editor.putInt("count", countMessage).apply()

        if(countMessage > 11){
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context, "ca-app-pub-7286158310312043/7949035373", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    App.mInterstitialAd = interstitialAd
                    Log.d("realChannelId", "onAdLoaded= ")
                    Log.d("realChannelId", "onAdLoaded= " + App.mInterstitialAd.toString())
                    App.mInterstitialAd?.show(context)
                }
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.d("realChannelId", "loadAdError.message= " + App.mInterstitialAd.toString())
                    Log.d("realChannelId", "loadAdError.message= " + loadAdError.message)
                }
            })
            App.editor.putInt("count", 0).apply()
        }
    }



}