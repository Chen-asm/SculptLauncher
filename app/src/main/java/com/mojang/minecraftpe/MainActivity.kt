package com.mojang.minecraftpe

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NativeActivity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Debug
import android.os.Environment
import android.os.Handler
import android.os.StatFs
import android.os.SystemClock
import android.os.Vibrator
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.DisplayMetrics
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import androidx.datastore.preferences.protobuf.StringValue
import cn.hutool.core.io.FileUtil
import com.chen.sculptlauncher.core.MyClassList
import com.chen.sculptlauncher.core.RedirectPackageManager
import dalvik.system.PathClassLoader
import org.fmod.FMOD
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Field
import java.math.BigInteger
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import kotlin.math.max
import kotlin.math.min


open class MainActivity : NativeActivity(), View.OnKeyListener {
    val ACTION_TCUI_BROADCAST_LAUNCH: String = "com.microsoft.beambroadcast.xle.app.action.TCUI"
    val BROADCAST_TITLE_NAME_KEY: String = "name"
    val MARKET_URL_FORMAT: String = "market://details?id=%s"
    private val MAX_SESSION_HISTORY_LENGTH: Int = 20
    val MINECRAFT_BROADCAST_TITLE: String = "Minecraft"
    val PACKAGE_NAME_KEY = "package"
    var RESULT_GOOGLEPLAY_PURCHASE: Int = 2
    var RESULT_PICK_IMAGE: Int = 1
    private var _isPowerVr: Boolean = false
    var mHasStoragePermission = false
    lateinit var mInstance: MainActivity
    @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat()
    private val _fromOnCreate = false
    private val _isTouchscreen = true
    private var _userInputStatus = -1
    private var _userInputText: Array<String>? = null
    private val _userInputValues = ArrayList<StringValue>()
    private val _viewDistance = 2
    // lateinit var clipboardManager: ClipboardManager
    lateinit var data: Bundle
    // lateinit var deviceManager:
    lateinit var initialUserLocale: Locale
    val mCachedMemoryInfo = ActivityManager.MemoryInfo()
    var mCachedMemoryInfoUpdateTime: Long = 0L
    var mCachedUsedMemory: Long = 0L
    var mCachedUsedMemoryUpdateTime: Long = 0L
    private var mCallback = 0L
    private lateinit var textToSpeechManager: TextToSpeech
    var virtualKeyboardHeight: Int = 0

    lateinit var packageInfo: PackageInfo
    lateinit var appInfo: ApplicationInfo
    lateinit var libraryDir: String
    lateinit var librariesLocation: ArrayList<String>
    lateinit var apkContext: Context
    var mcpePackage: Boolean = false


