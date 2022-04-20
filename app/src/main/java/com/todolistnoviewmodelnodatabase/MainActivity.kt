package com.todolistnoviewmodelnodatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todolistnoviewmodelnodatabase.ui.theme.TodoListNoViewModelNoDatabaseTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListNoViewModelNoDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoScreen()
                }
            }
        }
    }
}

@Composable
fun TodoScreen(modifier: Modifier = Modifier) {
    var _list: MutableList<Todo> = mutableListOf()
    (0..20).map { _list.add(Todo("Todo #$it")) }
    var list = remember { _list.toMutableStateList() }

    TodoContent(list = list, onDeleteClicked = { list.remove(it) })
}

@Composable
fun TodoContent(
    list: SnapshotStateList<Todo>,
    onDeleteClicked: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    var index by remember { mutableStateOf(0)}
    LazyColumn()
    {
        items(
            items = list,
            key = { todo -> todo.name }
        )
        { todo ->
            index++
            if(index !=0)
                Divider()
            TodoItem(
                todo = todo,
                onDeleteClicked = { onDeleteClicked(todo) })
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onDeleteClicked: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable { mutableStateOf(false) }
    TodoItem(
        todo = todo,
        onDeleteClicked = onDeleteClicked,
        checked = checked,
        onCheckedChanged = { checked = it },
        modifier = modifier
    )
}

@Composable
fun TodoItem(
    todo: Todo,
    onDeleteClicked: (Todo) -> Unit,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = todo.name
        )
        Checkbox(checked = checked, onCheckedChange = { onCheckedChanged(it) })
        IconButton(onClick = { onDeleteClicked(todo) }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}