package mn.factory.data.api.adzuna.mapper

import mn.factory.data.api.adzuna.model.VersionNetwork
import mn.factory.domain.adzuna.model.Version
import mn.factory.domain.utils.Mapper

/**
 * Created by Turkin A. on 05/10/2018.
 */
class VersionNetworkToVersionMapper : Mapper<VersionNetwork, Version> {

    override fun map(from: VersionNetwork): Version {
        return Version(
                from.softwareVersion,
                from.classType,
                from.apiVersion
        )
    }

}