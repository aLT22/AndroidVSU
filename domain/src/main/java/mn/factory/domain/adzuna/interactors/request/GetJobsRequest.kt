package mn.factory.domain.adzuna.interactors.request

import mn.factory.domain.utils.InteractorRequest

/**
 * Created by Turkin A. on 07/10/2018.
 */
class GetJobsRequest(
        var country: String = "ru",
        var page: Int = 1,
        var resultsPerPage: Int = 20
) : InteractorRequest