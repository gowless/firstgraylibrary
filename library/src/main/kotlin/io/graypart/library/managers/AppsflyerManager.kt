package io.graypart.library.managers

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import io.graypart.library.AppsProjector

import io.graypart.library.AppsProjector.preferences
import io.graypart.library.Constants
import io.graypart.library.Constants.LOG
import io.graypart.library.Constants.ONCONVERSION
import io.graypart.library.Constants.TAG
import io.graypart.library.Constants.TRUE
import io.graypart.library.callbacks.AppsflyerListenerCallback
import io.graypart.library.callbacks.RemoteListenerCallback
import io.graypart.library.storage.persistroom.model.Link
import io.graypart.library.utils.Utils

class AppsflyerManager(private val context: Context, private val appsDevKey: String):
    RemoteListenerCallback {

   private lateinit var appsflyerListenerCallback: AppsflyerListenerCallback

    fun start(offerUrl: String) {

        appsflyerListenerCallback = context as AppsflyerListenerCallback

      //  if (LOG) Log.d(TAG, "got apps Data - method invoked")
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                data?.let { cvData ->
                    cvData.map {
                        if (LOG) Log.d(TAG, "got apps Data - succes conversion")
                        when (preferences.getOnConversionDataSuccess(ONCONVERSION)) {
                            "null" -> {
                                preferences.setOnConversionDataSuccess(
                                    ONCONVERSION,
                                    TRUE
                                )
                                if (LOG) Log.d(TAG, "got apps Data - $data")
                                if (data["campaign"].toString().contains("sub")) {

                                    /*
                                    if (LOG) Log.d(TAG, "added link to storage - " + MainClass.utils.getFinalUrl(
                                            part1 + part2 + part3, data["campaign"].toString(), context))
                                     */

                                    val url = Utils.getFinalUrl(
                                        offerUrl,
                                        data["campaign"].toString(),
                                        context, data["af_c_id"].toString(),
                                        data["media_source"].toString(),
                                        data["advertising_id"].toString()
                                    )


                                    if (LOG) Log.d(TAG, "$url -- final url")
                                    AppsProjector.createRepoInstance(context).insert(Link(1, url))
                                 //   if (LOG) Log.d(TAG, "added to viewmodel number 1")
                                    appsflyerListenerCallback.onConversionDataSuccess(data, url)

                                } else {
                                    preferences.setOnConversionDataSuccess(
                                        ONCONVERSION,
                                        Constants.TRUE
                                    )
                                    val url = offerUrl + "?app_id=" + Utils.getAppBundle(context) +
                                            "&af_status=" + "Organic" +
                                            "&afToken=" + appsDevKey +
                                            "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                                  //  if (LOG) Log.d(TAG, "url - $url")
                                    AppsProjector.createRepoInstance(context).insert(Link(1, url))
                                  //  if (LOG) Log.d(TAG, "added to viewmodel number 2")
                                    appsflyerListenerCallback.onConversionDataSuccess(data, url)
                                }
                            }
                            "true" -> {

                            }
                            "false" -> {

                            }
                            else -> {

                            }
                        }


                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                if (LOG) Log.d(TAG, "onConversionDataFail")
                appsflyerListenerCallback.onConversionDataFail(error)
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    if (LOG) Log.d(TAG, "onAppOpenAttribution")
                }
            }

            override fun onAttributionFailure(error: String?) {
                if (LOG) Log.d(TAG, "onAttributionFailure")
            }
        }
        //инициализируем SDK AppsFlyer'a
        AppsFlyerLib.getInstance().init(appsDevKey, conversionDataListener, context)
        AppsFlyerLib.getInstance().start(context)
    }

    override fun onFalseCode(int: Int) {

    }

    override fun onSuccessCode(offerUrl: String) {
        Log.d(TAG, "onSuccessCode AppsFlyer Class")
        start(offerUrl)
    }

    override fun onStatusTrue() {

    }

    override fun onStatusFalse() {

    }

    override fun nonFirstLaunch(url: String) {

    }

}