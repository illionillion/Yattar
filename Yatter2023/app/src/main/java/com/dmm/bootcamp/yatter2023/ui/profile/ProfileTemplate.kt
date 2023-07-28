package com.dmm.bootcamp.yatter2023.ui.profile

import android.webkit.URLUtil
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dmm.bootcamp.yatter2023.R
import com.dmm.bootcamp.yatter2023.ui.component.FullScreenLoadingIndicator
import com.dmm.bootcamp.yatter2023.ui.theme.Yatter2023Theme
import com.dmm.bootcamp.yatter2023.ui.timeline.StatusRow
import com.dmm.bootcamp.yatter2023.ui.timeline.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2023.ui.timeline.bindingmodel.StatusBindingModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    username: String,
    displayName: String,
    note: String,
    avatar: String?,
    header: String?,
    followingCount: Int,
    followerCount: Int,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickEdit: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        val startSpace = 20
        LazyColumn(modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(5.dp)
        ) {
            item {

                AsyncImage(
                    model = header,
                    contentDescription = "header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Divider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.Gray), CircleShape),
                        model = avatar,
                        contentDescription = "avatar image",
                        contentScale = ContentScale.Crop
                    )
                    OutlinedButton(
                        onClick = onClickEdit,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            backgroundColor = Color.White,
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Edit",
                        )
                    }
                }
                Text(
                    text = displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = startSpace.dp)
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = "@${username}", modifier = Modifier.padding(start = startSpace.dp))
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = note, modifier = Modifier.padding(start = startSpace.dp))
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Row(modifier = Modifier.padding(start = startSpace.dp)) {
                    Text(text = "$followingCount", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 2.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = "following")
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(text = "$followerCount", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 2.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = "followers")
                    }
                }
                Divider(thickness = 1.dp, modifier = Modifier.padding(top = 10.dp))
            }
            items(statusList) {
                Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                    StatusRow(
                        statusBindingModel = it,
//                    onClick = {}
                    )
                    Divider()
                }

            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if (isLoading) {
            FullScreenLoadingIndicator()
        }
    }
}

@Preview
@Composable
private fun ProfileTemplatePreview() {
    Yatter2023Theme {
        Surface {
            ProfileTemplate(
                username = "yt8492",
                displayName = "mayamito",
                note = "Hello, World",
                avatar = "https://avatars.githubusercontent.com/u/39693306?v=4",
                header = "https://pbs.twimg.com/profile_banners/972404402425245697/1690337648/1500x500",
                followingCount = 50,
                followerCount = 100,
                statusList = listOf(
                    StatusBindingModel(
                        id = "id",
                        displayName = "display name",
                        username = "username",
                        avatar = "https://avatars.githubusercontent.com/u/39693306?v=4",
                        content = "preview content",
                        attachmentMediaList = listOf(
                            MediaBindingModel(
                                id = "id",
                                type = "image",
                                url = "https://avatars.githubusercontent.com/u/39693306?v=4",
                                description = "icon"
                            ),
                            MediaBindingModel(
                                id = "id",
                                type = "image",
                                url = "https://avatars.githubusercontent.com/u/39693306?v=4",
                                description = "icon"
                            ),
                            MediaBindingModel(
                                id = "id",
                                type = "image",
                                url = "https://pbs.twimg.com/profile_banners/972404402425245697/1690337648/1500x500",
                                description = "icon"
                            )
                        )
                    ),
                    StatusBindingModel(
                        id = "id",
                        displayName = "display name",
                        username = "username",
                        avatar = "https://pbs.twimg.com/profile_banners/972404402425245697/1690337648/1500x500",
                        content = "preview content",
                        attachmentMediaList = listOf(
                            MediaBindingModel(
                                id = "id",
                                type = "image",
                                url = "https://avatars.githubusercontent.com/u/39693306?v=4",
                                description = "icon"
                            ),
                            MediaBindingModel(
                                id = "id",
                                type = "image",
                                url = "https://pbs.twimg.com/profile_banners/972404402425245697/1690337648/1500x500",
                                description = "icon"
                            )
                        )
                    ),
                    StatusBindingModel(
                        id = "id",
                        displayName = "display name",
                        username = "username",
                        avatar = null,
                        content = "preview content",
                        attachmentMediaList = listOf()
                    )
                ),
                isLoading = false,
                isRefreshing = false,
                onRefresh = {},
                onClickEdit = {}
            )
        }
    }
}