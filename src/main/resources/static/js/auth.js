// js/auth.js
document.getElementById('loginBtn').addEventListener('click', () => {
    new bootstrap.Modal(document.getElementById('loginModal')).show();
});

document.getElementById('registerBtn').addEventListener('click', () => {
    new bootstrap.Modal(document.getElementById('registerModal')).show();
});

document.getElementById('logoutBtn').addEventListener('click', logout);

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: formData.get('email'),
                password: formData.get('password')
            })
        });

        if (!response.ok) throw new Error('Credenciales inválidas');

        const data = await response.json();
        localStorage.setItem('token', data.token);
        await fetchUserInfo();
        bootstrap.Modal.getInstance(document.getElementById('loginModal')).hide();
    } catch (error) {
        handleError(error);
    }
});

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    try {
        const response = await fetch(`${API_URL}/users/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: formData.get('name'),
                email: formData.get('email'),
                password: formData.get('password')
            })
        });

        if (!response.ok) throw new Error('Error en el registro');

        bootstrap.Modal.getInstance(document.getElementById('registerModal')).hide();
        alert('Registro exitoso. Por favor inicia sesión.');
    } catch (error) {
        handleError(error);
    }
});

function logout() {
    localStorage.removeItem('token');
    currentUser = null;
    updateUIForUser();
    showSection('homeSection');
}