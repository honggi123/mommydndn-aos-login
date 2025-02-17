package com.mommydndn.app.ui.care.post.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mommydndn.app.ui.theme.White

val PostSectionPadding = PaddingValues(
    start = 24.dp,
    top = 28.dp,
    end = 24.dp,
    bottom = 40.dp
)

@Composable
fun PostSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White)
            .padding(PostSectionPadding),
        verticalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        PostSectionTitle(
            title = title,
            subtitle = subtitle,
            modifier = Modifier.wrapContentSize(),
        )

        content()
    }
}