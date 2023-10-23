import React from 'react';
import { BrowserRouter, NavLink, Route, Switch } from 'react-router-dom';
import { Menu, Tab } from 'semantic-ui-react';
import PingPage from './ping-page/PingPage';
import TasksPage from './tasks-page/TasksPage';

class MainPage extends React.Component {

    state = { activeItem: 'home' }

    handleItemClick = (e, { name }) => {
        this.setState({ activeItem: name })
    }

    renderNavBar = () => {
        return (
            <Menu>
                <Menu.Item
                    name='tasks'
                    as={NavLink} to="/tasks"
                    active={this.state.activeItem === 'tasks'}
                    onClick={this.handleItemClick}
                >
                    Tasks
                </Menu.Item>

                <Menu.Item
                    name='ping'
                    as={NavLink} to="/ping"
                    active={this.state.activeItem === 'ping'}
                    onClick={this.handleItemClick}
                >
                    Ping
                </Menu.Item>
            </Menu>
        );
    };

    render() {
        return (
            <div className="ui container">
                <BrowserRouter>
                    <this.renderNavBar />

                    <Switch>
                        <Route exact path="/">
                            <pre>Click on something!</pre>
                        </Route>
                        <Route path="/tasks">
                            <TasksPage />
                        </Route>
                        <Route path="/ping">
                            <PingPage />
                        </Route>
                    </Switch>
                </BrowserRouter>
            </div>
        );
    }
}

export default MainPage;
