// js/main.js
const API_URL = 'http://localhost:8080/api';
let currentUser = null;

// Utility functions
function showSection(sectionId) {
    document.querySelectorAll('.main-content > div').forEach(div => div.classList.add('hidden'));
    document.getElementById(sectionId).classList.remove('hidden');
}

function updateUIForUser() {
    const isAdmin = currentUser?.role === 'ADMIN';
    document.querySelectorAll('.admin-only').forEach(el => {
        el.classList.toggle('hidden', !isAdmin);
    });
    document.querySelectorAll('.user-only').forEach(el => {
        el.classList.toggle('hidden', !currentUser);
    });
    document.getElementById('authButtons').classList.toggle('hidden', !!currentUser);
    document.getElementById('userInfo').classList.toggle('hidden', !currentUser);
    if (currentUser) {
        document.getElementById('userName').textContent = currentUser.name;
    }
}

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    // Check if user is logged in
    const token = localStorage.getItem('token');
    if (token) {
        // Validate token and get user info
        fetchUserInfo();
    }

    // Navigation event listeners
    document.getElementById('homeLink').addEventListener('click', () => showSection('homeSection'));
    document.getElementById('vehiclesLink').addEventListener('click', () => {
        showSection('vehiclesSection');
        loadVehicles();
    });
    document.getElementById('myReservationsLink').addEventListener('click', () => {
        showSection('myReservationsSection');
        loadUserReservations();
    });
    document.getElementById('adminPanelLink').addEventListener('click', () => {
        showSection('adminSection');
        loadAdminDashboard();
    });

    // Search vehicles
    document.getElementById('searchVehiclesBtn').addEventListener('click', () => {
        const brand = document.getElementById('searchBrand').value;
        const model = document.getElementById('searchModel').value;
        loadVehicles(brand, model);
    });
});

// Error handling
function handleError(error) {
    console.error('Error:', error);
    // You could implement a toast notification system here
    alert(error.message || 'Ha ocurrido un error');
}

// API calls
async function fetchUserInfo() {
    try {
        const response = await fetch(`${API_URL}/users/me`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });
        if (!response.ok) throw new Error('Error al obtener información del usuario');
        currentUser = await response.json();
        updateUIForUser();
    } catch (error) {
        handleError(error);
        localStorage.removeItem('token');
        currentUser = null;
        updateUIForUser();
    }
}

// Initialize tooltips and popovers
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});