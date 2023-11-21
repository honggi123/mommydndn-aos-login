package com.mommydndn.app.ui.features.care.company.write

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mommydndn.app.data.model.care.CaringTypeItem
import com.mommydndn.app.data.model.care.EtcCheckItem
import com.mommydndn.app.data.model.care.MinHourlySalary
import com.mommydndn.app.data.model.care.SalaryType
import com.mommydndn.app.data.model.care.SalaryTypeItem
import com.mommydndn.app.data.model.map.EmdItem
import com.mommydndn.app.data.model.map.LocationInfo
import com.mommydndn.app.data.respository.CaringRepository
import com.mommydndn.app.data.respository.LocationRepository
import com.mommydndn.app.data.respository.UserRepository
import com.mommydndn.app.utils.NumberUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyWriteViewModel @Inject constructor(
    private val caringRepository: CaringRepository,
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private var _careTypes: MutableStateFlow<List<CaringTypeItem>> = MutableStateFlow(emptyList())
    val careTypes: StateFlow<List<CaringTypeItem>> = _careTypes

    private var _salaryTypes: MutableStateFlow<List<SalaryTypeItem>> = MutableStateFlow(
        listOf(
            SalaryTypeItem(SalaryType.HOURLY, true),
            SalaryTypeItem(SalaryType.DAILY),
            SalaryTypeItem(SalaryType.WEEKLY),
            SalaryTypeItem(SalaryType.MONTHLY),
            SalaryTypeItem(SalaryType.NEGOTIATION)
        )
    )
    val salaryTypes: StateFlow<List<SalaryTypeItem>> = _salaryTypes

    private val _introduce: MutableStateFlow<String> = MutableStateFlow("")
    val introduce: StateFlow<String> = _introduce

    private val _salary: MutableStateFlow<Int?> = MutableStateFlow(null)
    val salary: StateFlow<Int?> = _salary

    private val _photo: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val photo: StateFlow<Uri?> = _photo

    private val _photos: MutableStateFlow<List<Uri>> = MutableStateFlow(listOf())
    val photos: StateFlow<List<Uri>> = _photos

    private val _etcCheckList: MutableStateFlow<List<EtcCheckItem>> = MutableStateFlow(emptyList())
    val etcCheckList: StateFlow<List<EtcCheckItem>> = _etcCheckList

    private val _emdItem: MutableStateFlow<EmdItem?> = MutableStateFlow<EmdItem?>(null)
    val emdItem: StateFlow<EmdItem?> = _emdItem

    private val _commission: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    val commission: StateFlow<Int?> = _commission

    private val _locationInfo: MutableStateFlow<LocationInfo?> =
        MutableStateFlow<LocationInfo?>(null)
    val locationInfo: StateFlow<LocationInfo?> = _locationInfo

    val minHourlySalary: StateFlow<MinHourlySalary?> =
        caringRepository.fetchMinHourlySalary().stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    init {
        fetchEtcCheckList()
        fetchCaringTypeItems()
        fetchUserInfo()
    }

    fun selectSalaryType(selectedSalaryTypeItem: SalaryTypeItem) {
        _salaryTypes.value = _salaryTypes.value.map { item ->
            if (item == selectedSalaryTypeItem) item.copy(isSelected = true)
            else item.copy(isSelected = false)
        }
    }

    fun selectCareTypes(selectedCareType: CaringTypeItem) {
        _careTypes.value = _careTypes.value.map { item ->
            if (item == selectedCareType) item.copy(isSelected = !item.isSelected)
            else item
        }
    }

    fun setIntroduce(curIntroduce: String) {
        _introduce.value = curIntroduce
    }

    fun setSalary(curSalary: String) {
        _salary.value = NumberUtils.getPrice(curSalary)
    }

    fun setCommission(curCommission: Int) {
        _commission.value = curCommission
    }

    fun addSelectedPhoto(selectedPhoto: Uri) {
        _photo.value = selectedPhoto
    }

    fun addSelectedPhotos(selectedPhotos: List<Uri>) {
        _photos.value = selectedPhotos
    }

    fun removePhoto(selectedUri: Uri) {
        _photos.value = _photos.value.filter {
            it != selectedUri
        }
    }

    fun checkEtcListItem(etcCheckItem: EtcCheckItem) {
        _etcCheckList.value = _etcCheckList.value.map { item ->
            if (item == etcCheckItem) item.copy(isChecked = !item.isChecked)
            else item
        }
    }

    fun fetchCaringTypeItems() {
        viewModelScope.launch {
            caringRepository.fetchCaringTypeItems().collect { types ->
                _careTypes.value = types
            }
        }
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            userRepository.fetchUserInfo().collect { userInfo ->
                _emdItem.value = userInfo.emd
                _photo.value = userInfo.profileUrl?.toUri()
            }
        }
    }

    fun fetchEtcCheckList() {
        viewModelScope.launch {
            caringRepository.fetchCompanyEtcCheckList().collect {
                _etcCheckList.value = it
            }
        }
    }
}