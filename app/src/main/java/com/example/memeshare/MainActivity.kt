package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadMeme()
        Toast.makeText(this, "Suman here!", Toast.LENGTH_SHORT).show()
    }

    private fun loadMeme() {
        val memeImageView = findViewById<ImageView>(R.id.memeimageView)
        val pgrBar = findViewById<ProgressBar>(R.id.pgrBar)
        pgrBar.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgrBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgrBar.visibility = View.GONE
                        return false
                    }

                }).into(memeImageView)

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })

// Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://meme-api.herokuapp.com/gimme")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))


    }

    fun nextMeme(view: View) {
        loadMeme()

    }
}