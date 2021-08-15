package nick.template.initializers

import javax.inject.Inject

class Initializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>
) : Initializer {
    override fun initialize() {
        initializers.forEach(Initializer::initialize)
    }
}