    @SuppressLint("UnsafeDynamicallyLoadedCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        mInstance = this
        librariesLocation = ArrayList()
        try {
            packageInfo = packageManager.getPackageInfo("com.mojang.minecraftpe", 0)
            appInfo = packageInfo.applicationInfo
            libraryDir = appInfo.nativeLibraryDir
            librariesLocation.add("${libraryDir}/libc++_shared.so")
            librariesLocation.add("${libraryDir}/libfmod.so")
            librariesLocation.add("${libraryDir}/libminecraftpe.so")
            mHardwareInformation = HardwareInformation(this, packageManager)
            Log.i(LAUNCHER, "游戏动态链接库为：${librariesLocation}")
            Log.i(LAUNCHER, "游戏资源地址：${appInfo.sourceDir}和${appInfo.publicSourceDir}应当相等")
            apkContext = if (this.packageName == "com.mojang.minecraftpe") this else {
                createPackageContext("com.mojang.minecraftpe", Context.CONTEXT_IGNORE_SECURITY)
            }
            Log.i(LAUNCHER, "开始加载周围库")
            System.load(librariesLocation[0])
            System.load(librariesLocation[1])
            Log.i(LAUNCHER, "libc++_shared.so和libfmod.so已被加载")
            System.load(librariesLocation[2])
            Log.i(LAUNCHER, "libminecraftpe.so已被加载")
            FMOD.init(this)
            if (!FMOD.checkInit()){
                Log.i(LAUNCHER, "FMOD未完成初始化")
            } else { Log.i(LAUNCHER, "FMOD完成初始化") }
//            librariesLocation.forEach {
//                println(it)
//                MyClassList.addLibraryDirToPath(it, this.apkContext)
//            }
            mcpePackage = false
            super.onCreate(savedInstanceState)
        } catch (e: Exception){
            Log.e(LAUNCHER, "启动失败：$e")
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun getPackageManager(): PackageManager {
        if (mcpePackage){
            return RedirectPackageManager(super.getPackageManager(), libraryDir)
        }
        return super.getPackageManager()
    }

    fun postScreenshotToFacebook(filepath: String, v1: Int, v2: Int, v3s: IntArray){}
    fun getImageData(filepath: String): IntArray? {
        val assetManager = applicationContext.assets
        try {
            val inputStream = assetManager.open(filepath)
            val bmp = BitmapFactory.decodeStream(inputStream)
            val retval = IntArray((bmp.width * bmp.height) + 2)
            retval[0] = bmp.getWidth()
            retval[1] = bmp.getHeight()
            bmp.getPixels(retval, 2, bmp.getWidth(), 0, 0, bmp.getWidth(),
                bmp.getHeight())
            inputStream.close()
            bmp.recycle()
            return retval
        } catch (e: Exception){
            return null
        }
    }
    fun getFileDataBytes(v1: String): ByteArray? {
        Log.i(LAUNCHER, "getFileDataBytes::$v1")
        return null
    }
    fun displayDialog(v1: Int){ Log.i(LAUNCHER, "想要打开弹窗：${v1}")}
    fun tick(){}
    fun quit(){ finish() }
    fun initiateUserInput(v1: Int){
        _userInputText = null
        _userInputStatus = -1
    }
    fun getUserInputStatus(): Int = _userInputStatus
    fun getUserInputString(): Array<String>? = _userInputText
    fun checkLicense(): Int = 0
    fun hasBuyButtonWhenInvalidLicense(): Boolean = true
    fun buyGame(){}
    fun vibrate(v1: Int){
        (getSystemService("vibrator") as Vibrator).vibrate(v1.toLong())
    }
    fun setIsPowerVR(v1: Boolean){ _isPowerVr = v1 }
    fun setClipboard(v1: String){
        val clipData = ClipData.newPlainText("MCPE-Clipdata", v1)
        (getSystemService("clipboard") as ClipboardManager).setPrimaryClip(clipData)
    }
    fun isNetworkEnabled(v1: Boolean): Boolean = true
    fun getPixelsPerMillimeter(): Float {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return (metrics.xdpi + metrics.ydpi) * 0.5F / 25.4F
    }
    fun getPlatformStringVar(v1: Int): String? = if (v1 == 0) Build.MODEL else null
    fun getKeyFromKeyCode(paramInt1: Int, paramInt2: Int, paramInt3: Int): Int {
        var i = paramInt3
        if (paramInt3 < 0) {
            val arrayOfInt = InputDevice.getDeviceIds()
            if (arrayOfInt.isEmpty()) return 0
            i = arrayOfInt[0]
        }
        val inputDevice = InputDevice.getDevice(i)
        return if ((inputDevice == null)) 0 else inputDevice.keyCharacterMap[paramInt1, paramInt2]
    }
    fun updateLocalization(v1: String, v2: String) {
        val locale = Locale(v1, v2)
        Locale.setDefault(locale)
        val config = Configuration()
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    // TODO: showKeyboard,hideKeyboard,getKeyboardHeight,updateTextboxText
    fun showKeyboard(v1: String, v2: Int, v3: Boolean, v4: Boolean, v5: Boolean){
    }
    fun hideKeyboard(){}
    fun getKeyboardHeight(): Float = 64F
    fun updateTextboxText(v1: String){}
    // TODO: getCursorPosition
    fun getCursorPosition(): Int = -1
    fun getAccessToken(): String = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        .getString("accessToken", "") ?: ""
    fun getClientId(): String = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        .getString("clientId", "") ?: ""
    fun getProfileId(): String = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        .getString("profileId", "") ?: ""
    fun getProfileName(): String =  PreferenceManager.getDefaultSharedPreferences(applicationContext)
        .getString("profileName", "") ?: ""
    fun getBroadcastAddresses(): Array<String> {
        val arrayList: ArrayList<String> = ArrayList()
        try {
            System.setProperty("java.net.preferIPv4Stack", "true")
            val enumeration = NetworkInterface.getNetworkInterfaces()
            while (enumeration.hasMoreElements()) {
                val networkInterface = enumeration.nextElement()
                if (!networkInterface.isLoopback) for (interfaceAddress in networkInterface.interfaceAddresses) {
                    if (interfaceAddress.broadcast != null) arrayList.add(
                        interfaceAddress.broadcast.toString().substring(1)
                    )
                }
            }
        } catch (_: Exception) {
        }
        return arrayList.toTypedArray<String>()
    }
    fun getIPAddresses(): Array<String> {
        val arrayList: ArrayList<String> = ArrayList()
        val str: String = chromebookCompatibilityIP()
        if (str.isNotEmpty()) arrayList.add(str)
        try {
            System.setProperty("java.net.preferIPv4Stack", "true")
            val enumeration = NetworkInterface.getNetworkInterfaces()
            while (enumeration.hasMoreElements()) {
                val networkInterface = enumeration.nextElement()
                if (!networkInterface.isLoopback && networkInterface.isUp) for (interfaceAddress in networkInterface.interfaceAddresses) {
                    val inetAddress = interfaceAddress.address
                    if (inetAddress != null && !inetAddress.isAnyLocalAddress && !inetAddress.isMulticastAddress && !inetAddress.isLinkLocalAddress) arrayList.add(
                        interfaceAddress.address.toString().substring(1)
                    )
                }
            }
        } catch (_: Exception) {
        }
        return arrayList.toTypedArray<String>()
    }
    fun getTotalMemory(): Long {
        val memoryInfo: ActivityManager.MemoryInfo = getMemoryInfo()
        val l1: Long = memoryInfo.totalMem
        var l2: Long
        l2 = l1
        if (Build.SUPPORTED_ABIS[0] != "arm64-v8a") {
            l2 = l1
            if (4294967296L < l1) l2 = 4294967296L
        }
        return l2
    }
    fun getMemoryLimit(): Long = getTotalMemory() - getMemoryInfo().threshold
    fun getUsedMemory(): Long {
        val l = SystemClock.uptimeMillis()
        if (l >= this.mCachedUsedMemoryUpdateTime) {
            this.mCachedUsedMemory = Debug.getNativeHeapAllocatedSize()
            this.mCachedUsedMemoryUpdateTime = l + 10000L
        }
        return this.mCachedUsedMemory
    }
    fun getFreeMemory(): Long {
        val memoryInfo = getMemoryInfo()
        return memoryInfo.availMem - memoryInfo.threshold
    }
    fun getHardwareInfo(): HardwareInformation = mHardwareInformation
    fun launchUri(paramString: String) {
        startActivity(Intent("android.intent.action.VIEW", Uri.parse(paramString)))
    }
    fun share(paramString1: String, paramString2: String, paramString3: String) {
        val intent = Intent()
        intent.setAction("android.intent.action.SEND")
        intent.putExtra("android.intent.extra.SUBJECT", paramString1)
        intent.putExtra("android.intent.extra.TITLE", paramString2)
        intent.putExtra("android.intent.extra.TEXT", paramString3)
        intent.setType("text/plain")
        startActivity(Intent.createChooser(intent, paramString1))
    }
    fun createAndroidLaunchIntent(): Intent? {
        val context = applicationContext
        return context.packageManager.getLaunchIntentForPackage(context.packageName)
    }
    fun calculateAvailableDiskFreeSpace(paramString: String): Long {
        var l: Long
        try {
            val statFs = StatFs(paramString)
            l = statFs.availableBytes
        } catch (exception: java.lang.Exception) {
            l = 0L
        }
        return l
    }
    fun getExternalStoragePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }
    fun requestStoragePermission(v1: Int){} // 我们已经申请了
    fun hasWriteExternalStoragePermission(): Boolean = false //禁止访问外部
    fun deviceIdCorrelationStart() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        val i = sharedPreferences.getInt("correlationAttempts", 10)
        if (i == 0) return
        val intent = Intent()
        val str = if (packageName.contains("trial")) {
            "com.mojang.minecraftpe"
        } else {
            "com.mojang.minecrafttrialpe"
        }
        intent.setComponent(ComponentName(str, "com.mojang.minecraftpe.ImportService"))
        // bindService(intent, this.mConnection, 1)
        val editor = sharedPreferences.edit()
        editor.putInt("correlationAttempts", i - 1)
        editor.apply()
    }
    fun createUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
    fun isMixerCreateInstalled(): Boolean = false
    // TODO:navigateToPlaystoreForMixerCreate, launchMixerCreateForBroadcast
    fun navigateToPlaystoreForMixerCreate(){}
    fun launchMixerCreateForBroadcast(): Boolean = false
    fun isOnWifi(): Boolean {
        return (getSystemService("connectivity") as ConnectivityManager).getNetworkInfo(1)!!
            .isConnectedOrConnecting
    }
    fun isTTSEnabled(): Boolean {
        if (applicationContext != null) {
            val accessibilityManager: AccessibilityManager =
                getSystemService("accessibility") as AccessibilityManager
            if (accessibilityManager.isEnabled && accessibilityManager.getEnabledAccessibilityServiceList(
                    1
                ).isNotEmpty()
            ) return true
        }
        return false
    }
    fun isTablet(): Boolean {
        val bool = (resources.configuration.screenLayout and 0xF) == 4
        return bool
    }
    external fun nativeInitializeXboxLive(v1: Long, v2: Long)
    fun initializeXboxLive(v1: Long, v2: Long){ nativeInitializeXboxLive(v1, v2) }
    fun setCachedDeviceId(paramString: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString("deviceId", paramString)
        editor.apply()
    }
    // TODO: getLastDeviceSessionId
    fun getLastDeviceSessionId(): String = ""
    fun getAPIVersion(paramString: String): Int {
        for (field in VERSION_CODES::class.java.fields) {
            if (field.name === paramString) try {
                val `object` = Any()
                return field.getInt(`object`)
            } catch (illegalArgumentException: IllegalArgumentException) {
                val stringBuilder1 = StringBuilder()
                stringBuilder1.append("IllegalArgumentException in getApiVersion(")
                stringBuilder1.append(paramString)
                stringBuilder1.append(")")
                Log.e(LAUNCHER, stringBuilder1.toString())
            } catch (illegalAccessException: IllegalAccessException) {
                val stringBuilder1 = StringBuilder()
                stringBuilder1.append("IllegalAccessException in getApiVersion(")
                stringBuilder1.append(paramString)
                stringBuilder1.append(")")
                Log.e(LAUNCHER, stringBuilder1.toString())
            } catch (nullPointerException: NullPointerException) {
                val stringBuilder1 = StringBuilder()
                stringBuilder1.append("NullPointerException in getApiVersion(")
                stringBuilder1.append(paramString)
                stringBuilder1.append(")")
                Log.e(LAUNCHER, stringBuilder1.toString())
            }
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append("Failed to find API version for: ")
        stringBuilder.append(paramString)
        Log.e(LAUNCHER, stringBuilder.toString())
        return -1
    }
    fun getSecureStorageKey(paramString: String?): String? {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getString(paramString, "")
    }
    fun getScreenWidth(): Int {
        val display = (getSystemService("window") as WindowManager).defaultDisplay
        val i =
            max(display.width.toDouble(), display.height.toDouble()).toInt()
        val printStream = System.out
        val stringBuilder = java.lang.StringBuilder()
        stringBuilder.append("getwidth: ")
        stringBuilder.append(i)
        printStream.println(stringBuilder.toString())
        return i
    }
    fun getScreenHeight(): Int {
        val display = (getSystemService("window") as WindowManager).defaultDisplay
        val i =
            min(display.width.toDouble(), display.height.toDouble()).toInt()
        val printStream = System.out
        val stringBuilder = java.lang.StringBuilder()
        stringBuilder.append("getheight: ")
        stringBuilder.append(i)
        printStream.println(stringBuilder.toString())
        return i
    }
    fun setSecureStorageKey(paramString1: String?, paramString2: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString(paramString1, paramString2)
        editor.apply()
    }
    fun setRefreshToken(paramString: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString("refreshToken", paramString)
        editor.apply()
    }
    fun setLoginInformation(v1: String?, v2: String?, v3: String?, v4: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString("accessToken", v1)
        editor.putString("clientId", v2)
        editor.putString("profileId", v3)
        editor.putString("profileName", v4)
        editor.apply()
    }
    fun setSession(paramString: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this as Context).edit()
        editor.putString("sessionID", paramString)
        editor.apply()
    }
    fun trackPurchaseEvent(v1: String, v2: String, v3: String, v4: String, v5: String, v6: String, v7: String, v8: String){}
    fun sendBrazeEvent(v1: String){}
    fun sendBrazeEventWithProperty(v1: String, v2: String, v3: Int){}
    fun sendBrazeEventWithStringProperty(v1: String, v2: String, v3: String){}
    fun sendBrazeToastClick(){}
    fun sendBrazeDialogButtonClick(v1: Int){}
    external fun nativeOnPickImageCanceled(v1: Long)
    external fun nativeOnPickImageSuccess(v1: Long, v2: String)
    fun pickImage(paramLong: Long) {
        this.mCallback = paramLong
        try {
            val intent = Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, RESULT_PICK_IMAGE)
        } catch (_: ActivityNotFoundException) {
        }
    }
    // TODO: setFileDialogCallback
    fun setFileDialogCallback(v1: Long){}
    fun getLegacyDeviceID(): String? {
        return PreferenceManager.getDefaultSharedPreferences(this as Context)
            .getString("snooperId", "")
    }
    fun hasHardwareKeyboard(): Boolean {
        val bool = resources.configuration.keyboard == 2
        return bool
    }
    fun getDeviceModel(): String {
        return HardwareInformation.getDeviceModelName()
    }
    fun startTextToSpeech(paramString: String?) {
        val textToSpeech = this.textToSpeechManager
        textToSpeech.speak(paramString, 0, null)
    }
    fun statsTrackEvent(paramString1: String?, paramString2: String?) {}
    fun statsUpdateUserData(paramString1: String?, paramString2: String?) {}
    fun stopTextToSpeech() {
        val textToSpeech = this.textToSpeechManager
        textToSpeech.stop()
    }
    fun throwRuntimeExceptionFromNative(message: String?) {
        Handler(mainLooper).post { throw RuntimeException(message) }
    }
    fun isTextToSpeechInProgress(): Boolean {
        val textToSpeech = this.textToSpeechManager
        return textToSpeech.isSpeaking
    }
    fun setTextToSpeechEnabled(paramBoolean: Boolean) {
        if (paramBoolean) {
            if (this.textToSpeechManager == null) try {
                val onInitListener: OnInitListener = OnInitListener { }
                val textToSpeech = TextToSpeech(this, onInitListener)
                this.textToSpeechManager = textToSpeech
            } catch (_: Exception) {
            }
        } else {
            this.textToSpeechManager.stop()
        }
    }

