package com.example.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ChatViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(viewModel)
        }
    }
}

@Composable
fun AppNavigation(viewModel: ChatViewModel){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenManager.ContactsScreen.route
    ) {
        composable(ScreenManager.ContactsScreen.route) {
            ContactsScreen(
                contacts = viewModel.getContacts(),
                onChatClick = { chat ->
                    navController.navigate(
                        ScreenManager.ChatScreen.withArgs(chat.id.toString())
                    )
                }
            )
        }
        composable(
            route = "${ScreenManager.ChatScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ChatScreen(id, viewModel, onBack = {navController.popBackStack()})
        }
    }
}