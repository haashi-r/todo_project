import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/project'; // Update with the correct backend URL if needed

// Fetch all projects
export const fetchAllProjects = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/get/all`);
    return response.data;
  } catch (error) {
    console.error("Error fetching projects:", error);
    throw error;
  }
};


// Create a new project
export const createProject = async (title) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/save`, { title });
    return response.data;
  } catch (error) {
    console.error("Error creating project:", error);
    throw error;
  }
};


// Edit project title
export const editProjectTitle = async (projectId, newTitle) => {
  try {
    const response = await axios.put(`${API_BASE_URL}/edit/title`, {
      projectId,
      title: newTitle,
    });
    return response.data;
  } catch (error) {
    console.error("Error updating project title:", error);
    throw error;
  }
};

// Create a new task
export const createTask = async (task) => {
    return axios.post(`${API_BASE_URL}/task/save`, task); // Matches @PostMapping("/task")
  };
  
  // Fetch tasks by project ID
  export const fetchTasksByProjectId = async (projectId) => {
    return axios.get(`${API_BASE_URL}/task/project/${projectId}`); 
    // Matches @GetMapping("/task/project/{projectId}")
  };
  
  // Update task status
  export const updateTaskStatus = async (taskId, status) => {
    return axios.put(`${API_BASE_URL}/task/${taskId}/status`, { status }); 
    // Matches @PutMapping("/task/{taskId}/status")
  };
  