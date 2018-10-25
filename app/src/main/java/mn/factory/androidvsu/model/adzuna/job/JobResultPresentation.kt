package mn.factory.androidvsu.model.adzuna.job

import mn.factory.androidvsu.model.ItemPresentation

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class JobResultPresentation(
        val count: Int?,
        val results: List<JobPresentation>?
) : ItemPresentation