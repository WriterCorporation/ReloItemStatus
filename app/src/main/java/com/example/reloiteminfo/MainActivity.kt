package com.example.reloiteminfo

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.reloiteminfo.data.model.ItemDetails

class MainActivity : AppCompatActivity(),LocationListener {
    val PERMISSION_CODE: Int = 100901
    var lat :String ?=null
    var long :String?=null
    lateinit var tvLocation : TextView
    lateinit var cvItem : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()

        initView()
        getLocation()
    }

    private fun initView(){
        var itemInfo = ItemDetails()
        var searchText = findViewById<EditText>(R.id.et_search)
        var searchButton = findViewById<CardView>(R.id.loginBtn)
        cvItem = findViewById<CardView>(R.id.cv)
        var name = findViewById<TextView>(R.id.tvName)
        var address = findViewById<TextView>(R.id.tvAddress)
        var item = findViewById<TextView>(R.id.tvItem)
        var btnRd = findViewById<AppCompatButton>(R.id.btn_rd)
        var btnNd = findViewById<AppCompatButton>(R.id.btn_nd)
        tvLocation = findViewById(R.id.tv_latlong)


        searchButton.setOnClickListener {

            if(searchText.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter item name",Toast.LENGTH_LONG).show()
            }else{
                cvItem.visibility = View.VISIBLE
                name.text = itemInfo.Name
                address.text = itemInfo.Address
                item.text = itemInfo.Item
            }
        }

        btnRd.setOnClickListener {
            searchText.setText("")
            cvItem.visibility = View.GONE
            val intent = Intent(this,Signature::class.java)
            startActivity(intent)
        }

        btnNd.setOnClickListener {
            openRoomDialog()
        }

        tvLocation.text= lat + " "+ long

    }

    fun getLocation() {

        val locationManager: LocationManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No Location Permission", Toast.LENGTH_LONG).show()
        } else {
            Log.e("Permission granted","done")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1F, this)
        }

    }


    fun openRoomDialog(flag : Boolean = false,position: Int =0) {
        val lp = WindowManager.LayoutParams()
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.select_room_exception)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        val exception = dialog.findViewById<EditText>(R.id.tv_notes)
        val btnSave = dialog.findViewById<AppCompatButton>(R.id.btn_save)
        val btnCancel = dialog.findViewById<ImageView>(R.id.cancel_icon)

        btnSave.setOnClickListener {
            if(exception.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter some remark",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Your remark is saved",Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.attributes = lp
        dialog.window!!.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent))
        dialog.show()
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                )
            {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ), PERMISSION_CODE
                )
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(cvItem.isVisible)
            cvItem.visibility = View.GONE
    }

    override fun onLocationChanged(location: Location) {
        Log.e("OnLocation Changed","Call");
        lat = location.latitude.toString()
        long = location.longitude.toString()
        tvLocation.text = lat + long
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.e("OnStatus Changed","Call")
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        Log.e("OnProvider Enabled","Call")
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        Log.e("OnProvider Disabled","Call")
    }


}