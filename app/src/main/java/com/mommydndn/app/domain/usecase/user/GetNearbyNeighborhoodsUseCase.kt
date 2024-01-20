package com.mommydndn.app.domain.usecase.user

import com.mommydndn.app.di.IODispatcher
import com.mommydndn.app.domain.model.NearbyNeighborhoodDistance
import com.mommydndn.app.domain.model.Neighborhood
import com.mommydndn.app.domain.repository.UserRepository
import com.mommydndn.app.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNearbyNeighborhoodsUseCase @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) : FlowUseCase<GetNearbyNeighborhoodsParams, Map<NearbyNeighborhoodDistance, List<Neighborhood>>>(coroutineDispatcher) {

    override fun execute(parameters: GetNearbyNeighborhoodsParams): Flow<Map<NearbyNeighborhoodDistance, List<Neighborhood>>> {
        TODO()
    }
}

data class GetNearbyNeighborhoodsParams(
    val latitude: Double,
    val longitude: Double,
)