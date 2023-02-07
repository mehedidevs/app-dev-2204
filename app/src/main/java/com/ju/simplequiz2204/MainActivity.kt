package com.ju.simplequiz2204


import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.ju.simplequiz2204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.settings.javaScriptEnabled = true

        binding.webView.loadUrl("https://www.daraz.com.bd/")

        binding.webView.webViewClient = object : WebViewClient() {


            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loading.visibility = View.VISIBLE
                binding.webView.visibility = View.INVISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loading.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressHorizontal.progress = newProgress
            }


        }


    }


    @Suppress("DEPRECATION")
    override fun onBackPressed() {


        if (binding.webView.canGoBack()) {
            binding.webView.goBack()

        } else {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setIcon(R.drawable.round_warning_24)
            builder.setTitle("Are you sure?")
            builder.setMessage("do you want to exit?")
            builder.setPositiveButton("yes") { _, _ ->
                finish()
            }
            builder.setNegativeButton("no") { _, _ -> }

            val alertDialog = builder.create()
            alertDialog.show()

        }


    }


}