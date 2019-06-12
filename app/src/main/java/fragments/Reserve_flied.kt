package fragments


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.proyecto.R
import com.example.proyecto.RequestCode
import com.example.proyecto.location.CustomLocationListener
import java.lang.Exception

class Reserve_flied : Fragment() {

    private lateinit var locationManager : LocationManager
    private val customLocationListener = CustomLocationListener()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserve_flied, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForGPSAccess()
        addListeners()
    }

    private fun askForGPSAccess(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){

        }
        else{
            if(checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
                requestPermissions(Array(2){Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.FINE_LOCATION_REQUEST)
            }
        }
    }

    private fun startReadingGPSPosition() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }
        else {
            locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = getLastKnowLocation()
            if (location != null){
                customLocationListener.latitude = location.latitude
                customLocationListener.longitude = location.latitude
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10f, customLocationListener)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnowLocation() : Location? {
        val providers = locationManager.getProviders(true)
        var bestLocation : Location? = null
        providers.forEach {provider ->
            val l = locationManager.getLastKnownLocation(provider)
            if (l != null) {
                if (bestLocation == null || l.accuracy < bestLocation!!.accuracy){
                    bestLocation = l
                }
            }
        }
        return bestLocation
    }

    private fun addListeners(){

    }

    private fun stopGPS(){
        try{
            locationManager.removeUpdates(customLocationListener)
        }
        catch (e: Exception){
            Log.d("INFO", "Exception ocurred while stop reading position -> ${e.message}")
        }
    }

    override fun onResume() {
        super.onResume()
        startReadingGPSPosition()
    }

    override fun onPause() {
        super.onPause()
        stopGPS()
    }


}
