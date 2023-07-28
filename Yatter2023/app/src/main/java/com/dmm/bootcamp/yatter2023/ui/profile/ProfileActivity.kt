package com.dmm.bootcamp.yatter2023.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
//import com.dmm.bootcamp.yatter2023.ui.edit.EditActivity
import com.dmm.bootcamp.yatter2023.ui.theme.Yatter2023Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity: AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(
            context,
            ProfileActivity::class.java
        )
    }

    val viewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Yatter2023Theme {
                Surface {
                    ProfilePage(viewModel = viewModel)
                }
            }
        }

        viewModel.navigateToEdit.observe(this) {
//            startActivity(EditActivity.newIntent(this))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}