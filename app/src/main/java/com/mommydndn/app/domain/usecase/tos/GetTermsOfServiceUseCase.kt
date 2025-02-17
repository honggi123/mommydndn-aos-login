package com.mommydndn.app.domain.usecase.tos

import com.mommydndn.app.di.IODispatcher
import com.mommydndn.app.domain.model.TermsOfService
import com.mommydndn.app.domain.repository.TermsOfServiceRepository
import com.mommydndn.app.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTermsOfServiceUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: TermsOfServiceRepository,
) : UseCase<Unit, List<TermsOfService>>(coroutineDispatcher) {

    override suspend fun execute(parameters: Unit): List<TermsOfService> {
        return repository.getTermsOfService()
    }
}