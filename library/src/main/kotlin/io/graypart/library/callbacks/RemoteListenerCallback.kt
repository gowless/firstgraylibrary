package io.graypart.library.callbacks

interface RemoteListenerCallback {

    fun onFalseCode(int: Int)

    fun onSuccessCode(offerUrl: String)

    fun onStatusTrue()

    fun onStatusFalse()

    fun nonFirstLaunch(url: String)

    fun onDeepLinkSuccess(fbappid: String, fbappsecret: String, offerUrl: String, naming: String)
}