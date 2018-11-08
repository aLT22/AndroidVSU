package mn.factory.domain.adzuna.repositories

import io.reactivex.Single
import mn.factory.domain.adzuna.model.Version

/**
 * Created by Turkin A. on 08/11/2018.
 */
interface VersionRepository : Repository {

    fun getVersion(): Single<Version>

}