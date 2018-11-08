package mn.factory.data.api.adzuna.repositories

import io.reactivex.Single
import mn.factory.data.api.adzuna.AdzunaService
import mn.factory.data.api.adzuna.mapper.VersionNetworkToVersionMapper
import mn.factory.domain.adzuna.repositories.VersionRepository
import mn.factory.domain.adzuna.model.Version

/**
 * Created by Turkin A. on 08/11/2018.
 */
class VersionRepositoryImpl(
        private val adzunaService: AdzunaService,
        private val mapperVersion: VersionNetworkToVersionMapper
) : VersionRepository {

    override fun getVersion(): Single<Version> =
            adzunaService
                    .getVersion()
                    .map { mapperVersion.map(it) }

}