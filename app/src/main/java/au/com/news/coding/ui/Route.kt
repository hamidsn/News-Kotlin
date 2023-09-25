package au.com.news.coding.ui

sealed class Route(val route: String) {
    object Home : Route("home")
    object Detail : Route("detail?id={id}")
}