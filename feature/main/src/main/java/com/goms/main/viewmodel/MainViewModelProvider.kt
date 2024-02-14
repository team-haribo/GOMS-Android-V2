package com.goms.main.viewmodel

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner

@Composable
fun MainViewModelProvider(
    viewModelStoreOwner: ViewModelStoreOwner,
    content: @Composable (viewModel: MainViewModel) -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel(viewModelStoreOwner)
    content(viewModel)
}