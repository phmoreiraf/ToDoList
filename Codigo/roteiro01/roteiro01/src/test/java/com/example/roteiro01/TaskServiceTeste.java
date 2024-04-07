
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import com.example.roteiro01.service.*;
import com.example.roteiro01.entity.*;
import com.example.roteiro01.model.*;
import com.example.roteiro01.controller.*;
import com.example.roteiro01.repository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)

public class TaskServiceTeste {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void obterTarefaPorId_PrazoTaskWithPastDeadline_ReturnsExpectedStatus() {
        Task prazoTask = new Task();
        prazoTask.setType(TaskType.PRAZO);
        prazoTask.setDeadlineInDays(1);
        prazoTask.setDueDate(LocalDate.now().minusDays(2)); // Prazo expirado
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(prazoTask));

        Task result = taskService.obterTarefaPorId(1L);

        assertEquals(TaskStatus.ATRASADA, result.getStatus());
    }

    @Test
    public void obterTarefaPorId_LivreTask_ReturnsExpectedStatus() {
        Task livreTask = new Task();
        livreTask.setType(TaskType.LIVRE);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(livreTask));

        Task result = taskService.obterTarefaPorId(1L);

        assertEquals(TaskStatus.PREVISTA, result.getStatus());
    }

}
