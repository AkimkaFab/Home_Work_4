import data.Priority
import data.Task
import data.TasksRepository
import net.serenitybdd.annotations.Step
import kotlin.test.assertEquals

open class ToDoSteps {

    lateinit var actor: String

    override fun toString(): String {
        return actor
    }


    @Step("Actor create {0} tasks.")
    fun createRepository(): TasksRepository = data.TasksRepositoryMemory()
    val repository = createRepository()
    open fun createTask(tasksAmount: Int): List<Int> {
        val list = mutableListOf<Int>()
        for (i: Int in 0 until tasksAmount) {
            val priorityLocal = listOf("LOW", "MEDIUM", "HIGH").random()
            list.add(repository.addTask(Task(name = "Task ${i+1}", priority = Priority.valueOf(priorityLocal) )))
        }
        return list
    }


    @Step("Actor check tasks amount. And get {0} tasks.")
    open fun checkTasksAmount(taskAmount: Int) {
        assertEquals(taskAmount, repository.getTasks().size)
    }


    @Step("Actor complete task № {0}.")
    open fun completeTask(completedTask: Int) {
        repository.completeTask(completedTask)
    }


    @Step("Actor check, that task № {0} completed.")
    open fun checkTaskCompleting(completedTask: Int) {
        val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
        allTasks.removeAt(completedTask-1)
        assertEquals(allTasks, repository.getTasks(completed = false))
    }


    @Step("Actor uncomplete task № {0}.")
    open fun uncompleteTask(completedTask: Int) {
        repository.uncompleteTask(completedTask)
    }


    @Step("Actor check, that task № {0} uncompleted.")
    open fun checkTaskUncompleting(tasksAmount: Int,completedTask: Int) {
        assertEquals(tasksAmount, repository.getTasks(completed = true).size)
    }

    @Step("Actor delete task № {0}.")
    open fun deleteTask(deleteTaskNumber: Int) {
        repository.deleteTask(deleteTaskNumber)
    }

    @Step("Actor check, that task № {0} deleted.")
    open fun checkTaskDeleting(tasksAmount: Int) {
        assertEquals(tasksAmount-1, repository.getTasks().size)
    }

}