package com.jj.shore.helpers.connectivity

import kotlinx.coroutines.flow.Flow

/**
 * REFERENCES:
 * * https://github.com/philipplackner/ObserveConnectivity/blob/master/app/src/main/java/com/plcoding/observeconnectivity/ConnectivityObserver.kt
 */

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost
    }

}