package com.mommydndn.app.domain.usecase.neighborhood

import com.mommydndn.app.di.IODispatcher
import com.mommydndn.app.domain.model.NeighborhoodVicinityLevel
import com.mommydndn.app.domain.repository.UserRepository
import com.mommydndn.app.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNearbyNeighborhoodDistanceUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: UserRepository,
) : FlowUseCase<Unit, NeighborhoodVicinityLevel>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<NeighborhoodVicinityLevel> {
        TODO()
    }
}