async function apiRequest(method, url, body) {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token)
        headers['Authorization'] = 'Bearer ' + token;

    const options = { method, headers };
    if (body !== undefined) options.body = JSON.stringify(body);

    const response = await fetch(url, options);
    let data;
    try {
        data = await response.json();
    } catch {
        throw new Error('Invalid server response');
    }

    if (!response.ok) {
        const msg = data.msg || data.message || data.error || 'Request failed';
        throw new Error(msg);
    }

    return data;
}

async function login(role, email, password) {
    const endpoints = {
        SUPER_ADMIN: '/api/super-admin/login',
        ADMIN: '/api/admin/login',
        TRAINER: '/api/trainer/login',
        STUDENT: '/api/students/login'
    };
    const result = await apiRequest('POST', endpoints[role], { email, password });
    return result.data;
}

async function resetPassword(newPassword) {
    const prefix = getApiPrefix();
    return apiRequest('PUT', prefix + '/reset-password', { newPassword });
}

// Super Admin APIs
const SuperAdminApi = {
    dashboard: () => apiRequest('GET', '/api/super-admin/dashboard'),
    getAdmins: () => apiRequest('GET', '/api/super-admin/viewAllAdmins'),
    getAdmin: (id) => apiRequest('GET', `/api/super-admin/getAdminById/${id}`),
    createAdmin: (body) => apiRequest('POST', '/api/super-admin/createAdmin', body),
    updateAdmin: (id, body) => apiRequest('PUT', `/api/super-admin/updateAdminById/${id}`, body),
    deleteAdmin: (id) => apiRequest('DELETE', `/api/super-admin/deleteAdminById/${id}`),
    getTrainers: () => apiRequest('GET', '/api/super-admin/getAllTrainers'),
    getStudents: () => apiRequest('GET', '/api/super-admin/getAllStudents'),
    getAssignments: () => apiRequest('GET', '/api/super-admin/getAllAssignments'),
    getSubmissions: () => apiRequest('GET', '/api/super-admin/viewAllSubmissions')
};

// Admin APIs
const AdminApi = {
    getTrainers: () => apiRequest('GET', '/api/admin/getAlltrainers'),
    getStudents: () => apiRequest('GET', '/api/admin/getAllstudents'),
    createTrainer: (body) => apiRequest('POST', '/api/admin/createTrainer', body),
    createStudent: (body) => apiRequest('POST', '/api/admin/createStudent', body),
    updateUser: (id, body) => apiRequest('PUT', `/api/admin/updateTrainerOrStudentById/${id}`, body),
    createAssignment: (body) => apiRequest('POST', '/api/admin/createAssignments', body),
    getAllAssignments: () => apiRequest('GET', '/api/admin/getAllAssignments'),
    getAssignment: (id) => apiRequest('GET', `/api/admin/getAssignmentDetailsById/${id}`),
    closeAssignment: (id) => apiRequest('DELETE', `/api/admin/closeAssignment/${id}`),
    assignAssignment: (assignmentId, studentIds) => apiRequest('POST', `/api/admin/assignAssignment/${assignmentId}`, studentIds),
    getSubmissions: (assignmentId) => apiRequest('GET', `/api/admin/getStudentSubmissions/${assignmentId}`),
    deleteTrainer: (id) => apiRequest('DELETE', `/api/admin/trainers/${id}`),
    deleteStudent: (id) => apiRequest('DELETE', `/api/admin/students/${id}`)
};

// Trainer APIs
const TrainerApi = {
    getAssignments: () => apiRequest('GET', '/api/trainer/getAssignedAndCreatedAssignments'),
    createAssignment: (body) => apiRequest('POST', '/api/trainer/createAssignments', body),
    updateAssignment: (id, body) => apiRequest('PUT', `/api/trainer/updateAssignment/${id}`, body),
    assignAssignment: (assignmentId, studentIds) => apiRequest('POST', `/api/trainer/assignAssignment/${assignmentId}`, studentIds),
    getStudents: () => apiRequest('GET', '/api/trainer/getAllstudents'),
    getSubmissions: (assignmentId) => apiRequest('GET', `/api/trainer/getStudentSubmissions/${assignmentId}`),
    evaluateSubmission: (submissionId, body) => apiRequest('PUT', `/api/trainer/evaluateSubmission/${submissionId}`, body),
    changeSubmissionStatus: (submissionId, status) => apiRequest('PUT', `/api/trainer/changeSubmissionStatus/${submissionId}?status=${status}`)
};

// Student APIs
const StudentApi = {
    getAssignments: (studentId) => apiRequest('GET', `/api/students/getMyAssignments/${studentId}/assignments`),
    getAssignment: (studentId, assignmentId) => apiRequest('GET', `/api/students/getAssignmentDetails/${studentId}/assignments/${assignmentId}`),
    submitAssignment: (studentId, assignmentId, body) => apiRequest('POST', `/api/students/submitAssignment/${studentId}/assignments/${assignmentId}/submissions`, body),
    updateSubmission: (studentId, assignmentId, body) => apiRequest('PUT', `/api/students/updateSubmission/${studentId}/assignments/${assignmentId}/submission`, body),
    getSubmissions: (studentId) => apiRequest('GET', `/api/students/getAllMySubmissions/${studentId}/submissions`),
    getSubmissionStatus: (studentId, assignmentId) => apiRequest('GET', `/api/students/getSubmissionStatus/${studentId}/assignments/${assignmentId}/submission/status`),
    getSubmissionResult: (studentId, assignmentId) => apiRequest('GET', `/api/students/getSubmissionResult/${studentId}/assignments/${assignmentId}/result`)
};
