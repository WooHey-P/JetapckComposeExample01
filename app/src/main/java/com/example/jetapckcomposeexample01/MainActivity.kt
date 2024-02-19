package com.example.jetapckcomposeexample01

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetapckcomposeexample01.ui.theme.JetapckComposeExample01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetapckComposeExample01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    // saveable state -> 화면 회전 등에도 상태를 유지
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(
                onClickContinue = { shouldShowOnboarding = false }
            )
        } else {
            Greetings()
        }

    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    /*Column(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        for (name in names) {
            Greeting(name = name)
        }
    }*/

    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        items(items = names) {
            Greeting(name = it)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        label = "padding",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier
                .padding(24.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                    .weight(1f)
            ) {
                Text(text = "Hello")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(text = stringResource(id = if (expanded) R.string.show_less else R.string.show_more))
            }
        }
    }
}

/**
 * 소형 스마트폰의 일반적인 너비인 320dp를 기준으로 미리보기를 생성
 */
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320)
@Composable
fun GreetingPreview() {
    JetapckComposeExample01Theme {
        MyApp(
            modifier = Modifier.fillMaxSize()
        )
    }
}