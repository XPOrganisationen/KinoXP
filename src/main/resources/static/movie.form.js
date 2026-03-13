export function MovieForm(element, store) {

    const resetForm = () => {
        element.reset();
        element.querySelector("input[name='movieId']").value = "";
        document.querySelector("#cancelEditBtn").classList.add("hidden");
        document.querySelector("[type='submit']").textContent = "Add Movie";
    };

    const destroy = () => {
        element.removeEventListener("submit", onSubmit);
        element.querySelector("#cancelEditBtn").removeEventListener("click", resetForm);
        store.unsubscribe(handleStoreChange);
    };

    const onSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData(element);
        const title = formData.get("title");
        const movieId = formData.get("movieId");
        const description = formData.get("description")
        const id = formData.get("movieId");
        const duration = formData.get("duration")
        const category = formData.get("category");
        const ageLimit = formData.get("ageLimit");
        const is3d = formData.get("is_3d").checked;

        const movie = {
            movieTitle: title,
            movieId: id,
            movieDescription: description,
            movieDuration: duration,
            movieCategory: category,
            ageLimit,
            is_3d: is3d
        };

        if (id) {
            movie.movieId = Number(id);
            await store.changeMovie(id, movie);
        } else {
            await store.addMovie(movie);
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

    const fillForm = (movie) => {
        element.querySelector("input[name='title']").value = movie.movieTitle;
        element.querySelector("input[name='movieId']").value = movie.movieId;
        element.querySelector("input[name='description']").value = movie.movieDescription;
        element.querySelector("input[name='duration']").value = movie.movieDuration;
        element.querySelector("input[name='category']").value = movie.movieCategory;
        element.querySelector("input[name='ageLimit']").value = movie.ageLimit;
        element.querySelector("input[name='is_3d']").value = movie.is_3d;
        element.querySelector("input[name='id']").value = movie.movieId;

        document.querySelector("#cancelEditBtn").classList.remove("hidden");
        document.querySelector("[type='submit']").textContent = "Update movie";
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