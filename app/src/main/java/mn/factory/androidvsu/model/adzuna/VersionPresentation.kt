package mn.factory.androidvsu.model.adzuna

import mn.factory.androidvsu.model.ItemPresentation

/**
 * Created by Turkin A. on 06/10/2018.
 */
data class VersionPresentation(
        val softwareVersion: String? = "softwareVersion",
        val classType: String? = "classType",
        val apiVersion: Int? = -1
) : ItemPresentation