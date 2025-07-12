package com.github.reactnativehero.dimension

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Size
import android.view.WindowManager
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class RNTDimensionModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val density: Float by lazy {
        reactApplicationContext.resources.displayMetrics.density
    }

    private var statusBarHeight = 0
    private var navigationBarHeight = 0

    private var cutoutTop = 0
    private var cutoutRight = 0
    private var cutoutBottom = 0
    private var cutoutLeft = 0

    override fun getName(): String {
        return "RNTDimension"
    }

    override fun getConstants(): Map<String, Any> {

        val constants: MutableMap<String, Any> = HashMap()

        constants["STATUS_BAR_HEIGHT"] = getStatusBarHeightData()
        constants["NAVIGATION_BAR_HEIGHT"] = getNavigationBarHeightData()

        val screenSize = getScreenSizeData()
        constants["SCREEN_WIDTH"] = screenSize.width
        constants["SCREEN_HEIGHT"] = screenSize.height

        val safeArea = getSafeAreaData()
        constants["SAFE_AREA_TOP"] = safeArea.top
        constants["SAFE_AREA_RIGHT"] = safeArea.right
        constants["SAFE_AREA_BOTTOM"] = safeArea.bottom
        constants["SAFE_AREA_LEFT"] = safeArea.left

        return constants

    }

    @ReactMethod
    fun init(promise: Promise) {

        lateinit var waitActivity: () -> Unit

        val handler = fun (activity: Activity?) {
            val listener = OnApplyWindowInsetsListener { _, insets ->

                val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val cutoutInsets = insets.getInsets(WindowInsetsCompat.Type.displayCutout())

                var isChange = false

                if (statusBarHeight != systemBarInsets.top) {
                    statusBarHeight = systemBarInsets.top
                    isChange = true
                }
                if (navigationBarHeight != systemBarInsets.bottom) {
                    navigationBarHeight = systemBarInsets.bottom
                    isChange = true
                }
                if (cutoutTop != cutoutInsets.top) {
                    cutoutTop = cutoutInsets.top
                    isChange = true
                }
                if (cutoutRight != cutoutInsets.right) {
                    cutoutRight = cutoutInsets.right
                    isChange = true
                }
                if (cutoutBottom != cutoutInsets.bottom) {
                    cutoutBottom = cutoutInsets.bottom
                    isChange = true
                }
                if (cutoutLeft != cutoutInsets.left) {
                    cutoutLeft = cutoutInsets.left
                    isChange = true
                }

                if (isChange) {
                    val screenSizeData = getScreenSizeData()
                    val screenSizeMap = Arguments.createMap()
                    screenSizeMap.putInt("width", screenSizeData.width)
                    screenSizeMap.putInt("height", screenSizeData.height)

                    val safeAreaData = getSafeAreaData()
                    val safeAreaMap = Arguments.createMap()
                    safeAreaMap.putInt("top", safeAreaData.top)
                    safeAreaMap.putInt("right", safeAreaData.right)
                    safeAreaMap.putInt("bottom", safeAreaData.bottom)
                    safeAreaMap.putInt("left", safeAreaData.left)

                    val map = Arguments.createMap()
                    map.putInt("statusBarHeight", getStatusBarHeightData())
                    map.putInt("navigationBarHeight", getNavigationBarHeightData())
                    map.putMap("screenSize", screenSizeMap)
                    map.putMap("safeArea", safeAreaMap)

                    sendEvent("change", map)
                }

                insets
            }
            if (activity != null) {
                activity.runOnUiThread {
                    val decorView = activity.window.decorView
                    ViewCompat.setOnApplyWindowInsetsListener(decorView, listener)
                    decorView.requestApplyInsets()

                    val map = Arguments.createMap()
                    promise.resolve(map)
                }
            } else {
                waitActivity()
            }
        }

        waitActivity = fun () {
            Handler(Looper.getMainLooper()).postDelayed({
                handler(currentActivity)
            }, 200)
        }

        handler(currentActivity)

    }

    @ReactMethod
    fun getStatusBarHeight(promise: Promise) {

        val map = Arguments.createMap()
        map.putInt("height", getStatusBarHeightData())

        promise.resolve(map)

    }

    @ReactMethod
    fun getNavigationBarHeight(promise: Promise) {

        val map = Arguments.createMap()
        map.putInt("height", getNavigationBarHeightData())

        promise.resolve(map)

    }

    @ReactMethod
    fun getScreenSize(promise: Promise) {

        val map = Arguments.createMap()
        val data = getScreenSizeData()

        map.putInt("width", data.width)
        map.putInt("height", data.height)

        promise.resolve(map)

    }

    @ReactMethod
    fun getSafeArea(promise: Promise) {

        val map = Arguments.createMap()
        val data = getSafeAreaData()

        map.putInt("top", data.top)
        map.putInt("right", data.right)
        map.putInt("bottom", data.bottom)
        map.putInt("left", data.left)

        promise.resolve(map)

    }

    private fun getScreenSizeData(): Size {
        // 跟 ios 保持一致，获取的是物理屏尺寸
        var screenWidth = 0
        var screenHeight = 0

        val windowManager = reactContext.getSystemService(WindowManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            val windowMetrics = windowManager.currentWindowMetrics
            screenWidth = windowMetrics.bounds.width()   // 屏幕总宽度（像素）
            screenHeight = windowMetrics.bounds.height() // 屏幕总高度（像素）
        } else {
            // 旧版本兼容方案（Android 5.0~10）
            val point = Point()
            windowManager.defaultDisplay.getRealSize(point)
            screenWidth = point.x
            screenHeight = point.y
        }
        return Size(toReactUnit(screenWidth), toReactUnit(screenHeight))
    }

    private fun getStatusBarHeightData(): Int {
        return toReactUnit(statusBarHeight)
    }

    private fun getNavigationBarHeightData(): Int {
        return toReactUnit(navigationBarHeight)
    }

    private fun getSafeAreaData(): Rect {
        return Rect(toReactUnit(cutoutLeft), toReactUnit(statusBarHeight), toReactUnit(cutoutRight), toReactUnit(cutoutBottom))
    }

    private fun toReactUnit(value: Int): Int {
        return (value / density).toInt()
    }

    private fun sendEvent(eventName: String, params: WritableMap) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

}
