package io.cyberfan.cyberchat

import ChatScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.cyberfan.cyberchat.login.ChatLoginScreen
import io.cyberfan.cyberchat.ui.theme.CyberChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyberChatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatApp(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ChatApp(
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("login") {
            ChatLoginScreen(
                onNavigateToChat = { chatId, user ->
                    navController.navigate("chat/$chatId/$user")
                    Log.d("TAG", "ChatApp: $chatId $user")
                }
            )
        }

        composable(
            "chat/{chatId}/{user}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("user") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val user = backStackEntry.arguments?.getString("user") ?: ""

            ChatScreen(
                chatId = chatId,
                user = user,
                onExitClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
