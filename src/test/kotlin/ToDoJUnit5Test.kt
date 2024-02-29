import data.Priority
import data.Task
import data.TasksRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ToDoJUnit5Test {


        fun createRepository(): TasksRepository = data.TasksRepositoryMemory()
        var repository = createRepository()
        fun generateTestData(taskAmount: Int): List<Int> {
            val list = mutableListOf<Int>()
            for (i: Int in 0 until taskAmount) {
                val priorityLocal = listOf("LOW", "MEDIUM", "HIGH").random()
                list.add(repository.addTask(Task(name = "Task ${i+1}", priority = Priority.valueOf(priorityLocal) )))
            }
            return list
        }


    @BeforeEach
    fun beforeEach() {
    }


    @ParameterizedTest
    @MethodSource("generateTaskAmount")
    @DisplayName("Тест создания таски")
    fun createTaskTest(taskAmount: Int) {
        generateTestData(taskAmount)
        Assertions.assertEquals(taskAmount, repository.getTasks().size)

    }

    @Test
    @DisplayName("Тест запроса пустого списка тасок")
    fun getEmptyTaskList() {
        Assertions.assertEquals(0,repository.getTasks().size)
    }

    @ParameterizedTest
    @MethodSource("generateTaskAmount")
    @DisplayName("Тест выполнения таски + фильтра выполненных тасок")
    fun completeTaskTest(taskAmount: Int) {
        generateTestData(taskAmount)
        val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
        val completedTask = (1..taskAmount).random()
        repository.completeTask(completedTask)
        allTasks.removeAt(completedTask-1)
        Assertions.assertEquals(allTasks, repository.getTasks(completed = false))
    }

    @ParameterizedTest
    @MethodSource("generateTaskAmount")
    @DisplayName("Тест отмены выполнения таски")
    fun uncompleteTaskTest(taskAmount: Int) {
        generateTestData(taskAmount)
        val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
        val allTasksOrigin = repository.getTasks()
        val completedTask = (1..taskAmount).random()
        repository.completeTask(completedTask)
        allTasks.removeAt(completedTask-1)
        Assertions.assertEquals(allTasks, repository.getTasks(completed = false))
        repository.uncompleteTask(completedTask)
        Assertions.assertEquals(allTasksOrigin, repository.getTasks(completed = false))
    }

    @ParameterizedTest
    @MethodSource("generateTaskAmount")
    @DisplayName("Тест удаления таски")
    fun deleteTaskTest(taskAmount: Int) {
        generateTestData(taskAmount)
        val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
        val deletedTask = (1..taskAmount).random()
        repository.deleteTask(deletedTask)
        allTasks.removeAt(deletedTask-1)
        Assertions.assertEquals(allTasks, repository.getTasks())
    }


    @AfterEach
    fun tearDown() {
        for (i: Int in 1 until 11) {
            repository.deleteTask(i)
        }

    }

    companion object {

        @JvmStatic
        fun generateTaskAmount() : Iterator<Arguments> = listOf(
                Arguments.of((1..10).random())
                ).iterator()

    }
}