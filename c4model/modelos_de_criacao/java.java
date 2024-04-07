@startuml
package "Model" {
    class Task {
        + ErrorMessage: string
    }
}

package "View" {
    interface View {
        + ListarTarefas()
        + IncluirTarefas(task: Task)
        + EditarTarefas(task: Task)
        + DeletarTarefas(taskId: int)
        + ConcluirTarefas(taskId: int)
    }
}

package "Controller" {
    class TaskController {
        - taskService: TaskService
        + TaskController(taskService: TaskService)
        + listarTarefas()
        + criarTarefa(task: Task)
        + atualizarTarefa(task: Task)
        + deletarTarefa(taskId: int)
        + completarTarefa(taskId: int)
    }
}

package "Service" {
    class TaskService {
        - taskRepository: TaskRepository
        + TaskService(taskRepository: TaskRepository)
        + listarTarefas()
        + criarTarefa(task: Task)
        + atualizarTarefa(task: Task)
        + deletarTarefa(taskId: int)
        + completarTarefa(taskId: int)
    }
}

package "Repository" {
    class TaskRepository {
        + listarTarefas(): List<Task>
        + criarTarefa(task: Task)
        + atualizarTarefa(task: Task)
        + deletarTarefa(taskId: int)
        + completarTarefa(taskId: int)
    }
}

package "H2Server" {
    class H2Server {
    }
}

package "Usuario" {
    class Usuario {
    }
}

Usuario -- TaskController : Interage
Usuario --> View : Visualiza
TaskController --> TaskService : Usa
TaskService --> TaskRepository : Usa
TaskRepository --> H2Server : Usa
TaskRepository --> Task : Mapeamento de dados
Task --> View : Atualiza
@enduml
