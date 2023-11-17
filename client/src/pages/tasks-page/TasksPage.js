import React, { useState } from 'react';
import { Button, ButtonGroup, Card } from 'semantic-ui-react';
import { createTask, getAllTasks, getDueTasks, updateTask } from '../../apis/Client';
import TaskEditor from '../../components/TaskEditor';

const TaskList = (props) => {

    const onSelectTask = (task) => {
        props.onSelectTask(task);
    }

    return props.tasks.map(task => {
        const formatDate = (date) => new Date(date).toLocaleDateString('en-US')

        return (
            <Card key={task.id}>
                <Card.Content>
                    <Card.Header onClick={() => onSelectTask(task)}>{task.description}</Card.Header>
                    <Card.Content onClick={() => onSelectTask(task)}>{formatDate(task.deadline)}</Card.Content>
                </Card.Content>
            </Card>
        );
    })
}

const TasksPage = () => {

    const blankTask = { id: null, description: '', deadline: new Date() };

    const TaskFilter = {
        All: 'All',
        Due: 'Due'
    };

    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [taskFilter, setTaskFilter] = useState(TaskFilter.All);

    const onSelectTaskFilter = async (newTaskFilter) => {
        setTaskFilter(newTaskFilter);

        switch (newTaskFilter) {
            default:
            case TaskFilter.Due:
                const dueTasks = await getDueTasks();
                setTasks(dueTasks.data);
                break;

            case TaskFilter.All:
                const newTasks = await getAllTasks();
                setTasks(newTasks.data);
                break;
        }
    };

    const onNewTask = () => {
        setSelectedTask(blankTask);
    };

    const onEditTaskDetails = (task) => {
        setSelectedTask(task);
    };

    const onChangeProperty = (name, value) => {
        const newSelectedTask = { ...(selectedTask), [name]: value };
        setSelectedTask(newSelectedTask);
    };

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
            <ButtonGroup>
                <Button onClick={() => onSelectTaskFilter(TaskFilter.All)} active={taskFilter === TaskFilter.All}>All</Button>
                <Button onClick={() => onSelectTaskFilter(TaskFilter.Due)} active={taskFilter === TaskFilter.Due}>Due</Button>
            </ButtonGroup>
            <Button onClick={onNewTask} floated='right'>New task</Button>
            <TaskList tasks={tasks} onSelectTask={onEditTaskDetails} />
            <TaskEditor
                task={selectedTask}
                onChangeProperty={onChangeProperty}
                onSaveChanges={onSaveChanges} />
            {/* <pre>{JSON.stringify(tasks, null, 2)}</pre> */}
        </div>
    )
};

export default TasksPage;
