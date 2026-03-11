export const BASE_URL = "http://localhost:8080/api/employees/";

const BASE_URL_EMPLOYEES = `${BASE_URL}`;

export async function fetchEmployees() {
    const response = await fetch(`${BASE_URL_EMPLOYEES}`);
    if (!response.ok) {
        throw new Error('Failed to fetch employees');
    }
    return response.json();
}

export async function createEmployee(employee) {
    const response = await fetch(`${BASE_URL_EMPLOYEES}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(employee),
    });
    if (!response.ok) {
        throw new Error('Failed to add employee');
    }
    return response.json();
}

export async function updateEmployee(employeeId, employee) {
    const response = await fetch(`${BASE_URL_EMPLOYEES}${employeeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(employee),
    });
    if (!response.ok) {
        throw new Error('Failed to update employee');
    }
    return response.json();
}

export async function deleteEmployee(employeeId) {
    const response = await fetch(`${BASE_URL_EMPLOYEES}${employeeId}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete employee');
    }
}