    private fun getMemoryInfo(): ActivityManager.MemoryInfo {
        val l: Long = SystemClock.uptimeMillis()
        if (l >= this.mCachedMemoryInfoUpdateTime) {
            (getSystemService("activity") as ActivityManager).getMemoryInfo(this.mCachedMemoryInfo)
            this.mCachedMemoryInfoUpdateTime = l + 2000L
        }
        return this.mCachedMemoryInfo
    }

    private fun addLibraryDirToPath(path: String){
        try {
            val classLoader = classLoader
            val clazz: Class<out ClassLoader> = classLoader.javaClass
            val field = getDeclaredFieldRecursive(clazz, "pathList")!!
            field.isAccessible = true
            val pathListObj: Any = field.get(classLoader)!!
            val pathListClass: Class<out Any> = pathListObj.javaClass
            val natfield = getDeclaredFieldRecursive(
                pathListClass,
                "nativeLibraryDirectories"
            )!!
            natfield.isAccessible = true
            val fileList = (natfield[pathListObj] as ArrayList<File?>).toTypedArray() //as Array<File?>
            val newList = addToFileList(fileList, File(path))
            println(newList.toList().toString())
            if (!fileList.contentEquals(newList)) natfield.set(pathListObj, newList.toList())
            Log.i(LAUNCHER, "Class loader shenanigans: ${
                (getClassLoader() as PathClassLoader).findLibrary(
                    "minecraftpe"
                )}")
        } catch (e: Exception){
            e.printStackTrace()
            Log.e("${LAUNCHER}::addLibraryDirToPath", e.toString())
        }
    }

