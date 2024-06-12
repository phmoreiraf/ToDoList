import React from 'react';

const Task = ({ task, onComplete, onDelete, onEdit, completed }) => {
    return (
        <div className={`task ${completed ? 'completed' : ''}`}>
            <div>
                <strong>Descrição:</strong>
                <span>{task.description}</span>
            </div>
            <div>
                <strong>Tipo:</strong>
                <span>{task.type}</span>
            </div>
            <div>
                <strong>Prioridade:</strong>
                <span>{task.priority}</span>
            </div>
            <div>
                <strong>Data de vencimento:</strong>
                <span>{task.dueDate || `${task.dueDays} dias`}</span>
            </div>
            <div className="task-buttons">
                {!completed && <button className="complete" onClick={onComplete}><strong>Concluir</strong></button>}
                {!completed && <button className="edit" onClick={onEdit} style={{backgroundColor: 'yellow'}}><strong>Editar</strong></button>}
                <button className="delete" onClick={onDelete}><strong>Excluir</strong></button>
            </div>
        </div>
    );
};

export default Task;