// js/admin.js
// Event listeners for admin navigation
document.getElementById('manageCarsLink').addEventListener('click', () => {
    showAdminSection('adminVehicles');
    loadAdminVehicles();
});

document.getElementById('manageUsersLink').addEventListener('click', () => {
    showAdminSection('adminUsers');
    loadUsers();
});

document.getElementById('manageReservationsLink').addEventListener('click', () => {
    showAdminSection('adminReservations');
    loadAllReservations();
});

document.getElementById('reportsLink').addEventListener('click', () => {
    showAdminSection('adminReports');
    loadReports();
});

function showAdminSection(sectionId) {
    document.querySelectorAll('#adminSection > div').forEach(div => div.classList.add('hidden'));
    document.getElementById(sectionId).classList.remove('hidden');
}

// Vehicles Management
async function loadAdminVehicles() {
    try {
        const response = await fetch(`${API_URL}/vehicles/all`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al cargar vehículos');

        const vehicles = await response.json();
        displayAdminVehicles(vehicles);
    } catch (error) {
        handleError(error);
    }
}

function displayAdminVehicles(vehicles) {
    const vehiclesList = document.getElementById('adminVehiclesList');
    vehiclesList.innerHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Año</th>
                    <th>Tipo</th>
                    <th>Precio/Día</th>
                    <th>Disponible</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                ${vehicles.map(vehicle => `
                    <tr>
                        <td>${vehicle.id}</td>
                        <td>${vehicle.brand}</td>
                        <td>${vehicle.model}</td>
                        <td>${vehicle.year}</td>
                        <td>${vehicle.type}</td>
                        <td>$${vehicle.dailyRate}</td>
                        <td>${vehicle.available ? 'Sí' : 'No'}</td>
                        <td>
                            <button class="btn btn-sm btn-warning me-1" onclick="editVehicle(${vehicle.id})">
                                Editar
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="deleteVehicle(${vehicle.id})">
                                Eliminar
                            </button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

// Users Management
async function loadUsers() {
    try {
        const response = await fetch(`${API_URL}/users`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al cargar usuarios');

        const users = await response.json();
        displayUsers(users);
    } catch (error) {
        handleError(error);
    }
}

function displayUsers(users) {
    const usersList = document.getElementById('usersList');
    usersList.innerHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                ${users.map(user => `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>${user.active ? 'Activo' : 'Inactivo'}</td>
                        <td>
                            <button class="btn btn-sm btn-warning me-1" onclick="editUser(${user.id})">
                                Editar
                            </button>
                            <button class="btn btn-sm btn-${user.active ? 'danger' : 'success'}"
                                    onclick="toggleUserStatus(${user.id})">
                                ${user.active ? 'Desactivar' : 'Activar'}
                            </button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

// Reports
async function loadReports() {
    try {
        const [availabilityData, reservationsData] = await Promise.all([
            fetch(`${API_URL}/reports/availability`).then(res => res.json()),
            fetch(`${API_URL}/reports/reservations`).then(res => res.json())
        ]);

        displayAvailabilityChart(availabilityData);
        displayReservationsChart(reservationsData);
    } catch (error) {
        handleError(error);
    }
}

function displayAvailabilityChart(data) {
    const ctx = document.getElementById('availabilityChart').getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Disponibles', 'Reservados'],
            datasets: [{
                data: [data.available, data.reserved],
                backgroundColor: ['#28a745', '#dc3545']
            }]
        }
    });
}

function displayReservationsChart(data) {
    const ctx = document.getElementById('reservationsChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: data.map(d => d.month),
            datasets: [{
                label: 'Reservas por mes',
                data: data.map(d => d.count),
                backgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// CRUD Operations for Vehicles
async function editVehicle(vehicleId) {
    // Implementar lógica para editar vehículo
}

async function deleteVehicle(vehicleId) {
    if (!confirm('¿Está seguro de eliminar este vehículo?')) return;

    try {
        const response = await fetch(`${API_URL}/vehicles/${vehicleId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al eliminar el vehículo');

        alert('Vehículo eliminado exitosamente');
        loadAdminVehicles();
    } catch (error) {
        handleError(error);
    }
}

// User Management
async function editUser(userId) {
    // Implementar lógica para editar usuario
}

async function toggleUserStatus(userId) {
    try {
        const response = await fetch(`${API_URL}/users/${userId}/toggle-status`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al cambiar el estado del usuario');

        loadUsers();
    } catch (error) {
        handleError(error);
    }
}