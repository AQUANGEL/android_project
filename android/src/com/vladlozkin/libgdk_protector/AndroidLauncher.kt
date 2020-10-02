package com.vladlozkin.libgdk_protector

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        val actionResolverAndroid = ActionResolverAndroid(this)
        initialize(Main(actionResolverAndroid), config)
    }
}