import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ProjectList from './components/ProjectList';
import AddProject from './components/AddProject';
import EditProjectTitle from './components/EditProjectTitle';
import TaskList from './components/TaskList';
import AddTask from './components/AddTask';

const App = () => {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Projects</Link>
            </li>
            <li>
              <Link to="/add-project">Add Project</Link>
            </li>
          </ul>
        </nav>
        <Routes>
          <Route path="/" element={<ProjectList />} />
          <Route path="/add-project" element={<AddProject />} />
          <Route path="/edit-project" element={<EditProjectTitle />} />
          <Route path="/tasks/:projectId" element={<TaskList />} />
          <Route path="/add-task/:projectId" element={<AddTask />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
