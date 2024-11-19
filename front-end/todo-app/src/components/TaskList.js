import React, { useEffect, useState } from 'react';
import { fetchTasksByProjectId } from '../services/api';
import { Link, useParams } from 'react-router-dom';

const TaskList = () => {
  const { projectId } = useParams();
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    fetchTasksByProjectId(projectId)
      .then((response) => setTasks(response.data.todos))
      .catch((error) => console.error(error));
  }, [projectId]);

  return (
    <div>
      <h1>Tasks</h1>
      <ul>
        {tasks.map((task) => (
          <li key={task.taskId}>{task.description}</li>
        ))}
      </ul>
      <Link to={`/add-task/${projectId}`}>Add Task</Link>
    </div>
  );
};

export default TaskList;
