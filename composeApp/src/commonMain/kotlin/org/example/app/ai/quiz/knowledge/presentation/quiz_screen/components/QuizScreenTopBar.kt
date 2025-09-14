//package com.quiz.knowledge.learn.study.reward.myquiz.presentation.quiz.components
//
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun QuizScreenTopBar(
//    modifier: Modifier = Modifier,
//    title: String,
//    onCancel: () -> Unit
//) {
//
//    TopAppBar(
//        windowInsets = WindowInsets(0.dp),
//        modifier = modifier,
//        title = { Text(text = title) },
//        colors = TopAppBarDefaults.topAppBarColors(),
//        actions = {
//            IconButton(
//                onClick = onCancel,
//                content = {
//                    Icon(
//                        modifier = Modifier.size(32.dp),
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "End Quiz"
//                    )
//                }
//            )
//        }
//    )
//
//}