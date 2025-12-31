package ship.f.engine.client.utils.networking

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_status_unsatisfied
import platform.Network.nw_path_t
import platform.darwin.dispatch_get_main_queue

actual class NetworkConnectivityObserver actual constructor(platformContext: Any? = null) : ConnectivityObserver {
    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val monitor = nw_path_monitor_create()
            nw_path_monitor_set_update_handler(monitor) { path: nw_path_t? ->
                val status = nw_path_get_status(path)
                val result = when (status) {
                    nw_path_status_satisfied -> ConnectivityObserver.Status.Available
                    nw_path_status_unsatisfied -> ConnectivityObserver.Status.Unavailable
                    else -> ConnectivityObserver.Status.Unavailable
                }
                trySend(result)
            }
            nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
            nw_path_monitor_start(monitor)

            awaitClose {
                nw_path_monitor_cancel(monitor)
            }
        }.distinctUntilChanged()
    }
}
