let ingredients = [];
let orders = [];
let availableIngredients = [];

async function fetchIngredients() {
    try {
        const response = await fetch('/api/admin/ingredients');
        ingredients = await response.json();
        availableIngredients = [...ingredients];
        renderIngredients();
        updateIngredientDropdowns();
    } catch (error) {
        console.error('Failed to fetch ingredients:', error);
    }
}

async function fetchOrders() {
    try {
        const response = await fetch('/api/admin/orders');
        orders = await response.json();
        renderOrders();
    } catch (error) {
        console.error('Failed to fetch orders:', error);
    }
}

function renderIngredients() {
    const ingredientList = document.getElementById('ingredientList');
    ingredientList.innerHTML = '';
    if (ingredients.length === 0) {
        ingredientList.innerHTML = '<p>No ingredients found.</p>';
    } else {
        ingredients.forEach(ingredient => {
            const div = document.createElement('div');
            div.className = 'item';
            div.innerHTML = `
                <div>${ingredient.name} - Residue: ${ingredient.residue}</div>
                <button class="supply-button" data-id="${ingredient.id}">Supply</button>
            `;
            ingredientList.appendChild(div);

            // Обработчик для кнопки "Supply"
            div.querySelector('.supply-button').addEventListener('click', () => {
                const newResidue = prompt('Enter the quantity to supply:');
                if (newResidue && !isNaN(newResidue) && parseInt(newResidue) > 0) {
                    supplyIngredient(ingredient.id, parseInt(newResidue));
                } else {
                    alert('Please enter a valid quantity.');
                }
            });
        });
    }
}

async function supplyIngredient(ingredientId, quantity) {
    try {
        const response = await fetch(`admin/ingredients/supply/${ingredientId}`, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({supplyQuantity: quantity})
        });
        if (response.ok) {
            const message = await response.text();
            showSuccessMessage("Supply was successful");
        }
        fetchIngredients();  // Перезагружаем ингредиенты, чтобы отобразить обновленный остаток
    } catch (error) {
        console.error('Failed to supply ingredient:', error);
        showErrorMessage('An error occurred while sending the request. Try again.')
    }
}

function showSuccessMessage(message) {
    alert(`Успех: ${message}`);
}

function showErrorMessage(error) {
    alert(`Ошибка: ${error}`);
}

document.getElementById('submitIngredient').addEventListener('click', async () => {
    const name = document.getElementById('ingredientName').value;
    const residue = parseInt(document.getElementById('ingredientResidue').value);

    if (!name || residue <= 0) {
        alert('Please provide valid ingredient details.');
        return;
    }

    try {
        const response = await fetch('admin/ingredients/newIngredient', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({name, residue})
        });
        if (response.ok) {
            const message = await response.text();
            showSuccessMessage(message);
        } else {
            const error = await response.text();
            showErrorMessage(error.message);
        }
    } catch (error) {
        console.error('Failed to add ingredient:', error);
        showErrorMessage('An error occurred while adding an ingredient.');
    }
});



document.getElementById('submitBurger').addEventListener('click', async () => {
    const name = document.querySelector('#burgerName');
    const cost = document.querySelector('#cost');
    const dropdowns = document.querySelectorAll('.ingredient-dropdown');
    const ingredients = Array.from(document.querySelectorAll('.select-group')).map(group => {
        const ingredientId = parseInt(group.querySelector('.ingredient-dropdown').value);
        const quantity = parseInt(group.querySelector('.ingredient-quantity').value);
        return { ingredientId, quantity };
    });

    // Проверки на заполненность
    if (!name.value.trim()) {
        alert('Please enter a burger name.');
        return;
    }
    if (!cost.value || isNaN(parseFloat(cost.value))) {
        alert('Please enter a valid cost.');
        return;
    }
    if (ingredients.some(ing => !ing.ingredientId || isNaN(ing.quantity) || ing.quantity <= 0)) {
        alert('Please select an ingredient and specify a valid quantity for each.');
        return;
    }
    if (Array.from(dropdowns).some(dd => dd.value === '')) {
        alert('Please select an ingredient for each dropdown.');
        return;
    }

    try {
        await fetch('/api/admin/burgers/newBurger', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                name: name.value,
                cost: parseFloat(cost.value),
                ingredients
            })
        });
        alert('Burger created successfully!');
    } catch (error) {
        alert('Failed to create the burger. Please try again.');
        console.error('Failed to create burger:', error);
    }
});



