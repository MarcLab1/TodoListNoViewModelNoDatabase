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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todolistnoviewmodelnodatabase.ui.theme.TodoListNoViewModelNoDatabaseTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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
    var todoViewModel: TodoViewModel = viewModel()
    TodoContent(
        list = todoViewModel.todos,
        onDeleteClicked = { todoViewModel.remove(it) },
        onCheckedChanged = { todo, checked -> todoViewModel.onCheckedChanged(todo, checked) })
}

@Composable
fun TodoContent(
    list: List<Todo>,
    onDeleteClicked: (Todo) -> Unit,
    onCheckedChanged: (Todo, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var index by remember { mutableStateOf(0) }
    LazyColumn()
    {
        items(
            items = list,
            key = { todo -> todo.name }
        )
        { todo ->
            index++
            if (index != 0)
                Divider()
            TodoItem(
                todo = todo,
                onDeleteClicked = { onDeleteClicked(todo) },
                checked = todo.checked,
                onCheckedChanged = { checked -> onCheckedChanged(todo, checked) },
                )
        }
    }
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
        Checkbox(checked = checked, onCheckedChange = {onCheckedChanged(it)})
        IconButton(onClick = { onDeleteClicked(todo) }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}

class TodoViewModel : ViewModel() {
    private val _todos = getTodos().toMutableStateList()
    val todos: List<Todo>
        get() = _todos

    fun remove(todo: Todo) {
        _todos.remove(todo)
    }

    fun removeByIndex(index: Int)
    {
        _todos.removeAt(index)
    }

    fun onCheckedChanged(todo: Todo, checked: Boolean)
    {
        _todos.find { it.name == todo.name }?.let { todo ->
            todo.checked = checked
        }
    }
}

private fun getTodos() = List(30) { i -> Todo("Task #$i") }