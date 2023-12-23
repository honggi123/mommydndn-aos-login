package com.mommydndn.app.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mommydndn.app.R
import com.mommydndn.app.ui.components.box.ProfileDataBox
import com.mommydndn.app.ui.theme.Grey300
import com.mommydndn.app.ui.theme.Grey500
import com.mommydndn.app.ui.theme.Grey600
import com.mommydndn.app.ui.theme.Grey700
import com.mommydndn.app.ui.theme.Salmon600
import com.mommydndn.app.ui.theme.caption100
import com.mommydndn.app.ui.theme.paragraph300
import com.mommydndn.app.ui.theme.paragraph400

@Composable
fun ProfileSummary(
    modifier: Modifier = Modifier,
    profileUri: String = "",
    nameText: String = "",
    locationText: String = "",
    dndnScore: Double = 0.0,
    isAuthenticated: Boolean = false,
    dateText: String,
    matchCount: Int = 0,
    reviewCount: Int = 0,
    responseRate: String = "",
    certificationList: List<String> = emptyList()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileBar(
            modifier = Modifier.fillMaxWidth(),
            profileUri = profileUri,
            nameText = nameText,
            locationText = locationText,
            dndnScore = dndnScore,
            isAuthenticated = isAuthenticated
        )
        SmallProfileInfoStack(
            modifier = Modifier.fillMaxWidth(),
            dateText = dateText,
            certificationList = certificationList
        )
        ProfileDataBox(
            modifier = Modifier.fillMaxWidth(),
            matchCount = matchCount,
            reviewCount = reviewCount,
            responseRate = responseRate
        )
    }
}

@Composable
fun ProfileBar(
    modifier: Modifier = Modifier,
    profileUri: String = "",
    nameText: String = "",
    locationText: String = "",
    dndnScore: Double = 0.0,
    isAuthenticated: Boolean = false
) {
    val profilePainter = rememberImagePainter(
        data = profileUri,
        builder = {
            crossfade(true)
        }
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .requiredWidth(width = 342.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = profilePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Grey300)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nameText,
                        color = Grey700,
                        style = MaterialTheme.typography.paragraph300.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    if (isAuthenticated) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_certificate),
                            contentDescription = "Icon/certificate",
                            modifier = Modifier
                                .size(size = 16.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = locationText,
                        color = Grey500,
                        style = MaterialTheme.typography.caption100.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = dndnScore.toString(),
                color = Salmon600,
                style = MaterialTheme.typography.paragraph400.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_muscle),
                    contentDescription = "Icon/muscle",
                    modifier = Modifier
                        .requiredSize(size = 16.dp)
                )
                Text(
                    text = "든든력",
                    color = Grey600,
                    style = MaterialTheme.typography.caption100.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }
}



@Preview
@Composable
fun previewProfileDataBox() {
    ProfileBar()
}