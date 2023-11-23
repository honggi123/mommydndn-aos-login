package com.mommydndn.app.ui.features.care.jobseeker.write.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mommydndn.app.data.api.model.response.UserResponse
import com.mommydndn.app.data.model.care.JobSeekerPreview
import com.mommydndn.app.domain.repository.CaringRepository
import com.mommydndn.app.domain.repository.LocationRepository
import com.mommydndn.app.domain.repository.UserRepository
import com.mommydndn.app.ui.navigation.MainNav
import com.mommydndn.app.util.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobSeekerPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val caringRepository: CaringRepository,
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val authorInfo: StateFlow<UserResponse?> = userRepository.fetchUserInfo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    fun createJobSeeker(
        navController: NavHostController,
        jobSeekerPreview: JobSeekerPreview
    ) {
        viewModelScope.launch {
            jobSeekerPreview.apply {
                caringRepository.createJobSeeker(
                    introduce = introduce,
                    caringTypeList = caringTypeList,
                    emd = emd,
                    latitude = locationInfo?.latitude,
                    longitude = locationInfo?.longitude,
                    salaryType = salaryType,
                    salary = salary,
                    etcCheckedList = etcCheckedList,
                ).collectLatest {
                    NavigationUtils.navigate(
                        navController,
                        MainNav.Care.route,
                        isLaunchSingleTop = true
                    )
                }
            }

        }
    }

}