package mn.factory.androidvsu.model.adzuna.mapper

import mn.factory.androidvsu.model.adzuna.VersionPresentation
import mn.factory.domain.adzuna.model.Version
import mn.factory.domain.utils.Mapper

/**
 * Created by Turkin A. on 06/10/2018.
 */
class VersionToVersionPresentationMapper : Mapper<Version, VersionPresentation> {
    override fun map(from: Version): VersionPresentation =
            VersionPresentation(
                    from.softwareVersion,
                    from.classType,
                    from.apiVersion
            )
}