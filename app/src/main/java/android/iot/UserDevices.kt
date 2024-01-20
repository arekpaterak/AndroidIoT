package android.iot

import android.app.AlertDialog
import android.content.Intent
import android.iot.bluetooth.BluetoothListDeviceAdapter
import android.iot.bluetooth.Data
import android.iot.bluetooth.RecyclerViewClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class UserDevices : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "sharedPrefs"
        const val USERNAME = "username"
        const val LOGGED_IN = "loggedIn"
    }

    private val devices = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_devices)


        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            val intentMain = Intent(
                this@UserDevices,
                MainActivity::class.java
            )
            this@UserDevices.startActivity(intentMain)
        }

        val addButton = findViewById<ImageButton>(R.id.addDevice)
        addButton.setOnClickListener {
            if (getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getBoolean(LOGGED_IN, false)) {
                val intentMain = Intent(
                    this@UserDevices,
                    PairedDeviceListActivity::class.java
                )

                intentMain.putExtra("devicesAddresses", devices)
                this@UserDevices.startActivity(intentMain)
            } else {
                //  User is not logged in
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Pleas log in to add a device!")
                    .setTitle("Not logged in!")

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        val data = this.getData()

        val listener = object: RecyclerViewClickListener() {
            override fun onClick(index: Int) {
                super.onClick(index)

                val intentMain = Intent(
                    this@UserDevices,
                    DeviceData::class.java
                )

                //  TODO: Fill intent with data that device data activity needs!!!

                this@UserDevices.startActivity(intentMain)
            }
        }

        val adapter = BluetoothListDeviceAdapter(data, this, listener)


        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.itemsList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    }


    private fun getData(): ArrayList<Data> {
        val output = ArrayList<Data>()

        output.add(Data("Device 1", "00:00:00:00:00:00"))
        output.add(Data("Device 2", "00:00:00:00:00:00"))
        output.add(Data("Device 3", "00:00:00:00:00:00"))

        return output
    }
}