package kr.ac.kumoh.ce.s20190467.smartapp_project

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage

// 책 앱의 두 가지 화면 상태를 정의: 목록 및 상세 정보
enum class BookScreen {
    List,
    Detail
}

// 책 앱의 주요 UI를 구성
@Composable
fun BookApp(bookList: List<Book>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BookScreen.List.name,
    ) {
        composable(route = BookScreen.List.name) {
            BookList(bookList) {
                navController.navigate(it)
            }
        }
        composable(
            route = BookScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                BookDetail(bookList[index])
        }
    }
}

//책 목록 표시
@Composable
fun BookList(list: List<Book>, onNavigateToDetail: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(list.size) {
            BookItem(it, list[it], onNavigateToDetail)
        }
    }
}

//개별 책 아이템 표시
@Composable
fun BookItem(index: Int, book: Book, onNavigateToDetail: (String) -> Unit) {

    Card(
        modifier = Modifier.clickable {
            onNavigateToDetail(BookScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            book.image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "책커버",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(percent = 10)),
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextTitle(book.title)
                TextWriter(book.writer)
            }
        }
    }
}
//책제목, 저자 표시
@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextWriter(writer: String) {
    Text(writer, fontSize = 20.sp)
}

//책의 상세 정보 표시
@Composable
fun BookDetail(book: Book) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingBar(book.rating)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            book.title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        book.image?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "책 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(book.writer, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))

        book.description?.let {
            Text(
                it,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=+${book.title}")
            )
            startActivity(context, intent, null)
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                googleIcon()
                Spacer(modifier = Modifier.width(16.dp))
                Text("책 검색", fontSize = 30.sp)
            }
        }

    }
}
//별점 표시
@Composable
fun RatingBar(stars: Int) {
    Row {
        repeat(stars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "stars",
                modifier = Modifier.size(48.dp),
                tint = Color.Red)
        }

        // 빈 별
        repeat(5 - stars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Empty star",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
        }
    }
}

// 출처
// https://github.com/worstkiller/jetpackcompose_canvas_icon_pack
// https://github.com/worstkiller/jetpackcompose_canvas_icon_pack/blob/master/app/src/main/java/com/vikas/jetpackcomposeiconpack/ShapesActivity.kt
@Composable
fun googleIcon() {

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(20.dp)
    ) {
        val width = this.size.width
        val height = this.size.height

        val path = Path()
        path.moveTo(width / 2, height / 2)
        path.lineTo(width + 5, height * .05f)
        path.lineTo(width + 20, height * 0.45f)


        clipPath(path = path, clipOp = ClipOp.Difference) {
            drawCircle(
                color = Color.Transparent,
                radius = 100f,
                style = Stroke(40f, cap = StrokeCap.Round)
            )
            drawPath(path = path, color = Color.Transparent)
        }

        drawRect(
            color = Color(0xFF4384f3),
            size = Size(width * .56f, 20f),
            topLeft = Offset(width * .55f, height * .45f)
        )

        drawRect(
            color = Color(0xFF4384f3),
            size = Size(width * .45f, 20f),
            topLeft = Offset(width * .55f, height * .54f)
        )

        drawArc(
            color = Color(0xFF4384f3),
            startAngle = 0f,
            sweepAngle = 45f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        drawArc(
            color = Color(0xFF33a852),
            startAngle = 45f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        drawArc(
            color = Color(0xFFfabd03),
            startAngle = 145f,
            sweepAngle = 80f,
            useCenter = false,
            style = Stroke(width = 40f)
        )

        drawArc(
            color = Color(0xFFeb4435),
            startAngle = 205f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
    }
}