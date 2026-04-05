package com.example.messenger.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.messenger.ui.ChatScreen
import com.example.messenger.ui.ContactsScreen
import com.example.messenger.ui.MessageOptionScreen
import com.example.messenger.viewmodel.ChatViewModel

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
fun AppNavigation(viewModel: ChatViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenManager.ContactsScreen.route
    ) {
        composable(ScreenManager.ContactsScreen.route) {
            ContactsScreen(
                contacts = viewModel.filteredContacts,
                searchQuery = viewModel.searchQuery,
                onSearchChange = { viewModel.updateSearch(it) },
                onChatClick = { chat ->
                    navController.navigate(
                        ScreenManager.ChatScreen.withArgs(chat.id.toString())
                    )
                }
            )
        }

        composable(
            route = "${ScreenManager.ChatScreen.route}/{chatId}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("chatId") ?: 0
            val messages = viewModel.chats.filter { it.chatId == id }
            ChatScreen(
                messages = messages,
                onSend = { chatId, text, isFromMe ->
                    viewModel.sendMessage(
                        chatId,
                        text,
                        isFromMe
                    )
                },
                onBack = { navController.popBackStack() },
                onMessageClick = { message ->
                    navController.navigate(
                        ScreenManager.MessageOptionScreen.withArgs(message.id.toString())
                    )
                }
            )
        }

        composable(
            route = "${ScreenManager.MessageOptionScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val messageId = backStackEntry.arguments?.getInt("id") ?: 0

            val message = viewModel.chats.find { it.id == messageId }

            if (message != null) {
                MessageOptionScreen(
                    message = message,
                    onDelete = {
                        viewModel.deleteMessage(message.id)
                        navController.popBackStack()
                    },
                    onEdit = { newText ->
                        viewModel.editMessage(message.id, newText)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("Message not found")
            }
        }
    }
}