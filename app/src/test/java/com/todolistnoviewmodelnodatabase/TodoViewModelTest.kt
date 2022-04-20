package com.todolistnoviewmodelnodatabase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TodoViewModelTest {

    private lateinit var viewModel: TodoViewModel

    @Before
    fun doNothing() {
        viewModel = TodoViewModel()
    }

    @Test
    fun removeIndex() {
        viewModel.removeByIndex(1)
        assertEquals(viewModel.todos.size, 29)
    }

    @Test
    fun removeTodo() {
        viewModel.remove(viewModel.todos.get(0))
        assertEquals(viewModel.todos.size, 29)
    }

    @Test
    fun removeTodoAgain() {
        val temp1 = viewModel.todos.get(0)
        val temp2 = viewModel.todos.get(1)
        viewModel.remove(temp1)
        assertFalse(viewModel.todos.contains(temp1))
        //assertFalse(viewModel.todos.contains(temp2))
    }

    @Test
    fun changeChecked() {
        viewModel.todos.get(0).checked = true
        assert(viewModel.todos[0].checked, { "Ok Jose?"})
    }
}
