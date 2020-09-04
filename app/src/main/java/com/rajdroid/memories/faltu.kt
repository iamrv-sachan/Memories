package com.rajdroid.memories

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import android.media.audiofx.Equalizer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_memories.*
import kotlinx.android.synthetic.main.activity_add_memories.date
import kotlinx.android.synthetic.main.activity_faltu.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

//@Suppress("DEPRECATION")
class faltu : AppCompatActivity(), View.OnClickListener {

    companion object
    {
        private const val Camera_permission_Code=1
        private const val Camera=2
        private const val GALLERY = 1
    }

    private var cal=Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faltu)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_YEAR,dayOfMonth)
            updateDateInView()
        }
        date.setOnClickListener(this)
        addimage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.date -> {
                DatePickerDialog(this@faltu,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_YEAR)).show()
            }

            R.id.addimage ->
            {

                Log.i("faltu", "ghus")
                val picdialog= AlertDialog.Builder(this)
                picdialog.setTitle("Select :")
                val dialogItem= arrayOf("Choose from Gallery","Open Camera")

                picdialog.setItems(dialogItem)
                {
                    _, which ->
                    when(which)
                    {
                        0 -> choosePicFromGallery()
                        1 -> {
                            Toast.makeText(this@faltu, "selection of camera coming soon", Toast.LENGTH_SHORT).show()

                            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                            {
                                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, Camera)
                            }
                            else
                            {
                                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                                    Camera_permission_Code)
                            }

                        }
                    }
                }.show()

            }
        }
    }



    @Suppress("DEPRECATION")
    private fun choosePicFromGallery()
    {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(object :
            MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {if(report.areAllPermissionsGranted())
            {
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, GALLERY)
            }
            }
            override fun onPermissionRationaleShouldBeShown(permissions:MutableList<PermissionRequest> ,token: PermissionToken) {
                showRationalDialogPermission()
            }
        }).onSameThread().check()
    }

    private fun showRationalDialogPermission()
    {
        AlertDialog.Builder(this).setMessage("turn on permission").setPositiveButton("go to settings")
        {
            _,_ ->
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package",packageName,null )
                intent.data=uri
                startActivity(intent)
            }
            catch (E:ActivityNotFoundException)
            {
                E.printStackTrace()            }
        }.setNegativeButton("Cancel"){
            dialog, which ->
            dialog.dismiss()
        }.show()
    }

    private fun updateDateInView()
    {
        val myFormat ="dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat,Locale.getDefault())
        date.setText(sdf.format(cal.time).toString())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==Camera_permission_Code)
        {
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, Camera)
            }
            else
            {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == Camera)
            {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                image.setImageBitmap(thumbnail)
            }
        }
        else if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == GALLERY)
            {
                if(data!=null)
                {
                    val contentURI=data.data
                    val selectImageBitmap =  MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI)
                    image.setImageBitmap(selectImageBitmap)
//                    try {
//
//                    }
//                    catch (e:IOException)
//                    {
//                        e.printStackTrace()
//                        Toast.makeText(this@faltu, "failed", Toast.LENGTH_SHORT).show()
//                    }
                }
            }
        }
    }
}