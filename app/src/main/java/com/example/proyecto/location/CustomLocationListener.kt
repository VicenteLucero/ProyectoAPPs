package com.example.proyecto.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class CustomLocationListener : LocationListener {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("INFO","onStatusChanged: provider $provider status $status extras $extras")
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

}