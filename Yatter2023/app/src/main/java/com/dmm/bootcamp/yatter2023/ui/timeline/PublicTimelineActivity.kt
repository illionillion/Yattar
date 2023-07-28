package com.dmm.bootcamp.yatter2023.ui.timeline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2023.ui.login.LoginActivity
import com.dmm.bootcamp.yatter2023.ui.post.PostActivity
import com.dmm.bootcamp.yatter2023.ui.profile.ProfileActivity
import com.dmm.bootcamp.yatter2023.ui.profile.ProfileViewModel
import com.dmm.bootcamp.yatter2023.ui.theme.Yatter2023Theme
import org.koin.androidx.viewmodel.ext.android.viewModel
/**
 * パブリックタイムラインのActivityを実装
 */
class PublicTimelineActivity : AppCompatActivity() {
    companion object {
        // Activityクラスをインスタンス化するメソッドを用意する
        fun newIntent(context: Context): Intent = Intent(
            context,
            PublicTimelineActivity::class.java,
        )
    }

    private val viewModel: PublicTimelineViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Yatter2023Theme {
                Surface {
                    PublicTimelinePage(viewModel = viewModel)
                }
            }
        }
        viewModel.navigateToPost.observe(this) {
            startActivity(PostActivity.newIntent(this))
        }
        viewModel.navigateToLogin.observe(this) {
            startActivity(LoginActivity.newIntent(this))
        }
        viewModel.navigateToProfile.observe(this) {
            startActivity(ProfileActivity.newIntent(this))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
