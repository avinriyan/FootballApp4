package com.larapin.kotlinsub4.util

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Avin on 09/10/2018.
 */
open class CoroutineContextProvider{
    open val main: CoroutineContext by lazy { UI }
}