document.getElementById('addIngredient').addEventListener('click', () => {
    if (document.querySelectorAll('.select-group').length >= ingredients.length) {
        alert('No more ingredients available.');
        return;
    }

    const div = document.createElement('div');
    div.className = 'select-group';
    div.innerHTML = `
        <select class="ingredient-dropdown"></select>
        <input type="number" class="ingredient-quantity" min="1" placeholder="Quantity for Burger">
        <button class="remove-ingredient">Remove</button>
    `;

    document.getElementById('burgerIngredients').appendChild(div);
    updateIngredientDropdowns();

    div.querySelector('.remove-ingredient').addEventListener('click', () => {
        div.remove();
        updateIngredientDropdowns();
    });

    div.querySelector('.ingredient-dropdown').addEventListener('change', updateIngredientDropdowns);
});

function updateIngredientDropdowns() {
    const dropdowns = document.querySelectorAll('.ingredient-dropdown');

    // Список выбранных значений
    const selectedValues = Array.from(dropdowns)
        .map(dd => parseInt(dd.value))
        .filter(val => !isNaN(val)); // Убираем пустые значения

    dropdowns.forEach(dropdown => {
        const currentValue = parseInt(dropdown.value) || null; // Текущее выбранное значение
        dropdown.innerHTML = ''; // Очищаем выпадающий список

        // Опция по умолчанию
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.textContent = 'Select an Ingredient';
        dropdown.appendChild(defaultOption);

        // Добавляем доступные ингредиенты
        ingredients.forEach(ingredient => {
            if (!selectedValues.includes(ingredient.id) || ingredient.id === currentValue) {
                const option = document.createElement('option');
                option.value = ingredient.id;
                option.textContent = `${ingredient.name} - Residue: ${ingredient.residue}`;
                dropdown.appendChild(option);
            }
        });

        // Восстанавливаем текущее значение
        if (currentValue !== null) {
            dropdown.value = currentValue;
        }
    });

}



function renderOrders() {
    const orderList = document.getElementById('orderList');
    orderList.innerHTML = '';

    if (orders.length === 0) {
        orderList.innerHTML = '<p>No orders found.</p>';
    } else {
        orders.forEach(order => {
            const div = document.createElement('div');
            div.className = 'item';

            // Формируем HTML для одного заказа
            div.innerHTML = `
                <div>Order ID: ${order.orderId}</div>
                <div>Customer: ${order.username}</div>
                <div>Employee: ${order.employee}</div>
                <div>Burgers:</div>
                <ul>
                    ${order.burgers.map(b => `<li>${b.name} - ${b.quantity}</li>`).join('')}
                </ul>
                <div>Total Price: ${order.totalPrice.toFixed(2)} руб.</div>
                <button class='delete-order-button' id="delete-order-button" data-order-id="${order.orderId}">Delete order</button>
            `;

            orderList.appendChild(div);
        });
    }

    addDeleteOrderHandlers();
}

function addDeleteOrderHandlers(){
    const deleteButtons = document.querySelectorAll('.delete-order-button');
    deleteButtons.forEach(button =>{
        button.addEventListener('click',async function(){
            const orderId = this.dataset.orderId;

            if (confirm(`Are you sure you want to delete the order with ID ${orderId} ?`)){
                try {
                    const response = await fetch(`/api/admin/orders/delete-order?orderId=${orderId}`,{
                        method: 'DELETE',
                    });
                    if (response.ok){
                        alert("The order was successfully deleted");
                        fetchOrders();
                    }else{
                        alert("Error when deleting an order.");
                    }
                }catch(error){
                    console.error("Error when deleting an order",error);
                    alert("Failed to delete order");
                }
            }
        })
    })
}


