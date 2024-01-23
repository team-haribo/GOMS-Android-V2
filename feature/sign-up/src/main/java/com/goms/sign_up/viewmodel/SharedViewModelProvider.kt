package com.goms.sign_up.viewmodel

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner

@Composable
fun SignUpViewModelProvider(
    viewModelStoreOwner: ViewModelStoreOwner,
    content: @Composable (viewModel: SignUpViewModel) -> Unit
) {
    val viewModel: SignUpViewModel = hiltViewModel(viewModelStoreOwner)
    content(viewModel)
}