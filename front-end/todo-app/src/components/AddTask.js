import React, { useState } from 'react';
import { createTask } from '../services/api';
import { useNavigate, useParams } from 'react-router-dom';

const AddTask = () => {
  const { projectId } = useParams();
  const [description, setDescription] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    createTask({ description, projectId })
      .then(() => navigate(`/tasks/${projectId}`))
      .catch((error) => console.error(error));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Add Task</h1>
      <input
        type="text"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder="Task Description"
      />
      <button type="submit">Add</button>
    </form>
  );
};

export default AddTask;
