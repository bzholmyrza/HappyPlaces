package com.happyplaces.activities

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.happyplaces.R
import com.happyplaces.models.HappyPlaceModel

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    var map: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    //var searchView: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        var happyPlaceDetailModel: HappyPlaceModel? = null
        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            // get the Serializable data model class with the details in it
            happyPlaceDetailModel = intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as HappyPlaceModel
        }
        if (happyPlaceDetailModel != null) {
            val latLng = LatLng(happyPlaceDetailModel.latitude, happyPlaceDetailModel.longitude)
            map!!.addMarker(MarkerOptions().position(latLng).title(happyPlaceDetailModel.title))
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
        mapFragment!!.getMapAsync(this)}
        override fun onMapReady(googleMap: GoogleMap?) {
            map = googleMap
        }
}