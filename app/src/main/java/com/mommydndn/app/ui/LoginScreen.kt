package com.mommydndn.app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mommydndn.app.ui.component.Header
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.mommydndn.app.ui.component.SocialLoginBox
import com.mommydndn.app.ui.theme.Grey500
import com.mommydndn.app.ui.theme.heading800
import com.mommydndn.app.R
import com.mommydndn.app.ui.theme.Paddings
import com.mommydndn.app.ui.theme.paragraph300
import com.mommydndn.app.ui.viewmodel.AccountViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kakao.sdk.common.util.Utility
import com.mommydndn.app.data.model.SignInType

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: AccountViewModel = viewModel()) {
    val context = LocalContext.current
    var keyHash = Utility.getKeyHash(context)
    Log.e("hashkeyyy",keyHash)
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("Kakao", "카카오 계정 로그인 실패", error)
            }

            token != null -> {
                loginWithKakaoNickName(token, viewModel)
            }
        }
    }

    Scaffold(
        topBar = {
            Header(
                rightContent = {
                    TextButton(
                        onClick = {},
                        contentPadding = PaddingValues(
                            top = 6.dp,
                            bottom = 6.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = "먼저 둘러보기",
                            style = MaterialTheme.typography.paragraph300.copy(
                                color = Grey500,
                                fontWeight = FontWeight.Medium,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .width(125.dp)
                            .height(121.dp),
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(Paddings.extra))
                    Text(
                        text = "우리 동네 엄마 찾기",
                        style = MaterialTheme.typography.heading800.copy(
                            color = Grey500,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = Modifier.height(Paddings.xlarge))
                    Text(
                        text = "내 아이를 맡길 수 있는\n따뜻한 동네 엄마를 찾아보세요",
                        style = MaterialTheme.typography.paragraph300.copy(
                            color = Grey500,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            SocialLoginBox(
                onClickGoogle = { loginGoogle() },
                onClickKakao = { loginKakao(context, kakaoCallback) },
                onClickNaver = { loginNaver() }
            )
        }
    }
}

private fun loginWithKakaoNickName(token: OAuthToken, viewModel: AccountViewModel) {
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> {
                Log.e("Kakao", "사용자 정보 실패", error)
            }

            user != null -> {
                Log.e("Kakao","login")

                viewModel.signIn(
                    tokenId = token.accessToken,
                    type = SignInType.KAKAO
                )
            }
        }
    }
}

private fun loginKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        // 카카오 설치
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("Kakao", "카카오톡 로그인 실패", error)
            }

            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }

            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    } else {
        // 카카오 미설치
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }
}

private fun loginGoogle() {}
private fun loginNaver() {}