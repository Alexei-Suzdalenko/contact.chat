package contact.messager.util.api
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import contact.messager.activity.MainActivity


object PageWidth {

    fun GetSizesPageItems(activity: MainActivity): Int{
        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var height = 0
        var width = 0
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                 val windowMetrics = wm.currentWindowMetrics
                 val windowInsets: WindowInsets = windowMetrics.windowInsets
                 val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
                 val insetsWidth = insets.right + insets.left
                 val insetsHeight = insets.top + insets.bottom
                 val b = windowMetrics.bounds
                 width = b.width() - insetsWidth
                 height = b.height() - insetsHeight
             } else {
                 val metrics = DisplayMetrics()
                 activity.windowManager.defaultDisplay.getMetrics(metrics)
                 val usableHeight = metrics.widthPixels
                 Log.d("pageWidth", "usableHeight = " + usableHeight)

             val size = Point()
             val display = wm.defaultDisplay
             display?.getSize(size)
             width = size.x
             height = size.y
         }
            if(width > 2000)
                return (width / 250).toInt()
             else
                return 4



    }
}