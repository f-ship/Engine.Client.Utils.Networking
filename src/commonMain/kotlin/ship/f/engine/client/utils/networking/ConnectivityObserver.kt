package ship.f.engine.client.utils.networking

import kotlinx.coroutines.flow.Flow

expect class NetworkConnectivityObserver(platformContext: Any? = null) : ConnectivityObserver

interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}
