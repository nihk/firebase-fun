package nick.template.data

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun items(): Flow<List<Item>>
    suspend fun addItem(item: Item): Result
    suspend fun deleteItem(item: Item): Result

    sealed class Result {
        object Success : Result()
        data class Error(val throwable: Throwable) : Result()
    }
}

