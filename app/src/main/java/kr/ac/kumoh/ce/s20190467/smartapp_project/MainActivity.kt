package kr.ac.kumoh.ce.s20190467.smartapp_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.ac.kumoh.ce.s20190467.smartapp_project.ui.theme.Smartapp_projectTheme

class MainActivity : ComponentActivity() {
    // ViewModel 인스턴스를 생성. 노래 목록과 같은 데이터를 관리
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel) {
    val bookList by viewModel.bookList.observeAsState(emptyList())

    Smartapp_projectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BookApp(bookList)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Smartapp_projectTheme {
        MainScreen(viewModel = BookViewModel())
    }
}
