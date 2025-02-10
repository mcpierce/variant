package org.comixedproject.variant.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.repositories.ServerRepository

private val TAG = "VariantViewModel"

/**
 * <code>VariantViewModel</code> provides a single source for application state on Android devices.
 *
 * @author Darryl L. Pierce
 */
class VariantViewModel(val serverRepository: ServerRepository) : BaseViewModel() {
    private val _serversFlow: MutableStateFlow<List<Server>> by lazy {
        MutableStateFlow(
            serverRepository.servers,
        )
    }
    val serverList = _serversFlow.asStateFlow()

    /**
     * Saves the given server to storage.
     *
     * @param server the server
     */
    fun saveServer(server: Server) {
        Logger.d(TAG, "Saving server: name=${server.name}")
        serverRepository.save(server)
        Logger.d(TAG, "There are now ${_serversFlow.asStateFlow().value} servers")
    }

    fun deleteServer(server: Server) {
        server.serverId.let { serverId ->
            if (serverId != null) {
                Logger.d(TAG, "Deleting server: name=${server.name}")
                serverRepository.deleteServer(serverId)
            }
        }
    }

    private val _currentServer = MutableStateFlow<Server?>(null)
    val currentServer: StateFlow<Server?> = _currentServer

    fun setCurrentServer(server: Server?) {
        this._currentServer.value = server
    }
}