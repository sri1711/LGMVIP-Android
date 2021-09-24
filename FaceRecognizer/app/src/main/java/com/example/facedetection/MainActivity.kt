package com.example.facedetection

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import java.io.IOException

import android.util.SparseArray

import android.R.attr.bitmap
import android.animation.ObjectAnimator
import android.graphics.*
import android.os.Handler
import android.view.View
import android.widget.*

import com.google.mlkit.vision.face.*
import de.hdodenhof.circleimageview.CircleImageView
import android.util.DisplayMetrics





class MainActivity : AppCompatActivity() {
    var selectedPhotoUri : Uri? = null
    var bitmap : Bitmap ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val Logo = findViewById<LinearLayout>(R.id.llLogo)
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            ObjectAnimator.ofFloat(Logo, "translationY", -(height.toFloat()/3)).apply {
                duration = 2000
                start()
            }
            Handler().postDelayed({

                findViewById<LinearLayout>(R.id.llContentContainer).visibility = View.VISIBLE

            },2000)
        },3000 )

        val ibtn_profile = findViewById<CircleImageView>(R.id.btn_select_image)
        ibtn_profile.setOnClickListener{
            findViewById<TextView>(R.id.tvMessage).text = ""
            findViewById<TextView>(R.id.tvMessage).visibility = View.GONE
            Toast.makeText(this, "Select photo", Toast.LENGTH_SHORT).show()
            val i = Intent(Intent.ACTION_PICK)
            i.type= "image/*"
            startActivityForResult(i, 0)

        }
        findViewById<Button>(R.id.btn_find_face).setOnClickListener {
            findViewById<TextView>(R.id.tvMessage).text = ""
            findViewById<TextView>(R.id.tvMessage).visibility = View.GONE
            findViewById<LinearLayout>(R.id.llLoading).visibility = View.VISIBLE
            if(selectedPhotoUri != null && bitmap != null) {
                detect_face()
            }
            else{
                findViewById<TextView>(R.id.tvMessage).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tvMessage).text = "Please Select an Image!"
                findViewById<LinearLayout>(R.id.llLoading).visibility = View.GONE
            }
        }
    }

    fun detect_face(){
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        var image: InputImage ? = null

        try {
            image = InputImage.fromFilePath(this, selectedPhotoUri)
        } catch (e: IOException) {
            findViewById<TextView>(R.id.tvMessage).text = e.message
            e.printStackTrace()
        }
        val detector = FaceDetection.getClient(highAccuracyOpts)
        var flag = false
        val result = detector.process(image)
            .addOnSuccessListener { faces ->

                for (face in faces) {
                    val bounds = face.boundingBox
                    val tempBitmap = bitmap!!.copy(Bitmap.Config.RGB_565, true);
                    val canvas = Canvas(tempBitmap)
                    val paint = Paint()
                    paint.setAlpha(0xA0)
                    paint.setColor(Color.RED)
                    paint.setStyle(Paint.Style.STROKE)
                    paint.setStrokeWidth(4F)
                    canvas.drawRect(bounds, paint)
                    findViewById<LinearLayout>(R.id.llLoading).visibility = View.GONE
                    findViewById<ImageView>(R.id.btn_select_image).setImageBitmap(tempBitmap)

                    if (face.smilingProbability != null) {
                        val smileProb = face.smilingProbability
                        Log.i("SRI171103109","${smileProb}")
                    }
                    else{
                        Log.i("SRI171103109","${null}")
                    }
                }
                Log.i("SRI1711",faces.toString())
                if(faces.isEmpty()) {
                    findViewById<LinearLayout>(R.id.llLoading).visibility = View.GONE
                    findViewById<TextView>(R.id.tvMessage).text = "No Faces Detected"
                    findViewById<TextView>(R.id.tvMessage).visibility = View.VISIBLE
                }
                else{
                    findViewById<LinearLayout>(R.id.llLoading).visibility = View.GONE
                    findViewById<TextView>(R.id.tvMessage).text = "Face Detected"
                    findViewById<TextView>(R.id.tvMessage).visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                findViewById<LinearLayout>(R.id.tvMessage).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tvMessage).text = e.message
                findViewById<LinearLayout>(R.id.llLoading).visibility = View.GONE
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data!=null){
            Log.i("info", "Photo Selected")
            selectedPhotoUri = data.data
            bitmap = MediaStore.Images.Media.getBitmap(
                contentResolver,
                selectedPhotoUri
            )
            findViewById<CircleImageView>(R.id.btn_select_image).setImageBitmap(bitmap)
        }
    }
}