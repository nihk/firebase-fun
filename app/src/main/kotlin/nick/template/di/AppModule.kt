package nick.template.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nick.template.data.FirebaseItemRepository
import nick.template.data.ItemRepository
import nick.template.initializers.Initializer
import nick.template.initializers.Initializers

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun itemRepository(itemRepository: FirebaseItemRepository): ItemRepository

    @Binds
    abstract fun initializers(initializers: Initializers): Initializer

    companion object {

    }
}
