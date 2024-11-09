// js/reservations.js
document.getElementById('reservationForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    try {
        const response = await fetch(`${API_URL}/reservations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify({
                vehicleId: formData.get('vehicleId'),
                startDate: formData.get('startDate'),
                endDate: formData.get('endDate')
            })
        });

        if (!response.ok) throw new Error('Error al crear la reserva');

        bootstrap.Modal.getInstance(document.getElementById('reservationModal')).hide();
        alert('Reserva creada exitosamente');
        loadUserReservations();
    } catch (error) {
        handleError(error);
    }
});

async function loadUserReservations() {
    try {
        const response = await fetch(`${API_URL}/reservations/user`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al cargar reservas');

        const reservations = await response.json();
        displayUserReservations(reservations);
    } catch (error) {
        handleError(error);
    }
}

function displayUserReservations(reservations) {
    const reservationsList = document.getElementById('reservationsList');
    reservationsList.innerHTML = '';

    reservations.forEach(reservation => {
        const card = document.createElement('div');
        card.className = 'card reservation-card';
        card.innerHTML = `
            <div class="card-body">
                <h5 class="card-title">${reservation.vehicle.brand} ${reservation.vehicle.model}</h5>
                <p class="card-text">
                    <strong>Fecha inicio:</strong> ${new Date(reservation.startDate).toLocaleDateString()}<br>
                    <strong>Fecha fin:</strong> ${new Date(reservation.endDate).toLocaleDateString()}<br>
                    <strong>Estado:</strong> ${reservation.status}<br>
                    <strong>Costo total:</strong> $${reservation.totalCost}
                </p>
                ${reservation.status === 'PENDING' ? `
                    <button class="btn btn-warning me-2" onclick="modifyReservation(${reservation.id})">
                        Modificar
                    </button>
                    <button class="btn btn-danger" onclick="cancelReservation(${reservation.id})">
                        Cancelar
                    </button>
                ` : ''}
            </div>
        `;
        reservationsList.appendChild(card);
    });
}

async function modifyReservation(reservationId) {
    // Implementar lógica para modificar reserva
    const newDates = prompt('Ingrese las nuevas fechas (inicio-fin)', 'YYYY-MM-DD,YYYY-MM-DD');
    if (!newDates) return;

    const [startDate, endDate] = newDates.split(',');

    try {
        const response = await fetch(`${API_URL}/reservations/${reservationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify({ startDate, endDate })
        });

        if (!response.ok) throw new Error('Error al modificar la reserva');

        alert('Reserva modificada exitosamente');
        loadUserReservations();
    } catch (error) {
        handleError(error);
    }
}

async function cancelReservation(reservationId) {
    if (!confirm('¿Está seguro de cancelar esta reserva?')) return;

    try {
        const response = await fetch(`${API_URL}/reservations/${reservationId}/cancel`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });

        if (!response.ok) throw new Error('Error al cancelar la reserva');

        alert('Reserva cancelada exitosamente');
        loadUserReservations();
    } catch (error) {
        handleError(error);
    }
}