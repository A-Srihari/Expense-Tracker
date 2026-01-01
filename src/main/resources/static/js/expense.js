async function loadExpenses() {
    const res = await fetch("/expenses");
    const data = await res.json();

    document.getElementById("total").innerText = data.total;

    const table = document.getElementById("expenseTable");
    table.innerHTML = "";

    data.expenses.forEach(e => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td data-label="Description">${e.description}</td>
            <td data-label="Amount">₹${e.amount}</td>
            <td data-label="Date">${e.date}</td>
            <td class="actions" data-label="Actions">
                <button class="primary-btn"
                    onclick="editExpense(${e.id}, '${e.description}', ${e.amount}, '${e.date}')">
                    Edit
                </button>
                <button class="danger-btn"
                    onclick="deleteExpense(${e.id})">
                    Delete
                </button>
            </td>
        `;
        table.appendChild(row);
    });
}

async function saveExpense() {
    const id = document.getElementById("expenseId").value;
    const amount = document.getElementById("amount").value;
    const description = document.getElementById("description").value;
    const date = document.getElementById("date").value;

    if (!amount || !description || !date) {
        alert("All fields are required");
        return;
    }

    const payload = { amount, description, date };

    if (id) {
        await fetch(`/expenses/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
    } else {
        await fetch("/expenses", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
    }

    clearForm();
    loadExpenses();
}

function editExpense(id, desc, amt, date) {
    document.getElementById("expenseId").value = id;
    document.getElementById("description").value = desc;
    document.getElementById("amount").value = amt;
    document.getElementById("date").value = date;
}

async function deleteExpense(id) {
    await fetch(`/expenses/${id}`, { method: "DELETE" });
    loadExpenses();
}

async function logout() {
    await fetch("/auth/logout", { method: "POST" });
    window.location.href = "index.html";
}

function clearForm() {
    document.getElementById("expenseId").value = "";
    document.getElementById("amount").value = "";
    document.getElementById("description").value = "";
    document.getElementById("date").value = "";
}

window.onload = loadExpenses;
