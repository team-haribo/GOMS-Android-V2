package com.goms.re_password.viewmodel

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner

@Composable
fun RePasswordViewmodelProvider(
    viewModelStoreOwner: ViewModelStoreOwner,
    content: @Composable (viewModel: RePasswordViewmodel) -> Unit
) {
    val viewModel: RePasswordViewmodel = hiltViewModel(viewModelStoreOwner)
    content(viewModel)
}