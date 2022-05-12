package com.newcore.spotifyclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import com.newcore.spotifyclone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val image = "https://firebasestorage.googleapis.com/v0/b/spotifyclone-f4fba.appspot.com/o/Cover%20Adham%20Nabulsi.png?alt=media&token=62e2c7a4-8d87-447c-91d4-82f7ffb579d5"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        glide.load(image).into(binding.imageView)

        binding.imageView.setOnClickListener {
            startActivity(packageManager?.getLaunchIntentForPackage(packageName))
        }


    }
}