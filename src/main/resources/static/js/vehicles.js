// js/vehicles.js
async function loadVehicles(brand = '', model = '') {
    try {
        let url = `${API_URL}/vehicles`;
        if (brand || model) {
            url += `?brand=${brand}&model=${model}`;
        }

        const response = await fetch(url);
        if (!response.ok) throw new Error('Error al cargar vehículos');

        const vehicles = await response.json();
        displayVehicles(vehicles);
    } catch (error) {
        handleError(error);
    }
}

function displayVehicles(vehicles) {
    const vehiclesList = document.getElementById('vehiclesList');
    vehiclesList.innerHTML = '';

    vehicles.forEach(vehicle => {
        const card = document.createElement('div');
        card.className = 'col-md-4';
        card.innerHTML = `
            <div class="card vehicle-card">
                <div class="card-body">
                    <h5 class="card-title">${vehicle.brand} ${vehicle.model}</h5>
                    <p class="card-text">
                        <strong>Año:</strong> ${vehicle.year}<br>
                        <strong>Tipo:</strong> ${vehicle.type}<br>
                        <strong>Precio por día:</strong> $${vehicle.dailyRate}
                    </p>
                    ${vehicle.available ?
            `<button class="btn btn-primary reserve-btn" 
                         data-vehicle-id="${vehicle.id}" 
                         data-daily-rate="${vehicle.dailyRate}"
                         ${currentUser ? '' : 'disabled'}>
                            Reservar
                        </button>` :
            '<button class="btn btn-secondary" disabled>No disponible</button>'
        }
                </div>
            </div>
        `;

        const reserveBtn = card.querySelector('.reserve-btn');
        if (reserveBtn) {
            reserveBtn.addEventListener('click', () => showReservationModal(vehicle));
        }

        vehiclesList.appendChild(card);
    });
}

function showReservationModal(vehicle) {
    const modal = document.getElementById('reservationModal');
    const form = document.getElementById('reservationForm');
    form.elements['vehicleId'].value = vehicle.id;
    document.getElementById('dailyRate').textContent = `$${vehicle.dailyRate}`;

    // Reset dates and total cost
    form.elements['startDate'].value = '';
    form.elements['endDate'].value = '';
    document.getElementById('totalCost').textContent = '$0';

    // Add event listeners for date changes
    const updateTotalCost = () => {
        const startDate = new Date(form.elements['startDate'].value);
        const endDate = new Date(form.elements['endDate'].value);
        if (startDate && endDate && startDate <= endDate) {
            const days = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
            const total = days * vehicle.dailyRate;
            document.getElementById('totalCost').textContent = `$${total}`;
        }
    };

    form.elements['startDate'].addEventListener('change', updateTotalCost);
    form.elements['endDate'].addEventListener('change', updateTotalCost);

    new bootstrap.Modal(modal).show();
}

// Set minimum dates for reservation
document.querySelectorAll('input[type="date"]').forEach(input => {
    input.min = new Date().toISOString().split('T')[0];
});