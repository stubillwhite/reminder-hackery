import React, { useState } from 'react';
import { Button, Form } from 'semantic-ui-react';
import { createTask } from '../apis/Client';

const Task = ({ id, description }) => {

    const [state, setState] = useState({
        description: ''
    });

    const handleSaveTask = async () => {
        const task = { description: state.description, id: null};
        console.log('Saving task', task);
        
        const response = await createTask(task);
        console.log('Task created', response.data)

        setState({ description: '' })
    };

    const handleChangeDescription = (e) => {
        setState({ description: e.target.value })
    };

    return (
        <Form onSubmit={handleSaveTask}>
            <Form.Field>
                <label>Description</label>
                <input
                    placeholder='Description'
                    value={state.description}
                    onChange={e => handleChangeDescription(e)} />
            </Form.Field>
            <Button type='submit'>Save</Button>
        </Form>
    );
};

export default Task;
