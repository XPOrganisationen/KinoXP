export function EmployeeForm(element, store) {

    const resetForm = () => {
        element.reset();
        element.querySelector("input[name='employeeId']").value = "";
        document.querySelector("#cancelEditBtn").classList.add("hidden");
        document.querySelector("[type='submit']").textContent = "Add Employee";
    };

    const destroy = () => {
        element.removeEventListener("submit", onSubmit);
        element.querySelector("#cancelEditBtn").removeEventListener("click", resetForm);
        store.unsubscribe(handleStoreChange);
    };

    const onSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData(element);
        const name = formData.get("name");
        const employeeId = formData.get("employeeId");
        const role = formData.get("role")
        const username = formData.get("username")
        const password = formData.get("password");
        const id = formData.get("employeeId");

        const employee = {
            name,
            id,
            role,
            username,
            password
        };

        if (id) {
            employee.employeeId = Number(id);
            await store.changeEmployee(id, employee);
        } else {
            await store.addEmployee(employee);
        }

        resetForm();
    };

    const setLoading = (loading) => {
        const submitBtn = element.querySelector("[type='submit']");
        submitBtn.disabled = loading;
    };

    const handleStoreChange = (data) => {
        setLoading(data.isLoading);

        if (data.error && (data.error.type === "CreateError" || data.error.type === "UpdateError")) {
            alert(`Error: ${data.error?.message}`);
            const submitBtn = element.querySelector("[type='submit']");
            submitBtn.disabled = false;
        }
    };

    const fillForm = (employee) => {
        element.querySelector("input[name='name']").value = employee.employeeName;
        element.querySelector("input[name='employeeId']").value = employee.employeeId;
        element.querySelector("input[name='role']").value = employee.employeeRole;
        element.querySelector("input[name='username']").value = employee.employeeUsername;
        element.querySelector("input[name='password']").value = employee.employeePassword;
        element.querySelector("input[name='id']").value = employee.id;

        document.querySelector("#cancelEditBtn").classList.remove("hidden");
        document.querySelector("[type='submit']").textContent = "Update employee";
    };

    const init = () => {
        element.addEventListener("submit", onSubmit);
        element.querySelector("#cancelEditBtn").addEventListener("click", resetForm);
        store.subscribe(handleStoreChange);
    };

    init();

    return {
        destroy,
        fillForm
    };
}