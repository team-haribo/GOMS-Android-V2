package com.goms.setting.viewmodel

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner

@Composable
fun SettingViewModelProvider(
    viewModelStoreOwner: ViewModelStoreOwner,
    content: @Composable (viewModel: SettingViewModel) -> Unit
) {
    val viewModel: SettingViewModel = hiltViewModel(viewModelStoreOwner)
    content(viewModel)
}