    fun getDeclaredFieldRecursive(clazz: Class<*>?, name: String?): Field? {
        if (clazz == null) return null
        return try {
            clazz.getDeclaredField(name!!)
        } catch (_: NoSuchFieldException) {
            getDeclaredFieldRecursive(clazz.superclass, name)
        }
    }

    private fun addToFileList(files: Array<File?>, toAdd: File): Array<File?> {
        for (f in files) {
            if (f == toAdd) {
                return files
            }
        }
        val retval = arrayOfNulls<File>(files.size + 1)
        System.arraycopy(files, 0, retval, 1, files.size)
        retval[0] = toAdd
        return retval
    }

    private fun chromebookCompatibilityIP(): String {
        val context = window.context
        if (isChromebook() && context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == PackageManager.PERMISSION_GRANTED) {
            val i =
                (context.getSystemService(WifiManager::class.java) as WifiManager).connectionInfo.ipAddress
            if (i != 0) {
                var j = i
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) j = Integer.reverseBytes(i)
                val arrayOfByte = BigInteger.valueOf(j.toLong()).toByteArray()
                return try {
                    InetAddress.getByAddress(arrayOfByte).hostAddress!!
                } catch (_: UnknownHostException) {
                    ""
                }
            }
        }
        return ""
    }

    private fun isChromebook(): Boolean {
        return window.context.packageManager.hasSystemFeature("android.hardware.type.pc")
    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        const val LAUNCHER: String = "雕刻启动器"
        lateinit var mHardwareInformation: HardwareInformation

        @JvmStatic fun saveScreenshot(filepath: String, width: Int, height: Int, colors: IntArray) {
            val bitmap = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888)
            val hookedPath =
                "${com.chen.sculptlauncher.MainActivity.host.getExternalFilesDir("ScreenShot")}/${
                    filepath.split("/").last()
                }"
            if (!FileUtil.exist(hookedPath)) File(hookedPath).createNewFile()
            try {
                val fileOutputStream = FileOutputStream(File(hookedPath))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: Exception){
                Log.e(LAUNCHER, "无法创建截图${hookedPath}，因为${e}")
            }
        }
    }
}