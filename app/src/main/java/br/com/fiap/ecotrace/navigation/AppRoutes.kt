package com.ecotrace.navigation

sealed class Destination (val route: String) {
    object InitialScreen: Destination("initial_screen")
    object LoginScreen: Destination("login_screen")
    object SignupScreen: Destination("signup_screen")

    object DashboardScreen: Destination("dashboard_screen")

    object RegisterScreen: Destination("register_screen")

    object ProfileScreen: Destination("profile_screen")

    object TipsScreen: Destination("tips_screen")
}