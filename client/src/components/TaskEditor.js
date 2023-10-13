
import React from 'react';
import { Button, Form, Modal } from 'semantic-ui-react';

const Task = (props) => {

    const onChangeProperty = (name, value) => {
        props.onChangeProperty(name, value);
    };

    const { task } = props;

    return (
        <Form onSubmit={props.onSaveChanges}>
            <Form.Field>
                <label>Description</label>
                <input
                    placeholder='Description'
                    value={task.description}
                    onChange={(e) => onChangeProperty('description', e.target.value)} />
            </Form.Field>
        </Form>
    );
};

const TaskEditor = (props) => {

    const task = props.task;

    if (task === null) {
        return (
            <div />
        );
    }
    else {
        return (
            <Modal open={true}>
                <Modal.Content>
                    <Task
                        task={task} 
                        onChangeProperty={props.onChangeProperty}/>
                    <Button onClick={props.onSaveChanges}>OK</Button>
                    <pre>{JSON.stringify(task, null, 2)}</pre>
                </Modal.Content>
            </Modal>
        );
    }
}

export default TaskEditor;