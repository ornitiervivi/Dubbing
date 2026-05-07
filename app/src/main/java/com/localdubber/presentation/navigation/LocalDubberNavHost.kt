package com.localdubber.presentation.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.localdubber.core.AppContainer
import com.localdubber.presentation.detail.DetailViewModel
import com.localdubber.presentation.home.HomeViewModel

@Composable
fun LocalDubberNavHost(container: AppContainer) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val vm: HomeViewModel = viewModel(factory = SimpleFactory { HomeViewModel(container.createDubbingProject, container.getDubbingProjects, container.deleteDubbingProject) })
            val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
                uri?.let { vm.create(it.lastPathSegment ?: "video", it.toString()) { id -> navController.navigate("detail/$id") } }
            }
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Text("LocalDubber", style = MaterialTheme.typography.headlineMedium)
                Text("Crie projetos de dublagem local a partir dos seus vídeos.")
                Button(onClick = { picker.launch(arrayOf("video/*")) }, modifier = Modifier.padding(vertical = 12.dp)) { Text("Selecionar vídeo local") }
                LazyColumn { items(vm.projects.collectAsStateWithLifecycleCompat().value) { p ->
                    Column(Modifier.fillMaxWidth().clickable { navController.navigate("detail/${p.id}") }.padding(8.dp)) {
                        Text(p.fileName)
                        Text(p.status.name)
                        Text("Criado em: ${p.createdAt}")
                        TextButton(onClick = { vm.delete(p.id) }) { Text("Excluir") }
                    }
                } }
            }
        }
        composable("detail/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { backStack ->
            val vm: DetailViewModel = viewModel(factory = SimpleFactory { DetailViewModel(container.getDubbingProjectById) })
            val id = backStack.arguments?.getLong("id") ?: 0L
            LaunchedEffect(id) { vm.load(id) }
            val project by vm.project.collectAsStateWithLifecycleCompat()
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Text("Detalhes do Projeto", style = MaterialTheme.typography.headlineSmall)
                project?.let {
                    Text(it.fileName)
                    Text(it.videoUri)
                    Text(it.status.name)
                }
                Text("Etapas")
                vm.timeline.forEach { Text("• ${it.name}") }
            }
        }
    }
}
