//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.hardware.usb.UsbDevice
//import android.hardware.usb.UsbManager
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.os.Message
//
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import com.example.chat.R
//import com.felhr.usbserial.UsbSerialDevice
//import com.felhr.usbserial.UsbSerialInterface
//import java.io.UnsupportedEncodingException
//import java.lang.ref.WeakReference
//
//class MainActivity : ComponentActivity() {
//
//    private lateinit var usbManager: UsbManager
//    private lateinit var usbDevice: UsbDevice
//    private var serialPort: UsbSerialDevice? = null
//
//    private lateinit var textView: TextView
//
//    private val usbReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            try {
//                when (intent.action) {
//                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
//                        val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
//                        if (device != null) {
//                            usbDevice = device
//                            startSerialConnection()
//                        }
//                    }
//                    UsbManager.ACTION_USB_DEVICE_DETACHED -> {
//                        Toast.makeText(context, "USB device detached", Toast.LENGTH_SHORT).show()
//                        if (serialPort != null) {
//                            serialPort!!.close()
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private val mHandler = MyHandler(this)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
//        textView = findViewById(R.id.textView)
//
//        val filter = IntentFilter()
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
//        registerReceiver(usbReceiver, filter)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        try {
//            val deviceList = usbManager.deviceList
//            for (entry in deviceList.entries) {
//                val device = entry.value
//                if (device != null) {
//                    usbDevice = device
//                    startSerialConnection()
//                    break
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        try {
//            if (serialPort != null) {
//                serialPort!!.close()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun startSerialConnection() {
//        try {
//            serialPort = UsbSerialDevice.createUsbSerialDevice(usbDevice, usbManager.openDevice(usbDevice))
//            if (serialPort != null) {
//                if (serialPort!!.open()) {
//                    serialPort!!.setBaudRate(9600)
//                    serialPort!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
//                    serialPort!!.setStopBits(UsbSerialInterface.STOP_BITS_1)
//                    serialPort!!.setParity(UsbSerialInterface.PARITY_NONE)
//                    serialPort!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
//
//                    serialPort!!.read { data ->
//                        try {
//                            if (data != null) {
//                                val receivedData: String = String(data, charset("UTF-8"))
//                                mHandler.obtainMessage(MESSAGE_FROM_ARDUINO, receivedData)
//                                    .sendToTarget()
//                            }
//                        } catch (e: UnsupportedEncodingException) {
//                            e.printStackTrace()
//                            Toast.makeText(
//                                this@MainActivity,
//                                "Error: ${e.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "Failed to open serial port", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Serial port is not available", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private class MyHandler(looper: Looper, activity: MainActivity) : Handler(looper) {
//        private val mActivity: WeakReference<MainActivity> = WeakReference(activity)
//
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                MESSAGE_FROM_ARDUINO -> {
//                    try {
//                        val receivedData = msg.obj as String
//                        mActivity.get()?.updateTextView(receivedData)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        Toast.makeText(
//                            mActivity.get(),
//                            "Error: ${e.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        }
//    }
//
//
//    private fun updateTextView(data: String) {
//        textView.text = data
//    }
//
//    companion object {
//        private const val MESSAGE_FROM_ARDUINO = 0
//    }
//}
