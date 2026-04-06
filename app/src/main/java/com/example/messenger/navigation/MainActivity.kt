package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.messenger.ui.*
import com.example.messenger.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
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
                contacts = listOf(
                    Chat(1, "Alex"),
                    Chat(2, "John"),
                    Chat(3, "Maria")
                ),
                onChatClick = { chat ->
                    navController.navigate(
                        ScreenManager.ChatScreen.withArgs(chat.id.toString())
                    )
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "${ScreenManager.ChatScreen.route}/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ) { backStackEntry ->

            val chatId = backStackEntry.arguments?.getInt("chatId") ?: 0

            val messages by viewModel.getMessages(chatId)
                .collectAsState(initial = emptyList())

            ChatScreen(
                messages = messages,
                chatId = chatId,
                onSend = { id, text, isFromMe ->
                    viewModel.sendMessage(id, text, isFromMe)
                },
                onBack = { navController.popBackStack() },
                onMessageClick = { message ->

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("messageId", message.id)

                    navController.navigate(
                        ScreenManager.MessageOptionScreen.route
                    )
                },
                formatTime = viewModel::formatTime
            )
        }

        composable(ScreenManager.MessageOptionScreen.route) {

            val viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            val messageId =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Int>("messageId")

            if (messageId != null) {
                MessageOptionScreen(
                    messageId = messageId,
                    viewModel = viewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}