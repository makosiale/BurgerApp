let burgers = [];
let availableBurgers = [];
let orders = [];

const positionsContainer = document.getElementById('positions');
const orderList = document.getElementById('orderList');

async function fetchBurgers() {
    try {
        const response = await fetch('/api/user/burgers');
        burgers = await response.json();
        availableBurgers = [...burgers];
        updateDropdowns();
    } catch (error) {
        console.error('Failed to fetch burgers:', error);
    }
}

async function fetchOrders(currentUser) {
    try {
        const currentUser = window.currentUser;
        const response = await fetch(`/api/user/order?username=${currentUser}`);
        orders = await response.json();
        renderOrders();
    } catch (error) {
        console.error('failed to fetch orders:', error);
    }
}

function updateDropdowns() {
    const selectedValues = Array.from(document.querySelectorAll('.burger-dropdown'))
        .map(dd => parseInt(dd.value) || null);

    availableBurgers = burgers.filter(burger => !selectedValues.includes(burger.id));

    const dropdowns = document.querySelectorAll('.burger-dropdown');
    dropdowns.forEach(dropdown => {
        const currentValue = parseInt(dropdown.value) || null;
        dropdown.innerHTML = '';

        burgers.forEach(burger => {
            if (!selectedValues.includes(burger.id) || burger.id === currentValue) {
                const option = document.createElement('option');
                option.value = burger.id;
                option.textContent = burger.name;
                dropdown.appendChild(option);
            }
        });

        dropdown.value = currentValue;
    });
}

document.getElementById('addPosition').addEventListener('click', () => {
    if (document.querySelectorAll('.burger-select').length >= burgers.length) {
        alert('No more burgers available.');
        return;
    }

    const positionDiv = document.createElement('div');
    positionDiv.className = 'burger-select';
    positionDiv.innerHTML = `
        <select class="burger-dropdown"></select>
        <input type="number" min="1" value="1" class="burger-quantity" placeholder="Quantity">
        <button class="remove-position">Remove</button>
    `;

    positionsContainer.appendChild(positionDiv);
    updateDropdowns();

    positionDiv.querySelector('.remove-position').addEventListener('click', () => {
        positionDiv.remove();
        updateDropdowns();
    });

    positionDiv.querySelector('.burger-dropdown').addEventListener('change', updateDropdowns);
    positionDiv.querySelector('.burger-dropdown').dispatchEvent(new Event('change'));
});

// const currentUser = "[[${#authentication.name}]]";
window.currentUser = document.querySelector('h1 span').innerText.trim();

document.getElementById('submitOrder').addEventListener('click', async () => {
    const positions = Array.from(document.querySelectorAll('.burger-select')).map(position => {
        const burgerId = parseInt(position.querySelector('.burger-dropdown').value);
        const quantity = parseInt(position.querySelector('.burger-quantity').value);
        return {burgerId, quantity};
    });

    if (positions.length === 0) {
        alert('Add at least one burger to the order.');
        return;
    }

    try {
        const response = await fetch('/api/user/order', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                username: currentUser,
                positions
            })
        });

        if (response.ok){
            alert("Order created successfully!");
            positionsContainer.innerHTML = '';
            availableBurgers = [...burgers];
            updateDropdowns();
            fetchOrders();
        }else{
            const errorMessage = await response.text();
            alert(`Error:${errorMessage}`);
        }
    } catch (error) {
        console.error('Failed to submit order:', error);
    }
});

function renderOrders() {
    orderList.innerHTML = '';
    if (orders.length === 0) {
        orderList.innerHTML = '<p>No orders yet.</p>';
    } else {
        orders.forEach((order, index) => {
            const orderDiv = document.createElement('div');
            orderDiv.className = 'order';
            orderDiv.innerHTML = `
            <h3>Order ${index + 1}</h3>
            <ul>
              ${order.positions.map(position => `<li>${position.burgerName} x ${position.quantity}</li>`).join('')}
            </ul>
          `;
            orderList.appendChild(orderDiv);
        });
    }
}

document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach(t => t
            .classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(tc => tc.classList.remove('active'));

        tab.classList.add('active');
        document.getElementById(tab.dataset.tab).classList.add('active');
    });
});

document.getElementById('logoutBtn').addEventListener('click', () => {
    // You can add the logout functionality here
    alert('You have logged out.');
    // Redirect to login page or perform other actions if necessary
    window.location.href = '/'; // Redirect example
});


/*window.currentUserRole = "${#authentication.authorities.?[authority == 'ROLE_ADMIN'].size() > 0 ? 'admin' : 'user'}";*/

if ("${#authentication.authorities.?[authority == 'ROLE_ADMIN'].size() > 0}") {
    document.getElementById('admin-favicon').style.display = 'block';  // Показываем иконку для админа
} else {
    document.getElementById('user-favicon').style.display = 'block';   // Показываем иконку для пользователя
}

// Initialize data
fetchBurgers();
fetchOrders(currentUser);