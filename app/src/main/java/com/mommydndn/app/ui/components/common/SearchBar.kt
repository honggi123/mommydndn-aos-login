package com.mommydndn.app.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mommydndn.app.R
import com.mommydndn.app.ui.theme.Grey100
import com.mommydndn.app.ui.theme.Grey400
import com.mommydndn.app.ui.theme.Grey700
import com.mommydndn.app.ui.theme.MommydndnaosTheme
import com.mommydndn.app.ui.theme.Salmon600
import com.mommydndn.app.ui.theme.White
import com.mommydndn.app.ui.theme.paragraph300
import com.mommydndn.app.ui.extensions.bottomBorder

@Composable
fun Searchbar(
    keyword: String,
    placeHolderText: String = "",
    onValueChange: (String) -> Unit,
    backStackAction: () -> Unit,
    searchAction: () -> Unit,
    clearAction: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(color = White)
            .bottomBorder(2.dp, Grey100)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp, end = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(onClick = {
                backStackAction()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "",
                    tint = Grey400
                )
            }

            Spacer(modifier = Modifier.padding(6.dp))

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Salmon600,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { searchAction() }
                ),
                textStyle = MaterialTheme.typography.paragraph300.copy(
                    fontWeight = FontWeight.Medium,
                    color = Grey700,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeHolderText,
                        style = MaterialTheme.typography.paragraph300.copy(
                            fontWeight = FontWeight.Medium,
                            color = Grey400,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        maxLines = 1
                    )
                },
                value = keyword,
                onValueChange = onValueChange,
                trailingIcon = {
                    if (!keyword.isEmpty()) {
                        IconButton(
                            onClick = { clearAction() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp),
                                painter = painterResource(id = R.drawable.ic_x_circle),
                                contentDescription = "",
                                tint = Grey400
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun previewSearchbar() {
    MommydndnaosTheme {
        var textFieldValue by remember { mutableStateOf("") }
        Searchbar(
            keyword = textFieldValue,
            onValueChange = { textFieldValue = it },
            clearAction = { textFieldValue = "" },
            placeHolderText = "placeholder",
            backStackAction = {},
            searchAction = {}
        )
    }
}