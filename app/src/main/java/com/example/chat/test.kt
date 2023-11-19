//package com.csa.serialmonitor
//
//import android.R
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.content.SharedPreferences
//import android.content.pm.PackageManager
//import android.hardware.usb.UsbDevice
//import android.hardware.usb.UsbManager
//import android.hardware.usb.UsbRequest
//import android.net.ConnectivityManager
//import android.os.Build
//import android.os.Bundle
//import android.text.method.LinkMovementMethod
//import android.text.method.ScrollingMovementMethod
//import android.view.GestureDetector
//import android.view.GestureDetector.SimpleOnGestureListener
//import android.view.Menu
//import android.view.MenuItem
//import android.view.MotionEvent
//import android.view.View
//import android.view.View.OnTouchListener
//import android.view.WindowManager.BadTokenException
//import android.widget.Button
//import android.widget.EditText
//import android.widget.RelativeLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AlertController
//import androidx.appcompat.app.b
//import androidx.core.content.ContextCompat
//import com.applovin.sdk.AppLovinSdk
//import com.facebook.ads.AudienceNetworkAds
//import com.mopub.common.MoPub
//import com.mopub.mobileads.IronSourceAdapterConfiguration
//import com.mopub.mobileads.MoPubErrorCode
//import com.mopub.mobileads.MoPubInterstitial
//import com.mopub.mobileads.MoPubView
//import d3.g
//import d3.h
//import java.io.UnsupportedEncodingException
//import java.nio.ByteBuffer
//import java.util.Objects
//
///* loaded from: classes.dex */
//class MainActivity : d.h() {
//    var B: UsbManager? = null
//    var D: d3.g? = null
//    var G: IntentFilter? = null
//    var H: MoPubView? = null
//    var I: MoPubInterstitial? = null
//    var K = false
//
//    /* renamed from: s  reason: collision with root package name */
//    var f2992s: Button? = null
//
//    /* renamed from: t  reason: collision with root package name */
//    var f2993t: TextView? = null
//
//    /* renamed from: u  reason: collision with root package name */
//    var f2994u: EditText? = null
//
//    /* renamed from: v  reason: collision with root package name */
//    var f2995v: RelativeLayout? = null
//
//    /* renamed from: w  reason: collision with root package name */
//    var f2996w = false
//
//    /* renamed from: x  reason: collision with root package name */
//    var f2997x = 0
//
//    /* renamed from: y  reason: collision with root package name */
//    var f2998y = 0
//
//    /* renamed from: z  reason: collision with root package name */
//    var f2999z = 22
//    var A = intArrayOf(
//        R.style.darkThemeNative,
//        R.style.lightThemeNative,
//        R.style.theme2,
//        R.style.theme3
//    )
//    var C: UsbDevice? = null
//    var E = false
//    var F = true
//    var J = 0
//    var L: b = b()
//    val M: c = c()
//    val N: d = d()
//
//    /* loaded from: classes.dex */
//    inner class a : MoPubInterstitial.InterstitialAdListener {
//        // com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
//        fun onInterstitialClicked(moPubInterstitial: MoPubInterstitial?) {}
//
//        // com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
//        fun onInterstitialDismissed(moPubInterstitial: MoPubInterstitial?) {
//            I.load()
//        }
//
//        // com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
//        fun onInterstitialFailed(
//            moPubInterstitial: MoPubInterstitial?,
//            moPubErrorCode: MoPubErrorCode?
//        ) {
//        }
//
//        // com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
//        fun onInterstitialLoaded(moPubInterstitial: MoPubInterstitial?) {}
//
//        // com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
//        fun onInterstitialShown(moPubInterstitial: MoPubInterstitial?) {}
//    }
//
//    /* loaded from: classes.dex */
//    inner class b : h.a {
//        fun a(bArr: ByteArray?) {
//            try {
//                val str = String(bArr!!, "UTF-8")
//                "$str/n"
//                val mainActivity = this@MainActivity
//                mainActivity.runOnUiThread(a(mainActivity.f2993t, str))
//            } catch (e6: UnsupportedEncodingException) {
//                e6.printStackTrace()
//            }
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class c : BroadcastReceiver() {
//        // android.content.BroadcastReceiver
//        override fun onReceive(context: Context, intent: Intent) {
//            val mainActivity: MainActivity
//            val str: String
//            try {
//                if (intent.action == "com.csa.serialmonitor.USB_PERMISSION") {
//                    if (intent.extras!!.getBoolean("permission")) {
//                        A()
//                        return
//                    }
//                    mainActivity = this@MainActivity
//                    str = "Permission denied"
//                    val i6 = O
//                } else if (intent.action == "android.hardware.usb.action.USB_DEVICE_ATTACHED") {
//                    z()
//                    A()
//                    mainActivity = this@MainActivity
//                    str = "USB connected"
//                } else if (intent.action != "android.hardware.usb.action.USB_DEVICE_DETACHED") {
//                    return
//                } else {
//                    val mainActivity2 = this@MainActivity
//                    mainActivity2.C = null
//                    mainActivity2.x()
//                    mainActivity = this@MainActivity
//                    str = "USB disconnected"
//                }
//                mainActivity.C(str)
//            } catch (e6: Exception) {
//                e6.printStackTrace()
//            }
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class d : BroadcastReceiver() {
//        // android.content.BroadcastReceiver
//        override fun onReceive(context: Context, intent: Intent) {
//            val activeNetworkInfo =
//                (context.getSystemService("connectivity") as ConnectivityManager).activeNetworkInfo
//            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
//                return
//            }
//            val mainActivity = this@MainActivity
//            val moPubView: MoPubView? = mainActivity.H
//            if (moPubView != null) {
//                moPubView.loadAd()
//            }
//            val moPubInterstitial: MoPubInterstitial? = mainActivity.I
//            if (moPubInterstitial != null) {
//                moPubInterstitial.load()
//            }
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class e : SimpleOnGestureListener() {
//        // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
//        override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
//            val mainActivity: MainActivity
//            val str: String
//            try {
//                val mainActivity2 = this@MainActivity
//                if (mainActivity2.F) {
//                    mainActivity2.f2993t!!.gravity = 0
//                    mainActivity = this@MainActivity
//                    mainActivity.F = !mainActivity.F
//                    str = "Auto scroll disabled"
//                } else {
//                    mainActivity2.f2993t!!.gravity = 80
//                    val mainActivity3 = this@MainActivity
//                    mainActivity3.runOnUiThread(a(mainActivity3.f2993t, ""))
//                    mainActivity = this@MainActivity
//                    mainActivity.F = !mainActivity.F
//                    str = "Auto scroll enabled"
//                }
//                mainActivity.C(str)
//            } catch (e6: Exception) {
//                e6.printStackTrace()
//            }
//            return true
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class f( /* renamed from: a  reason: collision with root package name */
//                   /* synthetic */val f3005a: GestureDetector
//    ) : OnTouchListener {
//        // android.view.View.OnTouchListener
//        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
//            return f3005a.onTouchEvent(motionEvent)
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class g : View.OnClickListener {
//        // android.view.View.OnClickListener
//        override fun onClick(view: View) {
//            try {
//                val mainActivity = this@MainActivity
//                if (mainActivity.E) {
//                    val gVar: d3.g? = mainActivity.D
//                    gVar.o(
//                        """${f2994u!!.text}
//    """.toByteArray()
//                    )
//                    f2994u!!.setText("")
//                } else {
//                    mainActivity.C("Port not opened")
//                }
//            } catch (e6: Exception) {
//                e6.printStackTrace()
//            }
//        }
//    }
//
//    /* loaded from: classes.dex */
//    inner class h : MoPubView.BannerAdListener {
//        // com.mopub.mobileads.MoPubView.BannerAdListener
//        fun onBannerClicked(moPubView: MoPubView?) {}
//
//        // com.mopub.mobileads.MoPubView.BannerAdListener
//        fun onBannerCollapsed(moPubView: MoPubView?) {}
//
//        // com.mopub.mobileads.MoPubView.BannerAdListener
//        fun onBannerExpanded(moPubView: MoPubView?) {}
//
//        // com.mopub.mobileads.MoPubView.BannerAdListener
//        fun onBannerFailed(moPubView: MoPubView?, moPubErrorCode: MoPubErrorCode?) {}
//
//        // com.mopub.mobileads.MoPubView.BannerAdListener
//        fun onBannerLoaded(moPubView: MoPubView?) {
//            Objects.requireNonNull(this@MainActivity)
//        }
//    }
//
//    fun A() {
//        val str: String
//        var aVar: g.a
//        var byteBuffer: ByteBuffer
//        try {
//            val usbDevice = C
//            if (usbDevice != null) {
//                val c6: d3.g = d3.g.c(C, B!!.openDevice(usbDevice))
//                D = c6
//                if (c6 == null) {
//                    str = "Unsupported device"
//                } else if (!B!!.hasPermission(C)) {
//                    try {
//                        B!!.requestPermission(
//                            C,
//                            PendingIntent.getBroadcast(
//                                this,
//                                0,
//                                Intent("com.csa.serialmonitor.USB_PERMISSION"),
//                                0
//                            )
//                        )
//                        return
//                    } catch (e6: Exception) {
//                        e6.printStackTrace()
//                        return
//                    }
//                } else if (D.f()) {
//                    D.k(2)
//                    D.a(true)
//                    D.i(f2997x)
//                    D.j()
//                    D.m()
//                    D.l()
//                    D.a(false)
//                    val gVar: d3.g? = D
//                    val bVar = L
//                    if (gVar.f6793e && gVar.f6791c.also { aVar = it } != null) {
//                        aVar.f6795b = bVar
//                        val usbRequest: UsbRequest = aVar.f6796c
//                        val fVar: d3.f = gVar.f6790b
//                        synchronized(fVar) { byteBuffer = fVar.f6784a }
//                        usbRequest.queue(byteBuffer, 16384)
//                    }
//                    f2993t!!.text = ""
//                    E = true
//                    str = "Port opened"
//                } else {
//                    str = "Error opening port"
//                }
//            } else {
//                str = "No device connected"
//            }
//            C(str)
//        } catch (unused: Exception) {
//            C("Unspecified error occurred")
//        }
//    }
//
//    fun B() {
//        val moPubInterstitial: MoPubInterstitial? = I
//        if (moPubInterstitial == null) {
//            y()
//            return
//        }
//        if (!moPubInterstitial.isReady()) {
//            I.load()
//        } else if (J % 4 == 0) {
//            I.show()
//        }
//        J++
//    }
//
//    fun C(charSequence: CharSequence?) {
//        if (K) {
//            Toast.makeText(this, charSequence, 0).show()
//            return
//        }
//        try {
//            Toast.makeText(this, charSequence, 0).show()
//        } catch (e6: BadTokenException) {
//            e6.printStackTrace()
//        }
//    }
//
//    // androidx.fragment.app.h, androidx.activity.ComponentActivity, p.f, android.app.Activity
//    fun onCreate(bundle: Bundle?) {
//        val sharedPreferences: SharedPreferences = getSharedPreferences("Settings", 0)
//        f2998y = sharedPreferences.getInt("theme", 0)
//        f2997x = sharedPreferences.getInt("baud", 9600)
//        f2996w = sharedPreferences.getBoolean("kpScrOn", false)
//        f2999z = sharedPreferences.getInt("txtSize", f2999z)
//        val i6 = f2998y
//        if (i6 != 0) {
//            setTheme(A[i6])
//        }
//        super.onCreate(bundle)
//        setContentView(R.layout.activity_main)
//        K = Build.VERSION.SDK_INT != 25
//        f2992s = findViewById(R.id.btnSend) as Button?
//        f2993t = findViewById(R.id.txtRead) as TextView?
//        f2994u = findViewById(R.id.txtSend) as EditText?
//        f2995v = findViewById(R.id.rlMain) as RelativeLayout?
//        f2993t!!.movementMethod = ScrollingMovementMethod()
//        f2993t!!.textSize = f2999z.toFloat()
//        f2994u!!.textSize = f2999z.toFloat()
//        f2993t!!.setOnTouchListener(f(GestureDetector(this, e())))
//        f2992s!!.setOnClickListener(g())
//        f2995v!!.keepScreenOn = f2996w
//        B = ContextCompat.getSystemService("usb") as UsbManager?
//        val intentFilter = IntentFilter()
//        G = intentFilter
//        intentFilter.addAction("com.csa.serialmonitor.USB_PERMISSION")
//        G!!.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED")
//        G!!.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED")
//        z()
//        AudienceNetworkAds.initialize(this)
//        AppLovinSdk.initializeSdk(this)
//        val hashMap: HashMap<*, *> = HashMap<Any?, Any?>()
//        hashMap["applicationKey"] = "11914fc7d"
//        MoPub.initializeSdk(
//            this, Builder("").withMediatedNetworkConfiguration(
//                IronSourceAdapterConfiguration::class.java.getName(), hashMap
//            ).build(), b(this)
//        )
//    }
//
//    // android.app.Activity
//    fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        getMenuInflater().inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    // d.h, androidx.fragment.app.h, android.app.Activity
//    fun onDestroy() {
//        unregisterReceiver(N)
//        val moPubView: MoPubView? = H
//        if (moPubView != null) {
//            moPubView.destroy()
//        }
//        val moPubInterstitial: MoPubInterstitial? = I
//        if (moPubInterstitial != null) {
//            moPubInterstitial.destroy()
//        }
//        super.onDestroy()
//    }
//
//    // android.app.Activity
//    fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
//        val str: String
//        val str2: String
//        try {
//            val itemId = menuItem.itemId
//            when (itemId) {
//                R.id.about -> {
//                    try {
//                        str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName
//                    } catch (e6: PackageManager.NameNotFoundException) {
//                        e6.printStackTrace()
//                    }
//                    val aVar: b.a = a(this)
//                    val bVar: AlertController.b = aVar.f226a
//                    bVar.f213e = "Serial Monitor v$str"
//                    bVar.f211c = R.mipmap.ic_launcher
//                    bVar.g = bVar.f209a.getText(R.string.license_link)
//                    val a6: androidx.appcompat.app.b = aVar.a()
//                    a6.show()
//                    (a6.findViewById(16908299) as TextView).movementMethod =
//                        LinkMovementMethod.getInstance()
//                    return true
//                }
//
//                R.id.autoScrol -> {
//                    val z5 = !menuItem.isChecked
//                    try {
//                        F = z5
//                        if (z5) {
//                            f2993t!!.gravity = 80
//                            runOnUiThread(a(f2993t, ""))
//                            str2 = "Auto scroll enabled"
//                        } else {
//                            f2993t!!.gravity = 0
//                            str2 = "Auto scroll disabled"
//                        }
//                        C(str2)
//                    } catch (e7: Exception) {
//                        e7.printStackTrace()
//                    }
//                    return true
//                }
//
//                R.id.clr -> {
//                    f2993t!!.text = ""
//                    return true
//                }
//
//                R.id.disCnct -> {
//                    if (E) {
//                        x()
//                        B()
//                    } else {
//                        B()
//                        C("Port not opened")
//                    }
//                    return true
//                }
//
//                R.id.kpScrOn -> {
//                    u(!menuItem.isChecked)
//                    return true
//                }
//
//                R.id.reCnct -> {
//                    x()
//                    z()
//                    A()
//                    B()
//                    return true
//                }
//
//                else -> when (itemId) {
//                    R.id.baud0, R.id.baud1, R.id.baud2, R.id.baud3, R.id.baud4 -> {
//                        t(menuItem.title.toString().toInt())
//                        return true
//                    }
//
//                    else -> when (itemId) {
//                        R.id.theme0 -> v(0)
//                        R.id.theme1 -> v(1)
//                        R.id.theme2 -> v(2)
//                        R.id.theme3 -> v(3)
//                        else -> when (itemId) {
//                            R.id.txtSize0, R.id.txtSize1, R.id.txtSize2, R.id.txtSize3, R.id.txtSize4 -> {
//                                f2999z = menuItem.title.toString().toInt()
//                                w()
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (e8: Exception) {
//            e8.printStackTrace()
//        }
//        return super.onOptionsItemSelected(menuItem)
//    }
//
//    /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */ /* JADX WARN: Removed duplicated region for block: B:34:0x005c  */
//    // android.app.Activity
//    /*
//        Code decompiled incorrectly, please refer to instructions dump.
//        To view partially-correct code enable 'Show inconsistent code' option in preferences
//    */   fun onPrepareOptionsMenu(r4: Menu?): Boolean {
//        /*
//            r3 = this;
//            int r0 = r3.f2997x
//            r1 = 9600(0x2580, float:1.3452E-41)
//            r2 = 1
//            if (r0 == r1) goto L2b
//            r1 = 19200(0x4b00, float:2.6905E-41)
//            if (r0 == r1) goto L27
//            r1 = 38400(0x9600, float:5.381E-41)
//            if (r0 == r1) goto L23
//            r1 = 57600(0xe100, float:8.0715E-41)
//            if (r0 == r1) goto L1f
//            r1 = 115200(0x1c200, float:1.6143E-40)
//            if (r0 == r1) goto L1b
//            goto L35
//        L1b:
//            r0 = 2131296368(0x7f090070, float:1.821065E38)
//            goto L2e
//        L1f:
//            r0 = 2131296367(0x7f09006f, float:1.8210649E38)
//            goto L2e
//        L23:
//            r0 = 2131296366(0x7f09006e, float:1.8210647E38)
//            goto L2e
//        L27:
//            r0 = 2131296365(0x7f09006d, float:1.8210645E38)
//            goto L2e
//        L2b:
//            r0 = 2131296364(0x7f09006c, float:1.8210643E38)
//        L2e:
//            android.view.MenuItem r0 = r4.findItem(r0)
//            r0.setChecked(r2)
//        L35:
//            int r0 = r3.f2999z
//            r1 = 20
//            if (r0 == r1) goto L5c
//            r1 = 22
//            if (r0 == r1) goto L58
//            r1 = 25
//            if (r0 == r1) goto L54
//            r1 = 27
//            if (r0 == r1) goto L50
//            r1 = 30
//            if (r0 == r1) goto L4c
//            goto L66
//        L4c:
//            r0 = 2131296695(0x7f0901b7, float:1.8211314E38)
//            goto L5f
//        L50:
//            r0 = 2131296694(0x7f0901b6, float:1.8211312E38)
//            goto L5f
//        L54:
//            r0 = 2131296693(0x7f0901b5, float:1.821131E38)
//            goto L5f
//        L58:
//            r0 = 2131296692(0x7f0901b4, float:1.8211308E38)
//            goto L5f
//        L5c:
//            r0 = 2131296691(0x7f0901b3, float:1.8211306E38)
//        L5f:
//            android.view.MenuItem r0 = r4.findItem(r0)
//            r0.setChecked(r2)
//        L66:
//            r0 = 4
//            int[] r0 = new int[r0]
//            r0 = {x0090: FILL_ARRAY_DATA  , data: [2131296665, 2131296666, 2131296667, 2131296668} // fill-array
//            int r1 = r3.f2998y
//            r0 = r0[r1]
//            android.view.MenuItem r0 = r4.findItem(r0)
//            r0.setChecked(r2)
//            r0 = 2131296493(0x7f0900ed, float:1.8210904E38)
//            android.view.MenuItem r0 = r4.findItem(r0)
//            boolean r1 = r3.f2996w
//            r0.setChecked(r1)
//            r0 = 2131296354(0x7f090062, float:1.8210622E38)
//            android.view.MenuItem r4 = r4.findItem(r0)
//            boolean r0 = r3.F
//            r4.setChecked(r0)
//            return r2
//        */
//        throw UnsupportedOperationException("Method not decompiled: com.csa.serialmonitor.MainActivity.onPrepareOptionsMenu(android.view.Menu):boolean")
//    }
//
//    // d.h, androidx.fragment.app.h, android.app.Activity
//    fun onStart() {
//        super.onStart()
//        registerReceiver(M, G)
//        A()
//    }
//
//    // d.h, androidx.fragment.app.h, android.app.Activity
//    fun onStop() {
//        super.onStop()
//        unregisterReceiver(M)
//        x()
//    }
//
//    fun t(i6: Int) {
//        try {
//            x()
//            val edit: SharedPreferences.Editor = getSharedPreferences("Settings", 0).edit()
//            f2997x = i6
//            edit.putInt("baud", i6)
//            edit.apply()
//            A()
//            B()
//            C("Baud rate set to $i6")
//        } catch (e6: Exception) {
//            e6.printStackTrace()
//        }
//    }
//
//    fun u(z5: Boolean) {
//        try {
//            f2996w = z5
//            f2995v!!.keepScreenOn = z5
//            val edit: SharedPreferences.Editor = getSharedPreferences("Settings", 0).edit()
//            edit.putBoolean("kpScrOn", f2996w)
//            edit.apply()
//            C(if (f2996w) "Keep screen on enabled" else "Keep screen on disabled")
//        } catch (e6: Exception) {
//            e6.printStackTrace()
//        }
//    }
//
//    fun v(i6: Int) {
//        val edit: SharedPreferences.Editor = getSharedPreferences("Settings", 0).edit()
//        f2998y = i6
//        setTheme(A[i6])
//        edit.putInt("theme", f2998y)
//        edit.commit()
//        x()
//        ContextCompat.startActivity(Intent(getApplicationContext(), MainActivity::class.java))
//        finish()
//        B()
//    }
//
//    fun w() {
//        f2993t!!.textSize = f2999z.toFloat()
//        f2994u!!.textSize = f2999z.toFloat()
//        val edit: SharedPreferences.Editor = getSharedPreferences("Settings", 0).edit()
//        edit.putInt("txtSize", f2999z)
//        edit.apply()
//        C("Text size changed to " + f2999z + "sp")
//    }
//
//    fun x() {
//        try {
//            if (E) {
//                D.b()
//                E = false
//                C("Port closed")
//            }
//        } catch (e6: Exception) {
//            e6.printStackTrace()
//        }
//    }
//
//    fun y() {
//        val moPubView: MoPubView = findViewById(R.id.bannerAdContainer) as MoPubView
//        H = moPubView
//        moPubView.setAdUnitId(getString(R.string.banner_id))
//        H.setAdSize(MoPubView.MoPubAdSize.HEIGHT_50)
//        H.setBannerAdListener(h())
//        val moPubInterstitial = MoPubInterstitial(this, getString(R.string.interstitial_id))
//        I = moPubInterstitial
//        moPubInterstitial.setInterstitialAdListener(a())
//        registerReceiver(N, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
//    }
//
//    fun z() {
//        try {
//            val deviceList = B!!.deviceList
//            val size = deviceList.size
//            if (size == 1) {
//                C = deviceList.entries.iterator().next().value
//            } else if (size == 0) {
//                C = null
//            } else {
//                for ((_, value): Map.Entry<String, UsbDevice> in deviceList) {
//                    C = value
//                    if (d3.g.c(C, B!!.openDevice(value)) != null) {
//                        return
//                    }
//                    C = null
//                }
//            }
//        } catch (e6: Exception) {
//            e6.printStackTrace()
//        }
//    }
//
//    companion object {
//        /* synthetic */ const val O = 0
//    }
//}