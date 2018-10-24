package mn.factory.androidvsu.utils.exceptions

/**
 * Created by Turkin A. on 24/10/2018.
 */
class InvalidLayoutManagerException(
        override val message: String
) : Exception(message) {
    companion object {
        private const val TAG = "InvalidLayoutManager"

        const val LAYOUT_MANAGER_IS_NULL_MESSAGE = "Layout manager is null!"
    }
}