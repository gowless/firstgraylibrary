package io.graypart.library.callbacks

interface AppsflyerListenerCallback {

    fun onConversionDataSuccess(data: MutableMap<String, Any>?, url: String)

    fun onConversionDataFail(error: String?)

}