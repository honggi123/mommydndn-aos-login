package com.mommydndn.app.ui.signin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mommydndn.app.R
import com.mommydndn.app.domain.model.OAuthProvider
import com.mommydndn.app.ui.theme.Grey700
import com.mommydndn.app.ui.theme.White
import com.mommydndn.app.ui.theme.paragraph300

@Composable
internal fun SocialLogin(
    onKakakSignInClick: () -> Unit,
    onNaverSignInClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.social_login),
            color = Grey700,
            style = MaterialTheme.typography.paragraph300,
            fontWeight = FontWeight.Medium
        )

        SocialLoginButtonsRow(
            onKakakSignInClick = onKakakSignInClick,
            onNaverSignInClick = onNaverSignInClick,
            onGoogleSignInClick = onGoogleSignInClick,
            modifier = Modifier.wrapContentSize(),
        )
    }
}

@Composable
internal fun SocialLoginButtonsRow(
    onKakakSignInClick: () -> Unit,
    onNaverSignInClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SocialLoginIconButton(
            onClick = onNaverSignInClick,
            painter = painterResource(id = R.drawable.icon_naver)
        )

        SocialLoginIconButton(
            onClick = onKakakSignInClick,
            painter = painterResource(id = R.drawable.icon_kakao)
        )

        SocialLoginIconButton(
            onClick = onGoogleSignInClick,
            painter = painterResource(id = R.drawable.icon_google),
        )
    }
}

@Composable
internal fun SocialLoginIconButton(
    onClick: () -> Unit,
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painter,
            contentDescription = "SocialLoginIconButton",
            modifier = Modifier.size(72.dp),
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
private fun SocialLoginPreview() {
    SocialLogin(
        onKakakSignInClick = {},
        onNaverSignInClick = {},
        onGoogleSignInClick = {},
        modifier = Modifier
            .background(White)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp)
    )
}