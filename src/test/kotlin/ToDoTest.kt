import net.serenitybdd.annotations.Steps
import net.serenitybdd.junit5.SerenityJUnit5Extension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(SerenityJUnit5Extension::class)
class ToDoTest {

    @Steps
    lateinit var user: ToDoSteps

    @Test
    fun createTaskTest() {
        val tasksAmount = (1 ..10).random()
        user.createTask(tasksAmount)
        user.checkTasksAmount(tasksAmount)
    }

    @Test
    fun completeTaskTest() {
        val tasksAmount = (1 ..10).random()
        val completedTask = (1 .. tasksAmount).random()
        user.createTask(tasksAmount)
        user.completeTask(completedTask)
        user.checkTaskCompleting(completedTask)
    }

    @Test
    fun uncompleteTastTest() {
        val tasksAmount = (1 ..10).random()
        val completedTask = (1 .. tasksAmount).random()
        user.createTask(tasksAmount)
        user.completeTask(completedTask)
        user.uncompleteTask(completedTask)
        user.checkTaskUncompleting(tasksAmount, completedTask)
    }

    @Test
    fun getEmptyTaskList() {
        user.checkTasksAmount(0)
    }

    @Test
    fun deleteTaskTest() {
        val tasksAmount = (1 ..10).random()
        val deleteTaskNumber = (1 .. tasksAmount).random()
        user.createTask(tasksAmount)
        user.deleteTask(deleteTaskNumber)
        user.checkTaskDeleting(tasksAmount)
    }

}