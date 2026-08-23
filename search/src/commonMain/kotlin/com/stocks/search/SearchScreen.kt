package com.stocks.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stocks.search.generated.resources.Res
import com.stocks.search.generated.resources.ic_search
import com.stocks.search.generated.resources.ic_buy
import com.stocks.yielda.ui.generated.resources.Res as UiRes
import com.stocks.yielda.ui.generated.resources.ic_heart
import com.stocks.yielda.ui.componens.MainToolbar
import com.stocks.yielda.ui.theme.YieldaTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
) {
    val results by viewModel.results.collectAsStateWithLifecycle()
    val recentSearchResults by viewModel.recentSearchResults.collectAsStateWithLifecycle()
    val queryState = viewModel.query.collectAsStateWithLifecycle()
    val displayedResults = if (queryState.value.trim().isEmpty()) {
        recentSearchResults
    } else {
        results
    }

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    SearchContent(
        results = displayedResults,
        queryState = queryState,
        onQueryChange = viewModel::onQueryChange,
    )
}

@Composable
private fun SearchContent(
    results: List<SearchResultItem>,
    queryState: State<String>,
    onQueryChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YieldaTheme.colorScheme.backgroundScreen)
            .padding(horizontal = 16.dp)
            .statusBarsPadding(),
    ) {
        MainToolbar(
            title = "Search",
            iconUrl = "https://picsum.photos/200/300",
            iconText = "Maksym",
        )
        Spacer(Modifier.height(4.dp))
        SearchTextField(
            queryState = queryState,
            onQueryChange = onQueryChange,
        )
        Spacer(Modifier.height(24.dp))

        if (results.isEmpty()) {
            Text(text = "No matches")
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(results, key = { item -> item.symbol }) { item ->
                    SearchResultRow(
                        item = item,
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResultRow(
    item: SearchResultItem,
    modifier: Modifier = Modifier,
    onFirstActionClick: () -> Unit = {},
    onSecondActionClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = YieldaTheme.colorScheme.searchFieldBackground,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(YieldaTheme.colorScheme.backgroundScreen, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = item.symbol.firstOrNull()?.uppercase() ?: item.description.firstOrNull()
                    ?.uppercase() ?: "",
                color = YieldaTheme.colorScheme.onBackground,
                style = YieldaTheme.typography.medium,
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = item.description,
                color = YieldaTheme.colorScheme.onBackground,
                style = YieldaTheme.typography.medium.copy(fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.displaySymbol,
                color = YieldaTheme.colorScheme.textSecondary,
                style = YieldaTheme.typography.regular.copy(fontSize = 12.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Image(
                painter = painterResource(UiRes.drawable.ic_heart),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable(onClick = onFirstActionClick),
            )

            Image(
                painter = painterResource(Res.drawable.ic_buy),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable(onClick = onSecondActionClick),
            )

        }
    }
}

@Composable
private fun SearchTextField(
    queryState: State<String>,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isFocused = remember { mutableStateOf(false) }
    val query = queryState.value

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            textStyle = YieldaTheme.typography.medium.copy(fontSize = 12.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = YieldaTheme.colorScheme.searchFieldBackground,
                unfocusedContainerColor = YieldaTheme.colorScheme.searchFieldBackground,
                disabledContainerColor = YieldaTheme.colorScheme.searchFieldBackground,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            label = if (query.isEmpty() && !isFocused.value) {
                {
                    Text(
                        text = "Stock or company",
                        color = YieldaTheme.colorScheme.textSecondary,
                        style = YieldaTheme.typography.label,
                    )
                }
            } else {
                null
            },
        )

        Spacer(modifier = Modifier.size(10.dp))

        Icon(
            painter = painterResource(Res.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (YieldaTheme.isDarkTheme) {
                Color.White
            } else {
                Color.Black
            },
        )
    }
}
