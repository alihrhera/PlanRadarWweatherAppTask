package hrhera.ali.network

object KeysProvider {

    init {
        System.loadLibrary("keys-lib")
    }

    external fun getApiKey(): String
}