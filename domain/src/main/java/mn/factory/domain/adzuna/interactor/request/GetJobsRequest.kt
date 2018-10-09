package mn.factory.domain.adzuna.interactor.request

import mn.factory.domain.utils.InteractorRequest

/**
 * Created by Turkin A. on 07/10/2018.
 */
class GetJobsRequest : InteractorRequest {

    var country: String = "gb"
    var page: Int = 1
    var resultsPerPage: Int = 20

}