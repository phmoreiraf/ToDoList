package com.example.roteiro01.service;

import com.example.roteiro01.dto.TaskAtualizarDTO;
import com.example.roteiro01.dto.TaskCriarDataDTO;
import com.example.roteiro01.dto.TaskCriarPrazoDTO;
import com.example.roteiro01.entity.Task;
import com.example.roteiro01.entity.TaskType;
import com.example.roteiro01.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Operation(summary = "Lista todas as tarefas da lista")
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Operation(summary = "Cria uma nova tarefa passando somente a descrição")
    public Task criar(String descricao) {
        Task novaTask = new Task(descricao);
        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Cria uma nova tarefa do tipoTask DATA, recebendo os dados do TaskCreateDataDTO")
    public Task criarDataTask(TaskCriarDataDTO taskDto) {
        if (taskDto.getDescricao() == null || taskDto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória");
        }

        LocalDate dataPlanejada = taskDto.getDataPrevistaConclusao();
        if (dataPlanejada == null || dataPlanejada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista de conclusão deve ser no presente ou no futuro");
        }

        Task novaTask = new Task(taskDto.getDescricao(), TaskType.DATA, taskDto.getPrioridade(), dataPlanejada);

        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Cria uma nova tarefa do tipoTask PRAZO, recebendo os dados do TaskCreatePrazoDTO")
    public Task criarPrazoTask(TaskCriarPrazoDTO taskDto) {
        if (taskDto.getDescricao() == null || taskDto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória");
        }

        Integer plannedDays = taskDto.getDiasPlanejados();
        if (plannedDays == null || plannedDays <= 0) {
            throw new IllegalArgumentException("O prazo previsto de conclusão deve ser um número positivo");
        }

        Task novaTask = new Task(taskDto.getDescricao(), TaskType.PRAZO, taskDto.getPrioridade(), plannedDays);

        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Atualiza uma tarefa existente")
    public Task atualizarTarefa(Long id, TaskAtualizarDTO taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));

        if (taskDto.getDescricao() != null && !taskDto.getDescricao().isEmpty()) {
            task.setDescricao(taskDto.getDescricao());
        }

        if (taskDto.getDataPrevistaConclusao() != null && !taskDto.getDataPrevistaConclusao().isBefore(LocalDate.now())) {
            task.setDataPlanejada(taskDto.getDataPrevistaConclusao());
        }

        if (taskDto.getPrioridade() != null) {
            task.setPriority(taskDto.getPrioridade());
        }

        if (taskDto.getDiasPlanejados() != null && taskDto.getDiasPlanejados() > 0) {
            task.setDatasPlanejadas(taskDto.getDiasPlanejados());
        }

        return taskRepository.save(task);
    }

    @Operation(summary = "Marca uma tarefa, cuja ID foi passada, como concluída")
    public Task marcarTarefaConcluida(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        task.setFinalizado(true);
        return taskRepository.save(task);
    }

    @Operation(summary = "Deleta a tarefa de acordo com o ID passado")
    public void deletar(Long id) {
        taskRepository.deleteById(id);
    }
}