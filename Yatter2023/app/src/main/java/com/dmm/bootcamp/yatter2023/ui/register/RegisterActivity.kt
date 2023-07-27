package com.dmm.bootcamp.yatter2023.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2023.ui.login.LoginActivity
import com.dmm.bootcamp.yatter2023.ui.theme.Yatter2023Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: ComponentActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    companion object {
        fun newIntent(context: Context): Intent = Intent(
            context,
            RegisterActivity::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Yatter2023Theme {
                Surface {
                    RegisterPage(viewModel = viewModel)
                }
            }
        }

        viewModel.navigateToLogin.observe(this) {
            startActivity(LoginActivity.newIntent(this))
            finish()
        }
    }
}

@Composable
fun RegisterTemplate(
    userName: String,
    onChangedUserName: (String) -> Unit,
    password: String,
    onChangedPassword: (String) -> Unit,
    isEnableRegister: Boolean,
    isLoading: Boolean,
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
) {
    Scaffold (
        topBar = {
            TopAppBar (
                title = {
                    Text(text = "新規登録")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "ユーザー名",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                OutlinedTextField(
                    value = userName, 
                    onValueChange = onChangedUserName,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    placeholder = {
                        Text(text = "username")
                    }
                )

                Text(
                    text = "パスワード",
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = onChangedPassword,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    placeholder = {
                        Text(text = "password")
                    }
                )

                Button(
                    onClick = onClickRegister,
                    enabled = isEnableRegister,
                    modifier =  Modifier.fillMaxWidth()
                ) {
                    Text(text = "新規登録")
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "会員登録済みの方が",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
                TextButton(
                    onClick = onClickLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "ログイン")
                }
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun RegisterTemplatePreview() {
    Yatter2023Theme {
        Surface {
            RegisterTemplate(
                userName = "username",
                onChangedUserName = {},
                password = "P@ssword",
                onChangedPassword = {},
                isEnableRegister = true,
                isLoading = true,
                onClickLogin = {},
                onClickRegister = {},
            )
        }
    }
}