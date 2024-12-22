package com.nssus.ihandy.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface Dispatcher {

    fun providesDefaultDispatcher(): CoroutineDispatcher

    fun providesIODispatcher(): CoroutineDispatcher

    fun providesMainDispatcher(): CoroutineDispatcher

}

class DispatcherImpl : Dispatcher {

    override fun providesDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun providesIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

}