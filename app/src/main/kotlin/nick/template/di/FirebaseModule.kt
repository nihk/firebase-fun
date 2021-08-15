package nick.template.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import nick.template.initializers.FirebaseInitializer
import nick.template.initializers.Initializer

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {
    @Binds
    @IntoSet
    abstract fun firebaseInitializer(initializer: FirebaseInitializer): Initializer

    companion object {
        @Provides
        fun database() = Firebase.database
    }
}
