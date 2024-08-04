import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun InitState(call: () -> Unit) {
    var isFirstTime by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        if (isFirstTime) {
            call.invoke()
            isFirstTime = false
        }
    }
}
