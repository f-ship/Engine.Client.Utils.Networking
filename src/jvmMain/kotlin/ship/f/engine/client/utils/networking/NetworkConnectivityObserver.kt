package ship.f.engine.client.utils.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class NetworkConnectivityObserver actual constructor(platformContext: Any?) : ConnectivityObserver {
    override fun observe(): Flow<ConnectivityObserver.Status> {
        // Simplified for desktop/JVM - always available for now
        return flowOf(ConnectivityObserver.Status.Available)
    }
}
