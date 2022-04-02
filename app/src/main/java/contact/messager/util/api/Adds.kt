package contact.messager.util.api
import android.util.Log
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import contact.messager.R
import contact.messager.activity.ChatConversationActivity
import contact.messager.activity.MainActivity
import contact.messager.activity.VisitOthrerUserPerfilActivity
import contact.messager.util.clas.App
import kotlinx.android.synthetic.main.activity_chat.*

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

        if(countMessage > 10){
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


    fun smartAdd(activity: MainActivity){
        MobileAds.initialize(activity) {}
        val params = RelativeLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT)
        val mAdView: AdView  = activity.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                var size = 211
                if( mAdView.height > 15 ) size = mAdView.height
                params.setMargins(0, (size / 1.5).toInt(), 0, size)
                activity.view_pager.layoutParams = params

            }
            override fun onAdFailedToLoad(p0: LoadAdError) {}
        }
        mAdView.loadAd(adRequest)
    }



}