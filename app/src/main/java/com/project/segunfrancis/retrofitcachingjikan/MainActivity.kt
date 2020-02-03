package com.project.segunfrancis.retrofitcachingjikan

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.segunfrancis.retrofitcachingjikan.adapter.AnimeAdapter
import com.project.segunfrancis.retrofitcachingjikan.api.Service
import com.project.segunfrancis.retrofitcachingjikan.data.BaseResponse
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache: Cache? = Cache(applicationContext.cacheDir, cacheSize)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                /*Get the request from the chain */
                var request = chain.request()
                /*
                       *  Leveraging the advantage of using Kotlin,
                       *  we initialize the request and change its header depending on whether
                       *  the device is connected to Internet or not.
                       */
                request = if (hasNetwork(applicationContext)!!)
                /*
                       *  If there is Internet, get the cache that was stored 5 seconds ago.
                       *  If the cache is older than 5 seconds, then discard it,
                       *  and indicate an error in fetching the response.
                       *  The 'max-age' attribute is responsible for this behavior.
                       */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(Service::class.java)
        val call: Call<BaseResponse> = service.getEpisodes()
        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val episodes = response.body()?.episodes
                val adapter = AnimeAdapter()
                adapter.setData(episodes)
                recycler_view.adapter = adapter
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, "Check Network Connection", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
