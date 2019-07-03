package com.example.proyecto.activities


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toast
import com.example.proyecto.CredentialsManager
import com.example.proyecto.R
import fragments.*
import com.example.proyecto.RequestCode
import com.example.proyecto.db.AppDatabase
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),  OnMapReadyCallback {

    private var currentLoadedFragment: Fragment? = null
    private lateinit var mMap: GoogleMap

    //para la localizacion
    private var latitude:Double=0.toDouble()
    private var longitude:Double=0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker?=null

    //Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    companion object{
        private const val MY_PERMISSION_CODE: Int = 1000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.MapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //Request runtime permission

        setToolbar()
        loadUserDataOrSendToLoginActivity()


    }

    private fun setListener() {
        mMap.setOnInfoWindowClickListener {
            val name = it.title.toString()
            val position = it.position
            if(name != "Your position" ){
                val intent = Intent(this, FieldActivity::class.java)
                intent.putExtra("NAME", name)
                intent.putExtra("LATITUDE", position.latitude)
                intent.putExtra("LONGITUDE", position.longitude)

                startActivity(intent)
            }

        }
    }

    private fun checkLocationPermission() :Boolean {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this,arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(this,arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            return false
        }
        else
            return true
    }

    //Override onRequestPermissionResult

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSION_CODE ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                        if(checkLocationPermission()){
                            buildLocationRequest()
                            buildLocationCallBack()

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                            mMap!!.isMyLocationEnabled=true
                        }
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations.get(p0!!.locations.size-1)//get last location

                if(mMarker != null){
                    mMarker!!.remove()
                }

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap!!.addMarker(markerOptions)

                //move Camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    override fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Init GooglePlay Services
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mMap!!.isMyLocationEnabled = true
            }

        } else {
            mMap!!.isMyLocationEnabled = true
        }

        //Enable Zoom control
        mMap.uiSettings.isZoomControlsEnabled=true
        setListener()
        loadFieldMarkers()
    }


    private fun loadFieldMarkers() {
        val fieldDao = AppDatabase.getDatabase(this).fieldDao()
        GlobalScope.launch(Dispatchers.IO){
            val fields = fieldDao.getAllFields()
            launch(Dispatchers.Main){
                if (fields != null) {
                    for (field in fields) {
                        var place = LatLng(field.latitude, field.longitude)
                        mMap!!.addMarker(
                            MarkerOptions()
                                .position(place)
                                .title(field.name)
                        )
                    }
                }
            }
        }
    }

    private fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
        setupNavViewListener()
    }

    private fun setupNavViewListener() {
        navView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            val transaction = supportFragmentManager.beginTransaction()

            when (menuItem.itemId) {
                R.id.home ->{
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
                R.id.profile -> {
                    val profileFragment = supportFragmentManager.findFragmentByTag("profileFrag")
                    if (profileFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, profileFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Profile(), "profileFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_home_title)
                }

                R.id.request -> {
                    val requestsFragment = supportFragmentManager.findFragmentByTag("requestsFrag")
                    if (requestsFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, requestsFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Join_request(), "requestsFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_join_requests_title)
                }

                R.id.payment -> {
                    val paymentsFragment = supportFragmentManager.findFragmentByTag("paymentsFrag")
                    if (paymentsFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, paymentsFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Payment_method(), "paymentsFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_payment_methods_title)
                }

                R.id.signOut -> {
                    onSignOut()
                }
            }

            transaction.commit()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun onSignOut(){
        CredentialsManager.getInstance(baseContext).deleteUser()
        goToLoginActivity()
    }

    private fun loadUserDataOrSendToLoginActivity() {

        val userData = loadUserData()
        if (userData != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkLocationPermission()) {
                    buildLocationRequest()
                    buildLocationCallBack()

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            }
            else{
                buildLocationRequest()
                buildLocationCallBack()

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

            }



        }
        else{
            goToLoginActivity()
        }

    }


    private fun loadUserData(): Pair<String, String>? {
        return CredentialsManager.getInstance(baseContext).loadUser()
    }


    private fun goToLoginActivity() {
        // Clear current fragment to release memory.
        if (currentLoadedFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(currentLoadedFragment!!).commit()
        }
        // Initialize LogIn Activity
        startActivityForResult(
            Intent(this, LoginActivity::class.java),
            RequestCode.GO_TO_LOGIN_FROM_MAIN_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RequestCode.GO_TO_LOGIN_FROM_MAIN_ACTIVITY -> {
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        storeCredentials(data.extras!!)
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(checkLocationPermission()) {
                                buildLocationRequest()
                                buildLocationCallBack()

                                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                            }
                        }
                        else{
                            buildLocationRequest()
                            buildLocationCallBack()

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                        }
                    }

                }
            }
        }
    }

    private fun storeCredentials(bundle: Bundle) {
        val userEmail = bundle.getString("EMAIL")
        val userPass = bundle.getString("PASSWORD")
        CredentialsManager.getInstance(baseContext).saveUser(userEmail, userPass)
    }

}

