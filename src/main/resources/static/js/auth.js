const AUTH_KEYS = {
    token: 'tracking_token',
    id: 'tracking_user_id',
    name: 'tracking_user_name',
    email: 'tracking_user_email',
    role: 'tracking_user_role'
};

const ROLE_ROUTES = {
    SUPER_ADMIN: '/super-admin/dashboard',
    ADMIN: '/admin/dashboard',
    TRAINER: '/trainer/dashboard',
    STUDENT: '/student/dashboard'
};

const ROLE_API_PREFIX = {
    SUPER_ADMIN: '/api/super-admin',
    ADMIN: '/api/admin',
    TRAINER: '/api/trainer',
    STUDENT: '/api/students'
};

function saveAuth(data) {
    localStorage.setItem(AUTH_KEYS.token, data.token);
    localStorage.setItem(AUTH_KEYS.id, data.id);
    localStorage.setItem(AUTH_KEYS.name, data.name);
    localStorage.setItem(AUTH_KEYS.email, data.email);
    localStorage.setItem(AUTH_KEYS.role, data.role);
}

function getToken() {
    return localStorage.getItem(AUTH_KEYS.token);
}

function getUser() {
    return {
        id: localStorage.getItem(AUTH_KEYS.id),
        name: localStorage.getItem(AUTH_KEYS.name),
        email: localStorage.getItem(AUTH_KEYS.email),
        role: localStorage.getItem(AUTH_KEYS.role)
    };
}

function getApiPrefix() {
    return ROLE_API_PREFIX[getUser().role] || '';
}

function logout() {
    Object.values(AUTH_KEYS).forEach(key => localStorage.removeItem(key));
    window.location.href = '/login';
}

function requireAuth(expectedRole) {
    const token = getToken();
    const user = getUser();

    if (!token || !user.role) {
        window.location.href = '/login';
        return false;
    }

    if (expectedRole && user.role !== expectedRole) {
        window.location.href = ROLE_ROUTES[user.role] || '/login';
        return false;
    }

    return true;
}

function initSidebar(role) {
    const user = getUser();
    const nameEl = document.getElementById('sidebar-user-name');
    const emailEl = document.getElementById('sidebar-user-email');
    if (nameEl) nameEl.textContent = user.name || 'User';
    if (emailEl) emailEl.textContent = user.email || '';

    const path = window.location.pathname;
    document.querySelectorAll('.sidebar-nav a').forEach(link => {
        if (link.getAttribute('href') === path) {
            link.classList.add('active');
        }
    });

    const toggle = document.getElementById('sidebar-toggle');
    const sidebar = document.querySelector('.sidebar');
    if (toggle && sidebar) {
        toggle.addEventListener('click', () => sidebar.classList.toggle('open'));
    }
}

function showAlert(message, type = 'success') {
    const container = document.getElementById('alert-container');
    if (!container) return;

    const alert = document.createElement('div');
    alert.className = `alert alert-${type} alert-dismissible fade show shadow-sm`;
    alert.innerHTML = `${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;
    container.appendChild(alert);

    setTimeout(() => alert.remove(), 5000);
}

function formatDate(dateStr) {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleDateString('en-IN', {
        year: 'numeric', month: 'short', day: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
}

function statusBadge(status) {
    const colors = {
        CREATED: 'secondary', ASSIGNED: 'primary', CLOSED: 'dark',
        PENDING: 'warning', SUBMITTED: 'info', EVALUATED: 'success', LATE: 'danger'
    };
    const color = colors[status] || 'secondary';
    return `<span class="badge bg-${color} badge-status">${status || '-'}</span>`;
}

function userStatusBadge(active) {
    const isActive = active === true;
    return `<span class="badge bg-${isActive ? 'success' : 'secondary'} badge-status">${isActive ? 'Active' : 'Inactive'}</span>`;
}

function isUserActive(active) {
    return active === true;
}

function filterByUserStatus(users, statusFilter) {
    if (statusFilter === 'active') return users.filter(u => u.active === true);
    if (statusFilter === 'inactive') return users.filter(u => u.active !== true);
    return users;
}

function toDateTimeLocal(dateStr) {
    if (!dateStr) return '';
    const d = new Date(dateStr);
    const pad = n => String(n).padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

function fromDateTimeLocal(value) {
    if (!value) return null;
    return value.length === 16 ? value + ':00' : value;
}

function todayMinDateTimeLocal() {
    const d = new Date();
    const pad = n => String(n).padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T00:00`;
}

function setupAssignmentDates() {
    const assignedEl = document.getElementById('assign-date');
    const dueEl = document.getElementById('assign-due');
    if (!assignedEl || !dueEl) return;

    assignedEl.min = todayMinDateTimeLocal();
    assignedEl.onchange = () => {
        dueEl.min = assignedEl.value || assignedEl.min;
        if (dueEl.value && assignedEl.value && dueEl.value <= assignedEl.value) {
            dueEl.value = '';
        }
    };
    assignedEl.onchange();
}

function validateAssignmentDates(assigned, due) {
    if (assigned < todayMinDateTimeLocal()) {
        return 'Assigned date cannot be in the past';
    }
    if (!due || due <= assigned) {
        return 'Due date must be after assigned date';
    }
    return null;
}

function getTrainerName(trainerId, trainers) {
    const trainer = (trainers || []).find(t => t.id === trainerId);
    return trainer ? trainer.name : (trainerId ? `Trainer #${trainerId}` : '-');
}

function formatAssignedStudents(students) {
    if (!students || !students.length) {
        return '<span class="text-muted small">None assigned</span>';
    }
    return students.map(s =>
        `<span class="badge bg-light text-dark border student-badge" title="${s.email || ''}">${s.name}</span>`
    ).join(' ');
}

function assignmentSearchText(a, trainers) {
    const studentNames = (a.assignedStudents || []).map(s => `${s.name} ${s.email || ''}`).join(' ');
    return `${a.title} ${getTrainerName(a.trainerId, trainers)} ${a.status || ''} ${a.maxMarks} ${studentNames}`;
}

function filterItems(items, query, getText) {
    if (!query || !query.trim()) return items;
    const q = query.toLowerCase().trim();
    return items.filter(item => getText(item).toLowerCase().includes(q));
}

function initSearch(inputId, onSearch) {
    const input = document.getElementById(inputId);
    if (!input) return;
    input.addEventListener('input', () => onSearch(input.value));

    document.querySelectorAll(`.btn-clear-search[data-target="${inputId}"]`).forEach(btn => {
        btn.addEventListener('click', () => {
            input.value = '';
            onSearch('');
            input.focus();
        });
    });
}

function bindTableSearch(inputId, tbodyId) {
    initSearch(inputId, () => filterTableRows(tbodyId, document.getElementById(inputId)?.value || ''));
}

function filterTableRows(tbodyId, query) {
    const tbody = document.getElementById(tbodyId);
    if (!tbody) return;
    const q = (query || '').toLowerCase().trim();
    let visible = 0;
    tbody.querySelectorAll('tr').forEach(row => {
        const isPlaceholder = row.cells.length === 1 && row.cells[0].colSpan > 1;
        if (isPlaceholder) return;
        const show = !q || row.textContent.toLowerCase().includes(q);
        row.style.display = show ? '' : 'none';
        if (show) visible++;
    });
    return visible;
}
