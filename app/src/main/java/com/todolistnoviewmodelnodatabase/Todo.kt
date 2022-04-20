package com.todolistnoviewmodelnodatabase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Todo(
   val name : String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

