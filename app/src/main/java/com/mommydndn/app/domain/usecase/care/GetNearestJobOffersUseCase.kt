package com.mommydndn.app.domain.usecase.care

import com.mommydndn.app.domain.model.care.JobOffer
import com.mommydndn.app.domain.repository.CareRepository
import com.mommydndn.app.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNearestJobOffersUseCase @Inject constructor(
    private val repository: CareRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, List<JobOffer>>(coroutineDispatcher) {

    override suspend fun execute(parameters: Unit): List<JobOffer> {
        return repository.fetchNearestJobOffers()
    }

}