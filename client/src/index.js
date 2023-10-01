import React from 'react';
import ReactDom from 'react-dom';
import App from './App';
import 'semantic-ui-css/semantic.min.css';

ReactDom.render(
  <React.StrictMode>
    <App />
    </React.StrictMode>,
    document.querySelector('#root')
);
