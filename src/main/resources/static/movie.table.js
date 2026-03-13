export function MovieTable(element, store, onEdit) {

    const resetTable = () => {
        element.innerHTML = "";
    };

    const destroy = () => {
        resetTable();
        store.unsubscribe(render);
        element.removeEventListener("click", onClick);
        element.removeEventListener("change", onChange);
    };

    const render = ({movies, isLoading, error }) => {
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

        movies.forEach(movie => {
            const tr = document.createElement("tr");
            tr.setAttribute("data-id", movie.movieId);
            tr.innerHTML =  `
                <td>${movie.movieTitle}</td>
                <td>${movie.movieDescription}</td>
                <td>${movie.movieDuration}</td>
                <td>${movie.movieCategory}</td>
                <td>${movie.ageLimit}</td>
                <td><input 
                        type="checkbox" 
                        ${movie.is_3d ? 'checked' : ''}
                        data-action="toggle"
                        class="completed-checkbox"
                    ></td>
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
            if (!confirm('Are you sure you want to delete this movie?')) {
                return;
            }
            await store.removeMovie(id);
            return;
        }

        if (event.target.getAttribute("data-action") === "edit") {
            const movie = await store.getMovieById(id);
            onEdit(movie);
            return;
        }

        window.location.href = `details/?id=${id}`;
    };

    const onChange = async (event) => {
        if (event.target.getAttribute("data-action") === "toggle") {
            const row = event.target.closest("tr");
            if (!row || !row.dataset.id) return;
            const id = parseInt(row.dataset.id);
            const completed = event.target.checked;

            const movie = store.getMovieById(id);
            await store.changeMovie(id, { ...movie, completed });
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