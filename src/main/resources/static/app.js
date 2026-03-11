import { EmployeeForm } from "./employee.form.js";
import { EmployeeStore } from "./employee.store.js";
import { EmployeeTable } from "./employee.table.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("#employeeForm");
    const table = document.querySelector("#employeeTableBody");
    const employeeStore = EmployeeStore();

    const employeeForm = EmployeeForm(form, employeeStore);
    const employeeTable = EmployeeTable(table, employeeStore, (employee) => employeeForm.fillForm(employee));

    employeeStore.init()
});