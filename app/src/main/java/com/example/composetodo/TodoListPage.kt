package com.example.composetodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(modifier: Modifier = Modifier, viewModel: TodoViewModel) {

    val todoList = viewModel.todoList.observeAsState().value ?: emptyList()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                value = inputText,
                onValueChange = { inputText = it },
            )
            Button(onClick = {
                viewModel.addTodo(
                    title = inputText
                )
                inputText = ""
            }) {
                Text(text = "Add")
            }
        }

        todoList.let {
            if (it.isEmpty()) {
                Text(
                    text = "No todos yet",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        LazyColumn {
            itemsIndexed(todoList) { index, todo ->
                TodoItem(todo = todo, index = index, onDelete = {
                    viewModel.deleteTodo(
                        id = todo.id
                    )
                })
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, index: Int, onDelete: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = SimpleDateFormat(
                        "HH:mm:aa, dd/MM/yyyy",
                        Locale.ENGLISH
                    ).format(todo.createdAt),
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = todo.title,
                    style = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete todo",
                    tint = Color.White
                )
            }
        }
    }
}