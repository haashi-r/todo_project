import React, { useEffect, useState } from 'react';
import { fetchAllProjects } from '../services/api';
import { Link } from 'react-router-dom';

const ProjectList = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchAllProjects()
      .then((response) => {
        if (response.success) {
          setProjects(response.projects);
        } else {
          setError(response.error.message || 'Failed to load projects.');
        }
        setLoading(false);
      })
      .catch((error) => {
        setError("An error occurred while fetching projects.");
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>Projects</h1>
      {projects.length === 0 ? (
        <p>No projects available.</p>
      ) : (
        <ul>
          {projects.map((project) => (
            <li key={project.projectId}>
              <Link to={`/tasks/${project.projectId}`}>{project.title}</Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ProjectList;


