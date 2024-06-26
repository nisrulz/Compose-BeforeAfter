package com.smarttoolfactory.composebeforeafter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composebeforeafter.demo.BeforeAfterImageDemo
import com.smarttoolfactory.composebeforeafter.demo.BeforeAfterLayoutDemo
import com.smarttoolfactory.composebeforeafter.ui.theme.ComposeBeforeAfterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBeforeAfterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    HomeContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent() {
    val pagerState: PagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = {
                tabList.size
            },
        )

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                // Our selected tab is our current page
                selectedTabIndex = pagerState.currentPage,
                // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                indicator = { tabPositions: List<TabPosition> ->
                    TabRowDefaults.Indicator(
                        modifier =
                            Modifier.tabIndicatorOffset(
                                tabPositions[pagerState.currentPage],
                            ),
                        height = 4.dp,
                    )
                },
            ) {
                // Add tabs for all of our pages
                tabList.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
        },
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            state = pagerState,
            userScrollEnabled = false,
        ) { page: Int ->

            when (page) {
                0 -> BeforeAfterImageDemo()
                else -> BeforeAfterLayoutDemo()
            }
        }
    }
}

internal val tabList =
    listOf(
        "Before/After Image",
        "Before/After Layout",
    )
