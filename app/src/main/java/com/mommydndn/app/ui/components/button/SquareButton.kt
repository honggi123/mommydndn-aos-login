package com.mommydndn.app.ui.components.button

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mommydndn.app.R
import com.mommydndn.app.domain.model.user.UserType
import com.mommydndn.app.ui.theme.Grey100
import com.mommydndn.app.ui.theme.Grey50
import com.mommydndn.app.ui.theme.Grey600
import com.mommydndn.app.ui.theme.MommydndnaosTheme
import com.mommydndn.app.ui.theme.Shapes
import com.mommydndn.app.ui.theme.White
import com.mommydndn.app.ui.theme.paragraph500
import com.mommydndn.app.ui.theme.shadow500

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    status: Boolean = false,
    imageResourceId: Int,
    text: String = "",
    onClick: (Boolean) -> Unit,
) {
    Crossfade(modifier = modifier, targetState = status, label = "") { isSelected ->
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .then(if (isSelected) shadow500 else modifier)
                .background(
                    color = if (isSelected) Grey100 else Grey50,
                    shape = Shapes.large
                )
                .clickable(onClick = { onClick(!status) })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = imageResourceId),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .padding(0.9.dp)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.paragraph500.copy(
                        fontWeight = FontWeight.Bold,
                        color = Grey600
                    )
                )
            }
        }
    }
}


@Preview
@Composable
fun previewSquareButton() {
    MommydndnaosTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(368.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SquareButton(
                    modifier = Modifier.weight(1f),
                    imageResourceId = R.drawable.icon_building,
                    text = stringResource(R.string.company_user),
                    onClick = { }
                )
                SquareButton(
                    modifier = Modifier.weight(1f),
                    imageResourceId = R.drawable.icon_person,
                    text = stringResource(R.string.individual_user),
                    onClick = { }
                )
            }
        }
    }
}