document.addEventListener("DOMContentLoaded", function () {
    async function fetchEmployees() {
        try {
            const response = await fetch("/api/admin/employees");
            if (!response.ok) {
                throw new Error('Network error');
            }
            return await response.json();
        } catch (error) {
            console.error("Error loading employees:", error);
            return [];
        }
    }

    async function getTotalRevenue() {
        try {
            const response = await fetch("/api/admin/orders/total-revenue");
            if (!response.ok) {
                throw new Error('Error getting total revenue');
            }
            const data = await response.json();
            document.getElementById('revenueAmount').textContent = data.totalRevenue;
        } catch (error) {
            console.error("Error receiving revenue:", error);
            document.getElementById('revenueAmount').textContent = 'Ошибка';
        }
    }

    // Обработчик для кнопки получения общей выручки
    document.getElementById('getTotalRevenueBtn').addEventListener("click", getTotalRevenue);

    async function renderEmployeeAssignment() {
        const employeeOrderList = document.getElementById('employeeOrderList');
        employeeOrderList.innerHTML = '';

        if (orders.length === 0) {
            employeeOrderList.innerHTML = '<p>No orders found.</p>';
            return;
        }

        // Создаем элементы заказов
        orders.forEach(order => {
            const div = document.createElement('div');
            div.className = 'item';
            div.innerHTML = `
                <div>Order ID: ${order.orderId}</div>
                <div>Customer: ${order.username}</div>
                <div>Employee:
                    <select id="employee-select-${order.orderId}" class="form-select">
                        <option value="">Выберите сотрудника</option>
                    </select>
                </div>
                <button class="assign-employee-btn"  data-order-id="${order.orderId}">Assign Employee</button>
            `;
            employeeOrderList.appendChild(div);
        });

        // Загружаем сотрудников и заполняем селекторы
        const employees = await fetchEmployees();
        const employeeSelectElements = document.querySelectorAll(".form-select");
        employeeSelectElements.forEach(select => {
            employees.forEach(employee => {
                const option = document.createElement("option");
                option.value = employee.id;
                option.textContent = employee.name;
                select.appendChild(option);
            });
        });

        // Обработчик для кнопок закрепления
        const assignButtons = document.querySelectorAll(".assign-employee-btn");
        assignButtons.forEach(button => {
            button.addEventListener("click", function () {
                const orderId = this.dataset.orderId;
                const selectElement = document.querySelector(`#employee-select-${orderId}`);
                const employeeId = selectElement.value;

                if (!employeeId) {
                    alert("Select an employee before confirming!");
                    return;
                }

                // Отправляем данные на сервер
                fetch(`/api/admin/orders/${orderId}/assign`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({employeeId})
                })
                    .then(response => {
                        if (response.ok) {
                            alert("The employee is successfully assigned to the order!");
                            /*                                location.reload(); // Обновляем страницу*/
                            fetchOrders()

                        } else {
                            alert("Error when assigning an employee.");
                        }
                    })
                    .catch(error => console.error("Request error:", error));
            });
        });
    }

    // Вызов функции для рендеринга заказов и сотрудников
    fetchOrders().then(renderEmployeeAssignment);
});


document.getElementById('logoutBtn').addEventListener('click', function () {
    // Display logout alert
    alert('You have logged out');
    // Redirect to the home page
    window.location.href = '/';
});

// Event listener for tab switching
const tabs = document.querySelectorAll('.tab');
tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        tabs.forEach(t => t.classList.remove('active'));
        tab.classList.add('active');

        const activeTab = tab.getAttribute('data-tab');
        const contents = document.querySelectorAll('.tab-content');
        contents.forEach(content => {
            if (content.id === activeTab) {
                content.classList.add('active');
            } else {
                content.classList.remove('active');
            }
        });

        // Fetch data for the current tab
        if (activeTab === 'view-ingredients') {
            fetchIngredients();
        } else if (activeTab === 'view-orders') {
            fetchOrders();
        }
    });
});


// Initial data fetch
fetchIngredients();
fetchOrders();