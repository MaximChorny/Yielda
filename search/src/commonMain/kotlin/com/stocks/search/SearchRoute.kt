package com.stocks.search

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun SearchRoute(viewModel: SearchViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SearchDestination,
    ) {
        composable<SearchDestination> {
            SearchScreen(
                viewModel = viewModel,
                onDetailsClick = { item ->
                    viewModel.onSearchResultDetailsClick(item)
                    navController.navigate(SearchDetails)
                },
            )
        }

        composable<SearchDetails> {
            SearchDetailsScreen(
                viewModel = viewModel,
                onBackClick = {
                    viewModel.clearSelectedSearchResult()
                    navController.popBackStack()
                },
            )
        }
    }
}

@Serializable
private data object SearchDestination

@Serializable
private data object SearchDetails
