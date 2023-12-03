package com.mommydndn.app.ui.features.signup

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.android.gms.location.FusedLocationProviderClient
import com.mommydndn.app.R
import com.mommydndn.app.data.model.common.LocationSearchType
import com.mommydndn.app.data.model.location.EmdItem
import com.mommydndn.app.data.model.location.LocationInfo
import com.mommydndn.app.data.model.location.displayName
import com.mommydndn.app.domain.model.TermsAndConditions.TermsAndConditionsItem
import com.mommydndn.app.ui.components.box.RadioListBox
import com.mommydndn.app.ui.components.box.SearchUnderHeader
import com.mommydndn.app.ui.components.inputfield.Searchbar
import com.mommydndn.app.ui.components.modal.TermsCheckListModal
import com.mommydndn.app.ui.theme.GreyOpacity400
import com.mommydndn.app.util.PermissionUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun LocationSearchRoute(
    navigateToNextScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
    fusedLocationClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        animationSpec = spring(
            dampingRatio = 0.85f,
            stiffness = 100f
        )
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Log.d("TownCheckScreen", "권한이 동의되었습니다.")
            searchNearLocations(
                fusedLocationClient,
                searchAction = { latitude, longitude ->
                    viewModel.setLocationInfo(
                        LocationInfo(
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
            )
        } else {
            Log.d("TownCheckScreen", "권한이 거부되었습니다.")
        }
    }

    LaunchedEffect(Unit) {
        requestAndSearchNearLocations(
            context = context,
            launcher = launcher,
            fusedLocationClient = fusedLocationClient,
            viewModel = viewModel
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenState = uiState as? SignUpUiState.LocationSearch

    val pagingItems = if (screenState?.locationSearchType == LocationSearchType.LOCATION) {
        viewModel.searchedNearestLocations.collectAsLazyPagingItems()
    } else {
        viewModel.searchedLocations.collectAsLazyPagingItems()
    }

    screenState?.isSignUpSuccess?.let { isSuccess ->
        if (isSuccess) {
            navigateToNextScreen()
        }
    }

    screenState?.let { state ->
        LocationSearchScreen(
            modifier = Modifier.fillMaxSize(),
            keyword = state.keyword,
            pagingItems = pagingItems,
            navigateToPreviousScreen = {
                navigateToPreviousScreen()
                viewModel.setSignUpStep(SignUpStep.USER_TYPE)
            },
            onSearchClick = {
                requestAndSearchNearLocations(
                    context,
                    launcher,
                    fusedLocationClient,
                    viewModel
                )
            },
            onItemClick = { item ->
                scope.launch { sheetState.show() }
                viewModel.setEmdId(item?.id)
            },
            onDialogDismiss = { scope.launch { sheetState.hide() } },
            onDialogItemSelected = { index, isChecked ->
                viewModel.setTermsCheckStatus(
                    id = state.termsAndConditions[index].id,
                    isChecked = isChecked
                )
            },
            itemList = state.termsAndConditions,
            onDialogComplete = { viewModel.signUp(state.signUpInfo) },
            scaffoldState = scaffoldState,
            sheetState = sheetState,
            onClearClick = { viewModel.clearKeyword() },
            onKeywordChange = { viewModel.setKeyword(it) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationSearchScreen(
    modifier: Modifier = Modifier,
    keyword: String,
    onSearchClick: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    onClearClick: () -> Unit,
    onKeywordChange: (String) -> Unit,
    onItemClick: (EmdItem) -> Unit,
    pagingItems: LazyPagingItems<EmdItem>,
    scaffoldState: ScaffoldState,
    sheetState: ModalBottomSheetState,
    itemList: List<TermsAndConditionsItem>,
    onDialogItemSelected: (Int, Boolean) -> Unit,
    onDialogDismiss: () -> Unit,
    onDialogComplete: () -> Unit,
) {

    Scaffold(
        topBar = {
            LocationSearchTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigateToPreviousScreen = { navigateToPreviousScreen() },
                onSearchClick = { onSearchClick() },
                keyword = keyword,
                onClearClick = { onClearClick() },
                onKeywordChange = { onKeywordChange(it) },
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            RadioListBox(
                modifier = Modifier.fillMaxWidth(),
                pagingItems = pagingItems,
                onItemClick = { item ->
                    if (item != null) {
                        onItemClick(item)
                    }
                },
                itemNameDisplay = EmdItem::displayName
            )
        }
    }

    BottomSheetModal(
        sheetState = sheetState,
        itemList = itemList,
        onDialogItemSelected = onDialogItemSelected,
        onDialogDismiss = onDialogDismiss,
        onDialogComplete = onDialogComplete,
    )
}

@Composable
fun LocationSearchTopAppBar(
    modifier: Modifier = Modifier,
    navigateToPreviousScreen: () -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onKeywordChange: (String) -> Unit,
    keyword: String,
) {
    Column(
        modifier = modifier
    ) {
        Searchbar(
            modifier = Modifier.fillMaxWidth(),
            keyword = keyword,
            onValueChange = { onKeywordChange(it) },
            clearAction = { onClearClick() },
            placeHolderText = stringResource(R.string.searched_neighborhood),
            backStackAction = { navigateToPreviousScreen() },
        )
        SearchUnderHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = if (keyword.isBlank()) {
                stringResource(R.string.search_location_keyword)
            } else {
                stringResource(R.string.result_searched_neighborhood, keyword)
            },
            searchAction = { onSearchClick() }
        )
    }
}

private fun requestAndSearchNearLocations(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: SignUpViewModel
) {
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    PermissionUtils.checkAndRequestPermissions(
        context,
        permissions,
        launcher,
        onPermissionGranted = {
            searchNearLocations(
                fusedLocationClient,
                searchAction = { latitude, longitude ->
                    viewModel.setLocationInfo(
                        LocationInfo(
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetModal(
    sheetState: ModalBottomSheetState,
    itemList: List<TermsAndConditionsItem>,
    onDialogItemSelected: (Int, Boolean) -> Unit,
    onDialogDismiss: () -> Unit,
    onDialogComplete: () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContentColor = Color.Transparent,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = GreyOpacity400,
        sheetElevation = 0.dp,
        sheetContent = {
            TermsCheckListModal(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                onDismiss = { onDialogDismiss() },
                onItemSelected = { index, isChecked -> onDialogItemSelected(index, isChecked) },
                onComplete = { onDialogComplete() },
                itemList = itemList,
                titleCheckBoxText = stringResource(R.string.total_terms_agreement)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        )
    }
}

private fun searchNearLocations(
    fusedLocationClient: FusedLocationProviderClient,
    searchAction: (Double, Double) -> Unit
) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let {
                searchAction(it.latitude, it.longitude)
            }
        }
    } catch (e: SecurityException) {
        Log.d("TownCheckScreen", e.stackTraceToString())
    }
}
