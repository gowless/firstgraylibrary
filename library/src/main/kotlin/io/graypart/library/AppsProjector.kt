package io.graypart.library

import android.app.Activity
import android.content.Context
import io.graypart.library.Constants.appsDevKey
import io.graypart.library.managers.AppsflyerManager
import io.graypart.library.managers.FirebaseRemoteListener
import io.graypart.library.managers.OneSignalManager
import io.graypart.library.storage.Repository
import io.graypart.library.storage.persistroom.LinkDatabase
import io.graypart.library.storage.prefs.StorageUtils

object AppsProjector {


    lateinit var preferences: StorageUtils.Preferences
    var repository: Repository? = null



    fun createRemoteConfigInstance(activity: Activity): FirebaseRemoteListener {
        preferences = StorageUtils.Preferences(activity, Constants.NAME,
            Constants.MAINKEY,
            Constants.CHYPRBOOL
        )
        return FirebaseRemoteListener(activity)
    }

    fun createAppsInstance(context: Context, devKey: String): AppsflyerManager {
        appsDevKey = devKey
       return AppsflyerManager(context, devKey)
    }

    fun createOneSignalInstance(context: Context, oneSignalId: String): OneSignalManager {
      /*  val userDao = LinkDatabase.getDatabase(context).linkDao()
        repository = Repository(userDao)*/
        return OneSignalManager(context, oneSignalId)
    }

    fun createRepoInstance(context: Context): Repository {
        if (repository == null){
            return Repository(LinkDatabase.getDatabase(context).linkDao())
        } else {
            return repository as Repository
        }
    }

    //class AppsProjector
}