package io.graypart.library.utils

import android.content.Context
import android.os.AsyncTask
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import io.graypart.library.Constants.appsDevKey
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class Utils {

    companion object {

        var ADId: String? = ""

        fun getAdId(context: Context){
            AsyncTask.execute {
                try {
                    val adInfo: AdvertisingIdClient.Info =
                        AdvertisingIdClient.getAdvertisingIdInfo(context)
                    ADId = if (adInfo != null) adInfo.getId() else null
                    // Use the advertising id
                } catch (exception: IOException) {
                    // Error handling if needed
                } catch (exception: GooglePlayServicesRepairableException) {
                } catch (exception: GooglePlayServicesNotAvailableException) {
                }
            }
        }

        fun concatCampaign(naming: String): String {
            return naming.replace("||", "&").replace("()", "=")
        }
        //test1||sub1()test||sub2()test2

        fun getFinalUrl(baseUrl: String, naming: String, context: Context, campaignId: String, mediaSource: String): String {
            return baseUrl + "?" + "sub12=" + getAppBundle(context) +
                    "&afToken=" + appsDevKey +
                    "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context) +
                    "&sub11=" + campaignId +
                    "&media_source=" + mediaSource +
                    "&advertising_id=" + ADId +
                    "&triger=" + concatCampaign(naming)


            //baseUrl + "?" + concatCampaign(naming) + "&sub6=" + getAppBundle(context) + "&sub7=" + getAppsFlyerID(context) + "&sub8=" + preferences.getAdId(
            //  ADID)
        }


        fun getFinalDeepUrl(baseUrl: String, naming: String, context: Context, fbappid: String, fbappsecret: String): String {
            //sub12=com.monkess.clashs
            // &afToken=KUEhjXjcdPFxMXzpqQxBEG
            // &afid=1635947120530-2776428480908124344
            // &sub11=51899338471543040
            // &fbappid=573049710570616
            //  &fbappsecret=67724a28f1e677aaea45beba9842b172
            //  &media_source=bigoads_int
            // &advertising_id=da7e233f-96f3-48a7-9b93-04ea3a4e07e6
            // &triger=team
            // &sub1=valueSub1
            //   &sub2=valueSub2
            //  &sub3=valueSub3
            /*   Log.d("testing",   "  -1")
               var string = baseUrl
               string += "?" + "sub12=" + getAppBundle(context)
               string += "&afToken=" + appsDevKey
               Log.d("testing", string + "  -2")
              // string += "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
               Log.d("testing", string + "  -3")
               string += "&sub11=" + "facebook"
               string += "&fbappid=" + fbappid
               string += "&fbappsecret=" + fbappsecret
               string += "&media_source=" + "facebook"
               Log.d("testing", string + "  -4")
               string += "&advertising_id=" + ADId
               Log.d("testing", string + "  -5")
               string += "&triger=" + concatCampaign(naming)
               Log.d("testing", string + "  -6")

             */

            return baseUrl + "?" + "sub12=" + getAppBundle(context) +
                    "&afToken=" + appsDevKey +
                    "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context) +
                    "&sub11=" + "facebook" +
                    "&fbappid=" + fbappid +
                    "&fbappsecret=" + fbappsecret +
                    "&media_source=" + "facebook" +
                    "&advertising_id=" + ADId +
                    "&triger=" + concatCampaign(naming)



        }

        fun getAppBundle(context: Context): String {
            return context.packageName
        }


        fun getResponseCode(url: String): Int{
            var int = 0
            try {
                //  Log.d(TAG, "opening url")
                val openUrl = URL(url)
                val http: HttpURLConnection = openUrl.openConnection() as HttpURLConnection
                int = http.responseCode

            } catch (exception: IOException){

            }
            //  Log.d(TAG, "$int  - integer before return")
            return int
        }
    }

}