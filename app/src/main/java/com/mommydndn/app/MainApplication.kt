package com.mommydndn.app

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)

        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            BuildConfig.NAVER_CLIENT_NAME,
        )
    }
}