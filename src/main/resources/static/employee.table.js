export function EmployeeTable(element, store, onEdit) {

    const resetTable = () => {
        element.innerHTML = "";
    };

    const destroy = () => {
        resetTable();
        store.unsubscribe(render);
        element.removeEventListener("click", onClick);
        element.removeEventListener("change", onChange);
    };

    const render = ({ employees, isLoading, error }) => {
        resetTable();

        if (error && error.type === "FetchError") {
            element.innerHTML = `<tr><td colspan="4">${error.message}</td></tr>`;
            return;
        }

        if (error && error.type === "DeleteError") {
            alert(`Error: ${error.message}`);
        }

        if (isLoading) {
            element.innerHTML = `<tr><td colspan="4">Loading...</td></tr>`;
            return;
        }

        employees.forEach(employee => {
            const tr = document.createElement("tr");
            tr.setAttribute("data-id", employee.id);
            tr.innerHTML =  `
                <td>${employee.employeeName}</td>
                <td>${employee.employeeRole}</td>
                <td>
                    <div class="gap-2 flex">
                        <button data-action="edit" class="btn btn-warning">Edit</button>
                        <button data-action="delete" class="btn btn-danger">Delete</button>
                    </div>
                </td>
            `;
            element.appendChild(tr);
        });
    };

    const onClick = async (event) => {
        const id = event.target.closest("tr")?.dataset.id;
        if (id === undefined) return;

        if (event.target.getAttribute("data-action") === "delete") {
            if (!confirm('Are you sure you want to delete this employee?')) {
                return;
            }
            await store.removeEmployee(id);
            return;
        }

        if (event.target.getAttribute("data-action") === "edit") {
            const todo = store.getEmployeeById(id);
            onEdit(todo);
            return;
        }

        window.location.href = `details/?id=${id}`;
    };

    const onChange = async (event) => {
        if (event.target.getAttribute("data-action") === "toggle") {
            const row = event.target.closest("tr");
            if (!row || !row.dataset.id) return;
            const id = parseInt(row.dataset.id);

            const employee = store.getEmployeeById(id);
            await store.changeEmployee(id, { ...employee});
        }
    };

    const init = () => {
        store.subscribe(render);
        element.addEventListener("click", onClick);
        element.addEventListener("change", onChange);
    }

    init();

    return {
        destroy
    };
}