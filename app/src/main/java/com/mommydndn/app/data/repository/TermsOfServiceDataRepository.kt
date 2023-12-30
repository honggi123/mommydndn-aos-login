package com.mommydndn.app.data.repository

import com.mommydndn.app.data.api.model.response.toDomain
import com.mommydndn.app.data.network.model.request.UpdateTermsOfServiceRequest
import com.mommydndn.app.data.network.service.TermsOfServiceService
import com.mommydndn.app.domain.model.tos.TermsOfService
import com.mommydndn.app.domain.model.tos.TosAgreementStatus
import com.mommydndn.app.domain.repository.TermsOfServiceRepository

class TermsOfServiceDataRepository constructor(
    private val service: TermsOfServiceService,
) : TermsOfServiceRepository {

    override suspend fun fetchTermsOfService(): List<TermsOfService> {
        return service.fetchTermsOfService().map { it.toDomain() }
    }

    override suspend fun updateTosApprovedStatus(tosItems: List<TosAgreementStatus>) {
        val approvalRequestList = tosItems.map {
            UpdateTermsOfServiceRequest(
                termsId = it.id,
                isApproved = it.isApproved
            )
        }
        service.updateTosApproval(approvalRequestList)
    }
}
