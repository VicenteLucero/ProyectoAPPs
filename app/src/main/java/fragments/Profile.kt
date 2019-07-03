package fragments


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyecto.CredentialsManager
import com.example.proyecto.R
import com.example.proyecto.RequestCode
import com.example.proyecto.db.AppDatabase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Profile : Fragment() {

    private var currentPhotoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        setListener()
    }

    private fun setUser() {
        val userData  = userdata()!!
        Log.d(userData.first, "nombre usuario")
        val email= userData.first
        val pass = userData.second
        GlobalScope.launch(Dispatchers.IO){
            val user = AppDatabase.getDatabase(context!!).userDao().getUser(email, pass)
            Log.d(user.name, "nombre usuario database")
            launch(Dispatchers.Main){
                userNameValue.text = user.name
                userLastNameValue.text =  user.last_name
                userEmailValue.text = user.email
                if(user.photo == null){

                }
                else{

                }
            }

        }
    }

    private fun userdata(): Pair<String, String>? {
        return CredentialsManager.getInstance(context!!).loadUser()
    }

    private fun updateUser(){
        val name = nameEdit.text.toString()
        val last = lastEdit.text.toString()
        val email = emailEdit.text.toString()
        //profileImage
        val pass = passEdit.text.toString()
        val currentCredentials = CredentialsManager.getInstance(context!!).loadUser()!!

        if (name == null ||  last == null || email==null || pass == null ){
            Toast.makeText(context!!, "You can't leave empty fields", Toast.LENGTH_LONG).show()
        }
        else {
            CredentialsManager.getInstance(context!!).deleteUser()
            CredentialsManager.getInstance(context!!).saveUser(email, pass)
            GlobalScope.launch(Dispatchers.IO) {
                val userDao = AppDatabase.getDatabase(context!!).userDao()
                val user = userDao.getUser(currentCredentials.first, currentCredentials.second)
                user.name = name
                user.last_name = last
                user.email = email
                user.password = pass
                userDao.update(user)
                setUser()

            }
        }
    }

    private fun dispatchToCameraActivity() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("ERROR", "Error creating file: ${ex.message}")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, RequestCode.REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            setPic()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun setPic() {
        // Get the dimensions of the View
        profileImage.setPadding(0, 0, 0, 0)
        val targetW: Int = profileImage.width
        val targetH: Int = profileImage.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPhotoPath, this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            profileImage.setImageBitmap(bitmap)
        }
    }

    private fun setListener() {
        profileEditButton.setOnClickListener {
            val name = userNameValue.text.toString()
            val last = userLastNameValue.text.toString()
            val email = userEmailValue.text.toString()
            val pass = userdata()!!.second

            userEmailValue.visibility = View.INVISIBLE
            userLastNameValue.visibility = View.INVISIBLE
            userNameValue.visibility = View.INVISIBLE
            profileEditButton.visibility = View.INVISIBLE

            nameEdit.visibility = View.VISIBLE
            nameEdit.hint = name
            nameEdit.setText(userNameValue.text.toString())

            lastEdit.visibility = View.VISIBLE
            lastEdit.hint = last
            lastEdit.setText(userLastNameValue.text.toString())

            emailEdit.visibility = View.VISIBLE
            emailEdit.hint = email
            emailEdit.setText(userEmailValue.text.toString())

            imageEdit.visibility = View.VISIBLE
            passText.visibility = View.VISIBLE
            passEdit.visibility = View.VISIBLE
            passEdit.hint = pass
            passEdit.setText(pass)

            cancelEdit.visibility = View.VISIBLE
            commitButton.visibility = View.VISIBLE


        }

        cancelEdit.setOnClickListener {
            userEmailValue.visibility = View.VISIBLE
            userLastNameValue.visibility = View.VISIBLE
            userNameValue.visibility = View.VISIBLE
            profileEditButton.visibility = View.VISIBLE

            nameEdit.visibility = View.INVISIBLE
            lastEdit.visibility = View.INVISIBLE
            emailEdit.visibility = View.INVISIBLE
            imageEdit.visibility = View.INVISIBLE
            passText.visibility = View.INVISIBLE
            passEdit.visibility = View.INVISIBLE
            cancelEdit.visibility = View.INVISIBLE
            commitButton.visibility = View.INVISIBLE
        }

        commitButton.setOnClickListener {
            userEmailValue.visibility = View.VISIBLE
            userLastNameValue.visibility = View.VISIBLE
            userNameValue.visibility = View.VISIBLE
            profileEditButton.visibility = View.VISIBLE

            nameEdit.visibility = View.INVISIBLE
            lastEdit.visibility = View.INVISIBLE
            emailEdit.visibility = View.INVISIBLE
            imageEdit.visibility = View.INVISIBLE
            passText.visibility = View.INVISIBLE
            passEdit.visibility = View.INVISIBLE
            cancelEdit.visibility = View.INVISIBLE
            commitButton.visibility = View.INVISIBLE
            updateUser()
        }

        imageEdit.setOnClickListener {
            if (hasCamera()) {
                dispatchToCameraActivity()
            } else {
                Toast.makeText(context!!, "Your device doesn't have a camera built in", Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun hasCamera(): Boolean {
        return context!!.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }


}
