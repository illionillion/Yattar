package com.dmm.bootcamp.yatter2023.ui.timeline

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmm.bootcamp.yatter2023.domain.model.Account
import com.dmm.bootcamp.yatter2023.domain.model.AccountId
import com.dmm.bootcamp.yatter2023.domain.model.Username
import com.dmm.bootcamp.yatter2023.infra.domain.model.MeImpl
import com.dmm.bootcamp.yatter2023.ui.theme.Yatter2023Theme
import com.dmm.bootcamp.yatter2023.ui.timeline.bindingmodel.StatusBindingModel
import kotlinx.coroutines.launch

/**
 * パブリックタイムラインのTemplateを実装
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PublicTimelineTemplate(
    myAccount: Account,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onClickPost: () -> Unit,
    onRefresh: () -> Unit,
    onClickLogout: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
    val context = LocalContext.current
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Profile",
            route = "profile",
            icon = Icons.Rounded.AccountBox,
        ),
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Create",
            route = "add",
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
        BottomNavItem(
            name = "Logout",
            route = "logout",
            icon = Icons.Rounded.ExitToApp,
        ),
    )
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Yatter", modifier = Modifier.padding(16.dp))
            Divider()
            // Drawer items
            // ユーザー名とアイコン
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    model = myAccount.avatar.toString(),
                    contentDescription = "アバター画像",
                    contentScale = ContentScale.Crop,
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    if (myAccount.displayName != "" && myAccount.displayName != null) {
                        Text(text = myAccount.displayName)
                    }
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = "@${myAccount.username.value}"
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
            ) {

                // ナビゲート
                items(bottomNavItems) { item ->
                    // val selected = item.route == backStackEntry.value?.destination?.route

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                when (item.name) {
                                    "Create" -> {
                                        onClickPost()
                                    }

                                    "Logout" -> {

                                        AlertDialog
                                            .Builder(context)
                                            .setTitle("ログアウト")
                                            .setTitle("ログアウトしますか？")
                                            .setPositiveButton(
                                                "OK",
                                                DialogInterface.OnClickListener { _, _ ->
                                                    onClickLogout()
                                                })
                                            .setNegativeButton(
                                                "Cancel",
                                                DialogInterface.OnClickListener { _, _ -> })
                                            .show()
                                    }

                                    else -> {

                                    }
                                }

                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                }
//                            navController.navigate(item.route)

                            },
//                    backgroundColor = if (selected) Color.Green else Color.White,
                        elevation = 0.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.name} Icon",
                                tint = if (item.name == "Logout") Color.Red else Color.DarkGray
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                text = item.name,
                                color = if (item.name == "Logout") Color.Red else Color.DarkGray
                            )
                        }
                    }
                }

            }

        },
        drawerGesturesEnabled = true,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "タイムライン")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open drawer")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickPost) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "post",
                    tint = Color.White
                )
            }
        },

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(statusList) { item ->
                    StatusRow(statusBindingModel = item)
                }
            }
            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }

}

@Preview
@Composable
private fun PublicTimelineTemplatePreview() {
    Yatter2023Theme {
        Surface {
            PublicTimelineTemplate(
                statusList = listOf(
                    // ここにリストで
                    StatusBindingModel(
                        id = "id",
                        displayName = "display name",
                        username = "username",
                        avatar = null,
                        content = "preview content",
                        attachmentMediaList = listOf()
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
                onClickPost = {},
                onRefresh = {},
                onClickLogout = {},
                myAccount = MeImpl(
                    id = AccountId("TEST_USER"),
                    username = Username("TEST_USER"),
                    displayName = "TEST - USER",
                    note = "",
                    avatar = null,
                    header = null,
                    followerCount = 0,
                    followingCount = 0,
                )
            )
        }
    }
}