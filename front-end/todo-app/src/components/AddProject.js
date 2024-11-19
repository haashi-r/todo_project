import React, { useState } from 'react';
import { createProject } from '../services/api';

const AddProject = () => {
  const [title, setTitle] = useState('');
  const [message, setMessage] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await createProject(title);
      if (response.success) {
        setMessage('Project created successfully!');
        setTitle('');
      } else {
        setMessage(response.error.message || 'Failed to create project.');
      }
    } catch (error) {
      setMessage('An error occurred while creating the project.');
    }
  };

  return (
    <div>
      <h1>Add Project</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Enter project title"
        />
        <button type="submit">Create</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AddProject;


