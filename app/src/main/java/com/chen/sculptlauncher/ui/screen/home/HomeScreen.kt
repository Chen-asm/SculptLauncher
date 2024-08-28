package com.chen.sculptlauncher.ui.screen.home

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FolderDelete
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cn.hutool.core.io.FileUtil
import com.chen.sculptlauncher.R
import com.chen.sculptlauncher.core.bridge.HomeCppBridge
import com.chen.sculptlauncher.core.data.VersionEntry
import com.chen.sculptlauncher.core.datastore.IS_FIRST_OPEN
import com.chen.sculptlauncher.core.datastore.getStoredValue
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
            HomeState.Home -> HomePart(model = viewModel, context = context)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomePart(
        model: HomeModel,
        context: Context,
        windowSize: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    ){
        val isLarge = windowSize.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        model.fetchLocalVersions(context) // 初始化版本列表

        @Composable fun HomeDrawerContext(){
            ModalDrawerSheet {
                Text(
                    text = stringResource(id = R.string.home_sider_version_title),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.home_empty_install_one)) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "install from disk icon"
                        )
                    }
                )
                Text(
                    text = stringResource(id = R.string.home_sider_app_title),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.home_sider_app_cabin)) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FolderDelete,
                            contentDescription = "recovery bin icon"
                        )
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.home_sider_app_settings)) },
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings icon"
                        )
                    }
                )
            }
        }

        @Composable fun HomeContext() {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = stringResource(id = R.string.home_title)) },
                        navigationIcon = {
                            if (!isLarge) {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "open or close drawer"
                                    )
                                }
                            }
                        }
                    )
                }
            ) {
                Row(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                    AnimatedVisibility(
                        visible = isLarge,
                        modifier = Modifier.weight(1f)
                    ) {
                        HomeDrawerContext()
                    }
                    if (model.versionList.isEmpty()) {
                        EmptyListPane(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }

        if (!isLarge) {
            ModalNavigationDrawer(
                drawerContent = { HomeDrawerContext() },
                drawerState = drawerState
            ) {
                HomeContext()
            }
        } else {
            HomeContext()
        }
    }
}

private class HomeModel : ScreenModel {
    var uiState = mutableStateOf(HomeState.Loading)
    var versionList = mutableStateListOf<VersionEntry>()

    fun fetchLocalVersions(context: Context) {
        val versionPath = "${context.getExternalFilesDir("versions")}/"
        if (!FileUtil.exist(versionPath)) {
            FileUtil.mkdir(versionPath)
        }
        FileUtil.ls(versionPath).forEach {
            val singleFile = it.path
            versionList.add(VersionEntry(
                path = singleFile,
                manifestPath = "${singleFile}/manifest.launcher.json",
                gameArch = HomeCppBridge.fetchSOAbi("${singleFile}/libminecraftpe.so")
            ))
        }
    }

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

// 没有版本时的缺省页
@Composable
private fun EmptyListPane(modifier: Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.NotInterested,
            contentDescription = "nothing to show icon",
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.home_empty_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(onClick = { /*TODO*/ }) {
            Row {
                Icon(
                    imageVector = Icons.Default.InstallMobile,
                    contentDescription = "install a game"
                )
                Box(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.home_empty_install_one))
            }
        }
    }
}
