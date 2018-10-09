package mn.factory.domain.utils

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface Mapper<in FROM, out TO> {

    fun map(from: FROM): TO

}