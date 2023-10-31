import React, { useState } from 'react';
import { Button, Card } from 'semantic-ui-react';
import { getTasks, updateTask } from '../../apis/Client';
import TaskEditor from '../../components/TaskEditor';
import { createTask } from '../../apis/Client';

const TaskList = (props) => {

    const onSelectTask = (task) => {
        props.onSelectTask(task);
    }

    return props.tasks.map(task => {
        return (
            <Card key={task.id}>
                <Card.Content>
                    <Card.Header onClick={() => onSelectTask(task)}>
                        {task.description}
                    </Card.Header>
                </Card.Content>
            </Card>
        );
    })
}

const TasksPage = () => {

    const blankTask = { id: null, description: '', deadline: ''};

    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);

    const onNewTask = () => {
        setSelectedTask(blankTask);
    }

    const onRefreshTasks = async () => {
        const response = await getTasks();
        const tasks = response.data;
        setTasks(tasks);
    };

    const onEditTaskDetails = (task) => {
        setSelectedTask(task);
    }

    const onChangeProperty = (name, value) => {
        const newSelectedTask = { ...(selectedTask), [name]: value };
        setSelectedTask(newSelectedTask);
    }

    const onSaveChanges = async () => {
        if (selectedTask.id === null) {
            const response = await createTask(selectedTask);
            const newTask = response.data;
            const newTasks = tasks.concat([newTask]);
            setTasks(newTasks);
        }
        else {
            await updateTask(selectedTask);
            const newTasks = tasks.map(x => selectedTask.id === x.id ? selectedTask : x);
            setTasks(newTasks);
        }
        
        setSelectedTask(null);
    }

    return (
        <div>
            <Button onClick={onNewTask}>New task</Button>
            <Button onClick={onRefreshTasks}>Refresh tasks</Button>
            <TaskList tasks={tasks} onSelectTask={onEditTaskDetails} />
            <TaskEditor
                task={selectedTask}
                onChangeProperty={onChangeProperty}
                onSaveChanges={onSaveChanges} />
            <pre>{JSON.stringify(tasks, null, 2)}</pre>
        </div>
    )
};

export default TasksPage;
