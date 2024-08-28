package com.chen.sculptlauncher.ui.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.chen.sculptlauncher.core.datastore.IS_FIRST_OPEN
import com.chen.sculptlauncher.core.datastore.getStoredValue
import com.chen.sculptlauncher.core.datastore.globalStore
import com.chen.sculptlauncher.core.datastore.setStoreValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private enum class HomeState {
    Loading,
    NeedWizard,
    Home
}

object HomeScreen : Screen {
    private fun readResolve(): Any = HomeScreen

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel = rememberScreenModel { HomeModel() }
        when(viewModel.uiState.value){
            HomeState.Loading -> WaitingFor(context = context) {
                if (it) {
                    viewModel.uiState.value = HomeState.Home
                } else {
                    viewModel.uiState.value = HomeState.NeedWizard
                }
            }
            HomeState.NeedWizard -> WizardPart(context = context){
                viewModel.writeVersion(context)
            }
            HomeState.Home -> Text(text = "")
        }
    }
}

private class HomeModel : ScreenModel {
    var uiState = mutableStateOf(HomeState.Loading)

    fun writeVersion(context: Context) {
        screenModelScope.launch {
            context.setStoreValue(IS_FIRST_OPEN, "0.0.1")
            withContext(Dispatchers.Main){
                uiState.value = HomeState.Home
            }
        }
    }
}

@Composable
private fun WaitingFor(context: Context, where2go: (Boolean) -> Unit) {
    Box(modifier = Modifier)
    LaunchedEffect(key1 = Unit) {
        where2go(context.getStoredValue(IS_FIRST_OPEN).first() == "0.0.1")
    }
}
