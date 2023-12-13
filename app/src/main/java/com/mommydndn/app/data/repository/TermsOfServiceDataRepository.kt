package com.mommydndn.app.data.repository

import com.mommydndn.app.data.api.model.request.UpdateTermsOfServiceRequest
import com.mommydndn.app.data.api.model.response.toDomain
import com.mommydndn.app.data.api.service.TermsOfServiceService
import com.mommydndn.app.domain.model.tos.TermsOfService
import com.mommydndn.app.domain.repository.TermsOfServiceRepository
import javax.inject.Inject

class TermsOfServiceDataRepository @Inject constructor(
    private val service: TermsOfServiceService,
) : TermsOfServiceRepository {

    override suspend fun fetchTermsOfService(): List<TermsOfService> {
        return service.fetchTermsOfService().map { it.toDomain() }
    }

    override suspend fun updateTermsCheckedStatus(termsItems: List<TermsOfService>) {
        val approvalRequestList = termsItems.map {
            UpdateTermsOfServiceRequest(
                termsId = it.id,
                isApproved = it.isApproved
            )
        }
        service.updateTermsApproval(approvalRequestList)
    }
}
