import com.example.roteiro01.controller.TaskController;
import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TaskTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTarefaExistentePorId() {
        // Mock
        Task task = new Task();
        task.setId(1L);
        when(taskService.obterTarefaPorId(1L)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.obterTarefa(1L);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testObterTarefaInexistentePorId() {
        // Mock
        when(taskService.obterTarefaPorId(1L)).thenReturn(null);

        // Test
        ResponseEntity<Task> response = taskController.obterTarefa(1L);

        // Verification
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCriarTarefaComSucesso() {
        // Mock
        Task task = new Task();
        task.setId(1L);
        when(taskService.criarTarefa(any(Task.class))).thenReturn(task);

        // Test
        ResponseEntity<?> response = taskController.criarTarefa(task);

        // Verification
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Location"));
        assertEquals("/tasks/1", response.getHeaders().getLocation().getPath());
        assertEquals(task, response.getBody());
    }

    @Test
    void testCriarTarefaComExcecao() {
        // Mock
        when(taskService.criarTarefa(any(Task.class))).thenThrow(new IllegalArgumentException("Erro ao criar tarefa"));

        // Test
        ResponseEntity<?> response = taskController.criarTarefa(new Task());

        // Verification
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAtualizarTarefaComSucesso() {
        // Mock
        Task task = new Task();
        task.setId(1L);
        when(taskService.atualizarTarefa(1L, task)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.atualizarTarefa(1L, task);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testAtualizarTarefaNotFound() {
        // Mock
        when(taskService.atualizarTarefa(anyLong(), any(Task.class))).thenReturn(null);

        // Test
        ResponseEntity<Task> response = taskController.atualizarTarefa(1L, new Task());

        // Verification
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeletarTarefa() {
        // Test
        ResponseEntity<Void> response = taskController.deletarTarefa(1L);

        // Verification
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deletarTarefa(1L);
    }

    @Test
    void testObterTodasTarefas() {
        // Mock
        List<Task> tasks = new ArrayList<>();
        when(taskService.obterTodasTarefas()).thenReturn(tasks);

        // Test
        ResponseEntity<List<Task>> response = taskController.obterTodasTarefas();

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }
}
