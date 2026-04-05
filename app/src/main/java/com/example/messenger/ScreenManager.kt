package com.example.messenger

sealed class ScreenManager(val route: String) {
    object ChatScreen: ScreenManager("ChatScreen")
    object ContactsScreen: ScreenManager("ContactsScreen")
    object MessageOptionScreen: ScreenManager("MessageOptionScreen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
