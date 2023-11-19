package com.example.chat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.chat.R.drawable.ic_notification
import com.felhr.usbserial.UsbSerialDevice
import com.felhr.usbserial.UsbSerialInterface
import java.lang.ref.WeakReference


class MainActivity : ComponentActivity (){



    lateinit var m_usbManager : UsbManager
    var m_device : UsbDevice? = null
    var m_serial : UsbSerialDevice? = null
    var m_connection : UsbDeviceConnection? = null
    var ACTION_USB_PERMISSION = "permission"

    private lateinit var connectButton: Button
    private lateinit var disconnectButton: Button
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var outputTextView: TextView




        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            m_usbManager = getSystemService(Context.USB_SERVICE) as UsbManager

            val filter = IntentFilter()
            filter.addAction(ACTION_USB_PERMISSION)
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
            registerReceiver(broadcastReceiver, filter)

            sendButton = findViewById(R.id.sendButton)
            inputEditText = findViewById(R.id.inputEditText)


            sendButton.setOnClickListener {
                // Get the text from the EditText
                val enteredText = inputEditText.text.toString()

                // Check if the enteredText is not empty
                if (enteredText.isNotEmpty()) {
                    // Display the text using a Toast

                    sendData("$enteredText")
                } else {
                    showToast("Please enter text first.")
                }
            }


            connectButton = findViewById(R.id.connectButton)
            outputTextView = findViewById(R.id.outputTextView)



//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channelId = "your_channel_id"
//                val channelName = "Your Channel Name"
//                val importance = NotificationManager.IMPORTANCE_DEFAULT
//                val channel = NotificationChannel(channelId, channelName, importance)
//                val notificationManager = getSystemService(NotificationManager::class.java)
//                notificationManager.createNotificationChannel(channel)
//            }
//
//
//// Create an explicit intent for the notification
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//            // Build the notification
//            val notificationBuilder = NotificationCompat.Builder(this, "your_channel_id")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle("Notification Title")
//                .setContentText("Notification Text")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//// Show the notification
//            val notificationManager = NotificationManagerCompat.from(this)
//            val notificationId = 0
//            notificationManager.notify(notificationId, notificationBuilder.build())

        }






    private fun startUsbConnecting (){
        val usbDevices : HashMap<String, UsbDevice> ? = m_usbManager.deviceList

        if(!usbDevices?.isEmpty()!!){
            var keep = true
            usbDevices.forEach { entry ->
                m_device = entry.value
                val deviceVendorId : Int? = m_device?.vendorId
                Log.i("serial", "vendorId " +deviceVendorId)
                showToast("vendorId " +deviceVendorId)

                if (deviceVendorId == 6790){
                    val intent : PendingIntent = PendingIntent.getBroadcast(this, 0 , Intent(ACTION_USB_PERMISSION), 0)
                    m_usbManager.requestPermission(m_device, intent)
                    keep = false
                    Log.i("serial", "Connection successfull!")
                    showToast("Connection successfull!")

                }else{
                    m_connection = null
                    m_device = null
                    Log.i("serial", "unable to connect")
                    showToast("unable to connect")
                }

                if (!keep){
                    return
                }


            }

        }else{
            Log.i("serial", "no usb device connected!")
            showToast("no usb device connected!")
        }


    }


//send data
private fun sendData (input : String) {
   m_serial?.write(input.toByteArray())



}


//read data
    private fun readData() {
        m_serial?.read(object : UsbSerialInterface.UsbReadCallback {
            override fun onReceivedData(data: ByteArray?) {
                if (data != null) {
                    val receivedData = String(data, Charsets.UTF_8)
                    Log.i("serial", "Received Data: $receivedData")
                    showToast("Received Data: $receivedData")
                    runOnUiThread{
                        outputTextView.text = "$receivedData"
                    }

                    // Create a notification
                    val notificationBuilder = NotificationCompat.Builder(this@MainActivity, "your_channel_id")
                        .setSmallIcon(ic_notification)
                        .setContentTitle("your data !")
                       
                        .setContentText(receivedData)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)

                    // Show the notification
                    val notificationManager = NotificationManagerCompat.from(this@MainActivity)
                    val notificationId = 0
                    notificationManager.notify(notificationId, notificationBuilder.build())



                    // You can process or display the received data as needed.
                }
            }
        })
    }



    //disconnect
    private fun disconnect () {

        m_serial?.close()

    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            sendButton = findViewById(R.id.sendButton)

            if(intent?.action!! == ACTION_USB_PERMISSION){
                val granted : Boolean = intent.extras!!.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)

                if (granted){
                    m_connection = m_usbManager.openDevice(m_device)
                    m_serial = UsbSerialDevice.createUsbSerialDevice(m_device, m_connection)



                    if(m_serial != null){
                        if(m_serial!!.open()){
                            m_serial!!.setBaudRate(9600)
                            m_serial!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
                            m_serial!!.setStopBits(UsbSerialInterface.STOP_BITS_1)
                            m_serial!!.setParity(UsbSerialInterface.PARITY_NONE)
                            m_serial!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)


                            //read data function
                            readData()

                        }else{
                            Log.i("serial", "port not open")
                            showToast("port not open")
                        }
                    }else{
                        Log.i("serial" , "serial is null")
                        showToast("serial is null")
                    }

                }else{
                    Log.i("serial", "permission not granted!")
                    showToast("permission not granted!")
                }

            }else if(intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED){

                startUsbConnecting()

            }else if (intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                disconnect()
            }
        }
    }


        private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }


}






















