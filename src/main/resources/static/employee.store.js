import {fetchEmployees, createEmployee, updateEmployee, deleteEmployee} from "./employee.api.js";

export function EmployeeStore() {
    let employees = [];
    let isLoading = false;
    let error = null;
    const subscribers = [];

    const subscribe = (callback) => {
        subscribers.push(callback);
        callback({ employees, isLoading, error });

        return () => {
            const index = subscribers.indexOf(callback);
            if (index > -1) {
                subscribers.splice(index, 1);
            }
        };
    };

    const unsubscribe = (callback) => {
        const index = subscribers.indexOf(callback);
        if (index > -1) {
            subscribers.splice(index, 1);
        }
    };

    const notify = () => {
        subscribers.forEach(callback => {
            callback({ employees, isLoading, error });
        });
    };

    async function loadEmployees(isSilent = false) {
        error = null;
        if (!isSilent) {
            isLoading = true;
            notify();
        }
        try {
            employees = await fetchEmployees();
        } catch (err) {
            error = {
                message: "Failed to fetch employees. Please try again.",
                type: "FetchError"
            };
        } finally {
            isLoading = false;
        }
        notify();
    }

    async function addEmployee(employee) {
        error = null;
        const tempId = -Date.now();
        const optimisticEmployee = { ...employee, employeeId: tempId };
        employee = [...employee, optimisticEmployee];
        notify();

        try {
            const newEmployee = await createEmployee(employee);

            employees = employees.map(e => e.employeeId == tempId ? newEmployee : e);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to create employee. Please try again.",
                type: "CreateError"
            };
            employees = employees.filter(t => t.id !== tempId);
        }
        notify();
    }

    async function changeEmployee(id, employee) {
        error = null;
        const previousEmployees = [...employees];

        employees = employees.map(t =>
            t.id === Number(id) ? { ...employee, id: Number(id) } : t
        );
        notify();

        try {
            await updateEmployee(id, employee);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to update employee. Please try again.",
                type: "UpdateError"
            };
            employees = previousEmployees;
        }
        notify();
    }

    async function removeEmployee(id) {
        error = null;
        const previousEmployees = [...employees];

        employees = employees.filter(employee => employee.employeeId !== Number(id));
        notify();

        try {
            await deleteEmployee(id);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to delete employee. Please try again.",
                type: "DeleteError"
            };
            employees = previousEmployees;
        }
        notify();
    }

    const getEmployeeById = (id) => {
        return employees.find(employee => employee.employeeId === Number(id));
    };

    const init = async () => {
        await loadEmployees();
    };

    return {
        subscribe,
        unsubscribe,
        init,
        addEmployee,
        changeEmployee,
        removeEmployee,
        getEmployeeById
    };
}