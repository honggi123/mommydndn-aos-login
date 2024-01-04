package com.mommydndn.app.ui.deprecated.components.inputfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.mommydndn.app.R
import com.mommydndn.app.data.model.common.ImageInputFieldType
import com.mommydndn.app.ui.theme.Grey300
import com.mommydndn.app.ui.theme.Grey50
import com.mommydndn.app.ui.theme.Grey500
import com.mommydndn.app.ui.theme.White
import com.mommydndn.app.ui.theme.paragraph300

@Composable
fun ImageInputField(
    modifier: Modifier = Modifier,
    inputType: ImageInputFieldType,
) {
    when (inputType) {
        is ImageInputFieldType.Add -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .width(width = 108.dp)
                    .height(height = 96.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(color = Grey50)
                    .clickable { inputType.onClick?.let { it() } }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_camera),
                    contentDescription = "",
                    tint = Grey300
                )
                if(inputType.isCaptionVisible){
                    Text(
                        text = inputType.index.toString() + "/10",
                        style = MaterialTheme.typography.paragraph300.copy(
                            fontWeight = FontWeight.Normal,
                            color = Grey500
                        )
                    )
                }
            }
        }

        is ImageInputFieldType.Editable -> {
            Box(
                modifier = modifier
                    .width(width = 108.dp)
                    .height(height = 96.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(inputType.imageUri),
                    contentDescription = "",
                    modifier = Modifier
                        .width(width = 108.dp)
                        .height(height = 96.dp)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_circle_x),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 88.dp,
                            y = (-5).dp
                        )
                        .clickable {
                            inputType.onRemoveClick?.let { it() }
                        }
                )
            }
        }

        is ImageInputFieldType.Ineditable -> {
            Box(
                modifier = modifier
                    .width(width = 108.dp)
                    .height(height = 96.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(color = White)
                    .clickable { inputType.onClick?.let { it() } }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(inputType.imageUri),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }


}


