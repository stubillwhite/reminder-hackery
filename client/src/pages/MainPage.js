import React from 'react';
import { Tab } from 'semantic-ui-react';
import PingPage from './ping-page/PingPage';
import TasksPage from './tasks-page/TasksPage';

class MainPage extends React.Component {

    panes = [
        { menuItem: 'Tasks', render: () => <Tab.Pane><TasksPage /></Tab.Pane> },
        { menuItem: 'Ping', render: () => <Tab.Pane><PingPage /></Tab.Pane> },
    ]

    render() {
        return (
            <div className="ui container">
                <h1 className="ui centered header">Reminder hackery</h1>
                <div className="ui divider"></div>
                <Tab panes={this.panes} />
            </div >
        );
    }
}

export default MainPage;