package com.mommydndn.app.ui.viewmodel

import android.content.Context
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.mommydndn.app.R
import com.mommydndn.app.data.model.LoginGoogleResponse
import com.mommydndn.app.data.model.LoginType
import com.mommydndn.app.data.respository.AccountRepository
import kotlinx.coroutines.Dispatchers
import androidx.navigation.NavHostController
import com.mommydndn.app.data.model.LoginResponse
import com.mommydndn.app.ui.TypeChoiceNav
import com.mommydndn.app.utils.NavigationUtils
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {


    private val _errMsg = MutableStateFlow<String>("")
    val errMsg: StateFlow<String> = _errMsg

    fun signIn(
        tokenId: String,
        type: LoginType,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            val response = accountRepository.signIn(tokenId, type)
            handleSignInResponse(response, navHostController)
        }
    }

    fun handleGoogleSignInResult(
        accountTask: Task<GoogleSignInAccount>,
        clientId: String,
        clientSecret: String,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            val account = accountTask.result

            account.serverAuthCode?.let {
                val accessToken = getGoogleAccessToken(
                    it,
                    clientId,
                    clientSecret
                )

                if (accessToken != null) {
                    signIn(
                        tokenId = accessToken,
                        type = LoginType.GOOGLE,
                        navHostController = navHostController
                    )
                }
            }
        }
    }

    private suspend fun getGoogleAccessToken(
        authCode: String,
        clientId: String,
        clientSecret: String
    ): String? = withContext(Dispatchers.IO) {
        val res = accountRepository.getGoogleAccesstoken(authCode, clientId, clientSecret)
        res.body()?.access_token
    }

    private fun handleSignInResponse(
        response: ApiResponse<LoginResponse>,
        navHostController: NavHostController
    ) {
        response
            .onSuccess {}
            .onError {
                val message = when (statusCode) {
                    StatusCode.InternalServerError -> "InternalServerError"
                    StatusCode.Forbidden -> {
                        NavigationUtils.navigate(navHostController, TypeChoiceNav.route)
                        "Forbidden"
                    }

                    else -> "$statusCode(${statusCode.code}): ${message()}"
                }
                _errMsg.value = message
            }
            .onException {
                message?.let { _errMsg.value = it }
            }
    }
}