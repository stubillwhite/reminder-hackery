
import React from 'react';
import SemanticDatepicker from 'react-semantic-ui-datepickers';
import 'react-semantic-ui-datepickers/dist/react-semantic-ui-datepickers.css';
import { Button, Checkbox, Form, Modal } from 'semantic-ui-react';

const Task = (props) => {

    const onChangeProperty = (name, value) => {
        props.onChangeProperty(name, value);
    };

    const { task } = props;

    const convertEmptyToNull = (s) => s ? s : null;

    return (
        <Form onSubmit={props.onSaveChanges}>
            <Form.Field>
                <label>Description</label>
                <input
                    placeholder='Description'
                    value={task.description}
                    onChange={(e) => onChangeProperty('description', e.target.value)} />
            </Form.Field>
            <Form.Field>
                <label>Deadline</label>
                <SemanticDatepicker
                    value={new Date(task.deadline)}
                    onChange={(e, data) => onChangeProperty('deadline', data.value)} />
            </Form.Field>
            <Form.Field>
                <label>Complete</label>
                <Checkbox
                    checked={task.complete}
                    onChange={(e, data) => onChangeProperty('complete', data.checked)} />
            </Form.Field>
            <Form.Field>
                <label>Recurrence in days</label>
                <input
                    placeholder=''
                    value={task.recurrence}
                    onChange={(e) => onChangeProperty('recurrence', convertEmptyToNull(e.target.value))} />
            </Form.Field>
            <div />
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
                        onChangeProperty={props.onChangeProperty} />
                    <Button onClick={props.onSaveChanges}>OK</Button>
                    {/* <pre>{JSON.stringify(task, null, 2)}</pre> */}
                </Modal.Content>
            </Modal>
        );
    }
}

export default TaskEditor;