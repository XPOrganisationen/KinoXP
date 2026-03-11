window.addEventListener('DOMContentLoaded', initApp);

async function initApp() {
    let employees = await fetchDataFrom('http://localhost:8080/api/employees/');
    renderPage(employees);
}

async function fetchDataFrom(URL) {
    let response;

    try {
        response = await fetch(URL);
    } catch (error) {
        console.error("Got error: ", error);
    }

    return await response.json();
}

function addRow(employeeName, employeeRole) {
    let tableBody = document.getElementById('myTableBody');
    let newRow = tableBody.insertRow(tableBody.rows.length);
    let nameCell = newRow.insertCell(0);
    let roleCell = newRow.insertCell(1);
    let nameText = document.createTextNode(employeeName);
    let roleText = document.createTextNode(employeeRole);
    nameCell.appendChild(nameText);
    roleCell.appendChild(roleText);
}

function buildEmployeeTable(employees){
    employees.forEach(function(listItem){
        addRow(listItem.name, listItem.role)
    });
}

function renderPage(employees) {
    let tableContents = document.querySelector(".myTableBody");
    tableContents.appendChild(buildEmployeeTable(employees));
}



