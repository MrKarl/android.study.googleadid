package com.eungpang.googleadid

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            retrieveGoogleAdId()
        }
    }

    private fun retrieveGoogleAdId() {
        val taskForQueringGoogleAdId = QueryGoogleAdId(this)
        taskForQueringGoogleAdId.execute()
    }

    companion object {
        private class QueryGoogleAdId(activity: Activity): AsyncTask<Void, Void, String>() {
            private val weakActivity: WeakReference<Activity> = WeakReference(activity)

            override fun doInBackground(vararg params: Void): String {
                lateinit var adId: String
                val activity = weakActivity.get()!!
                try {
                    val info = AdvertisingIdClient.getAdvertisingIdInfo(activity.applicationContext)
                    adId = info.id


                } catch (e: Exception) {
                    return "--"
                }

                return adId
            }

            override fun onPostExecute(result: kotlin.String?) {
                Log.e(TAG, result)
                weakActivity.get()?.tv?.text = result
            }
        }
    }
}