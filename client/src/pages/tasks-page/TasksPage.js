import React from 'react';
import { Card } from 'semantic-ui-react';
import { getTasks } from '../../apis/Client';

const renderTasks = (tasks) => {
    return tasks.map(task => {
        return (
            <Card>
                <Card.Content>
                    <Card.Header>{task.description}</Card.Header>
                </Card.Content>
            </Card>
        );
    })
}

const stubTasks = [
    { description: "task-1" },
    { description: "task-2" },
    { description: "task-3" },
]



const TasksPage = () => {
    const tasks = getTasks()

    return (
        <div>
            <Button>Refresh tasks</Button>
            {renderTasks(stubTasks)}
        </div>
    )
};

export default TasksPage;
