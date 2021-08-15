package nick.template.data

import android.util.Log
import androidx.annotation.Keep
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// $ firebase emulators:start --project=firebase-fun
class FirebaseItemRepository @Inject constructor(
    database: FirebaseDatabase
) : ItemRepository {
    private val messagesRef = database.reference.child("messages")

    override fun items(): Flow<List<Item>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val firebaseItems: Map<String, FirebaseItem>? = snapshot.getValue<Map<String, FirebaseItem>>()

                // Will be null when there are 0 elements present.
                val items = firebaseItems?.map { map ->
                    Item(
                        id = map.key,
                        message = requireNotNull(map.value.message)
                    )
                }

                trySend(items ?: emptyList())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("asdf", "Error", error.toException())
                val cause = CancellationException("Firebase DB Flow cancelled", error.toException())
                cancel(cause)
            }
        }

        messagesRef.addValueEventListener(listener)

        awaitClose {
            Log.d("asdf", "closing firebase listening")
            messagesRef.removeEventListener(listener)
        }
    }

    override suspend fun addItem(item: Item): ItemRepository.Result {
        return try {
            messagesRef
                .child(item.id)
                .setValue(FirebaseItem(item.message))
                .await()
            ItemRepository.Result.Success
        } catch (throwable: Throwable) {
            ItemRepository.Result.Error(throwable)
        }
    }

    override suspend fun deleteItem(item: Item): ItemRepository.Result {
        return try {
            messagesRef
                .child(item.id)
                .removeValue()
                .await()
            ItemRepository.Result.Success
        } catch (throwable: Throwable) {
            ItemRepository.Result.Error(throwable)
        }
    }

    @Keep
    @IgnoreExtraProperties
    private data class FirebaseItem(val message: String? = null)
}
