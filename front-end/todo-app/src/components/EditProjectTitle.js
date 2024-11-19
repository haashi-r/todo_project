import React, { useState } from 'react';
import { editProjectTitle } from '../services/api';

const EditProject = ({ projectId, currentTitle }) => {
  const [title, setTitle] = useState(currentTitle);
  const [message, setMessage] = useState(null);

  const handleUpdate = async () => {
    try {
      const response = await editProjectTitle(projectId, title);
      if (response.success) {
        setMessage('Title updated successfully!');
      } else {
        setMessage(response.error.message || 'Failed to update title.');
      }
    } catch (error) {
      setMessage('An error occurred while updating the title.');
    }
  };

  return (
    <div>
      <input
        type="text"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <button onClick={handleUpdate}>Update Title</button>
      {message && <p>{message}</p>}
    </div>
  );
};

export default EditProject;


