package nick.template.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nick.template.data.Item
import nick.template.data.ItemRepository

class MainViewModel(
    private val repository: ItemRepository
) : ViewModel() {
    val items = repository.items()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2_000L),
            initialValue = emptyList()
        )

    fun addItem() {
        viewModelScope.launch {
            val item = Item(
                id = UUID.randomUUID().toString(),
                message = UUID.randomUUID().toString()
            )
            repository.addItem(item)
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    class Factory @Inject constructor(
        private val repository: ItemRepository
    ) {
        fun create(owner: SavedStateRegistryOwner): AbstractSavedStateViewModelFactory {
            return object : AbstractSavedStateViewModelFactory(owner, null) {
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(repository) as T
                }
            }
        }
    }
}
