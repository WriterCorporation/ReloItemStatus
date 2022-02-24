package com.example.reloiteminfo

import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.github.gcacace.signaturepad.views.SignaturePad

class Signature : AppCompatActivity(),LocationListener {
    var lat :String ?=null
    var long :String?=null
    lateinit var tvLocation : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Take Signature")
        initView()

    }

    private fun initView(){
        var signaturePad = findViewById<SignaturePad>(R.id.csignatare_pad)
        var btnSave = findViewById<AppCompatButton>(R.id.saveBtn)
        var btnClear = findViewById<AppCompatButton>(R.id.clearBtn)

        btnSave.setOnClickListener {
            Toast.makeText(this,"Signature saved successfully",Toast.LENGTH_LONG).show()
            signaturePad.clear()
        }

        btnClear.setOnClickListener {
            signaturePad.clear()
        }


        tvLocation = findViewById(R.id.tv_latlong)
    }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude.toString()
        long = location.longitude.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}