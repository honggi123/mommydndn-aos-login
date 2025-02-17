package com.mommydndn.app.ui.care.post.agency

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mommydndn.app.R
import com.mommydndn.app.domain.model.CareAgencyOtherCondition
import com.mommydndn.app.domain.model.CareType
import com.mommydndn.app.ui.care.post.components.BioPostSection
import com.mommydndn.app.ui.care.post.components.CareTypesPostSection
import com.mommydndn.app.ui.care.post.components.GetPhotoPostSection
import com.mommydndn.app.ui.care.post.components.GetPhotosPostSection
import com.mommydndn.app.ui.care.post.components.NeighborhoodPostSection
import com.mommydndn.app.ui.care.post.components.NeighborhoodUiModel
import com.mommydndn.app.ui.care.post.components.OtherConditionsPostSection
import com.mommydndn.app.ui.care.post.components.PostTopAppBar
import com.mommydndn.app.ui.theme.Grey300
import com.mommydndn.app.ui.theme.Grey50

data class PostCareAgencyProfileUiModel(
    val coverPhotos: List<Uri>,
    val profilePhoto: Uri,
    val bio: String,
    val neighborhood: NeighborhoodUiModel,
    val careTypes: List<CareType>,
    val otherConditions: List<CareAgencyOtherCondition>,
)

@Composable
fun RegisterCareAgencyScreen(
    onCloseClick: () -> Unit,
    coverPhotoUris: List<Uri>,
    onCoverPhotosAdded: (List<Uri>) -> Unit,
    onRemoveCoverPhotoClick: (Uri) -> Unit,
    profilePhotoUri: Uri?,
    onProfilePhotoChange: (Uri?) -> Unit,
    bio: String,
    onBioChange: (String) -> Unit,
    neighborhood: NeighborhoodUiModel,
    onNeighborhoodClick: () -> Unit,
    onNearbyNeighborhoodsClick: () -> Unit,
    careTypes: List<CareType>,
    onCareTypeClick: (CareType) -> Unit,
    selectedConditions: List<CareAgencyOtherCondition>,
    onOtherConditionClick: (CareAgencyOtherCondition) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PostTopAppBar(
            leading = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = "MommyDnDnTopAppBar_Close",
                    modifier = Modifier.size(size = 36.dp),
                    tint = Grey300,
                )
            },
            onLeadingClick = onCloseClick,
            title = stringResource(R.string.register_care_agency_profile),
            modifier = Modifier,
        )

        Column(
            modifier = Modifier
                .background(Grey50)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            GetPhotosPostSection(
                title = stringResource(R.string.cover_photo),
                subtitle = stringResource(id = R.string.required),
                uris = coverPhotoUris,
                onPhotosChange = onCoverPhotosAdded,
                onRemoveClick = onRemoveCoverPhotoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                maxSize = 5,
            )

            GetPhotoPostSection(
                uri = profilePhotoUri,
                onPhotoChange = onProfilePhotoChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )

            BioPostSection(
                bio = bio,
                onBioChange = onBioChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )

            NeighborhoodPostSection(
                neighborhood = neighborhood,
                onNeighborhoodClick = onNeighborhoodClick,
                onNearbyNeighborhoodsClick = onNearbyNeighborhoodsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )

            CareTypesPostSection(
                selectedCareTypes = careTypes,
                onCareTypeClick = onCareTypeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )

            OtherConditionsPostSection(
                selectedConditions = selectedConditions,
                onClick = onOtherConditionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
    }
}

@Preview
@Composable
private fun PostCareAgencyProfilePreview() {
    RegisterCareAgencyScreen(
        onCloseClick = {},
        coverPhotoUris = emptyList(),
        onCoverPhotosAdded = {},
        onRemoveCoverPhotoClick = {},
        profilePhotoUri = null,
        onProfilePhotoChange = {},
        bio = "",
        onBioChange = {},
        neighborhood = NeighborhoodUiModel(
            name = "서초동",
            address = "서울 서초구 서초중앙로 15",
            nearbyNeighborhoodsCount = 24,
        ),
        onNeighborhoodClick = {},
        onNearbyNeighborhoodsClick = {},
        careTypes = emptyList(),
        onCareTypeClick = {},
        selectedConditions = emptyList(),
        onOtherConditionClick = {},
        modifier = Modifier,
    )
}