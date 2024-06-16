import React, { useState } from 'react';
import TaskForm from './TaskForm';
import Task from './task';

const TodoWrapper = () => {
    const [tasks, setTasks] = useState([]);
    const [completedTasks, setCompletedTasks] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [editingTask, setEditingTask] = useState(null);

    const addTask = (newTask, taskToEdit) => {
        if(taskToEdit) {
            const newTasks = tasks.map(t => t === taskToEdit ? newTask : t);
            setTasks(newTasks);
            setEditingTask(null);
        } else {
            setTasks([...tasks, newTask]);
        }
        setShowForm(false);
    };

    const completeTask = (index) => {
        const newTasks = [...tasks];
        const [completedTask] = newTasks.splice(index, 1);
        setCompletedTasks([...completedTasks, completedTask]);
        setTasks(newTasks);
    };

    const deleteTask = (index, isCompleted) => {
        if (isCompleted) {
            setCompletedTasks(completedTasks.filter((_, i) => i !== index));
        } else {
            setTasks(tasks.filter((_, i) => i !== index));
        }
    };

    const editTask = (task) => {
        setEditingTask(task);
        setShowForm(true);
    };

    return (
        <div className="todo-wrapper">
            <div className="pending-tasks">
                <h2>Tarefas Pendentes:</h2>
                {tasks.map((task, index) => (
                    <Task key={index} task={task} onComplete={() => completeTask(index)} onDelete={() => deleteTask(index, false)} onEdit={() => editTask(task)} />
                ))}
                {!editingTask && <button onClick={() => setShowForm(true)}>+</button>}
            </div>
            <div className="completed-tasks">
                <h2>Tarefas Concluídas:</h2>
                {completedTasks.map((task, index) => (
                    <Task key={index} task={task} onDelete={() => deleteTask(index, true)} completed />
                ))}
            </div>
            {showForm && <TaskForm onAddTask={addTask} initialTask={editingTask} />}
        </div>
    );
};

export default TodoWrapper;