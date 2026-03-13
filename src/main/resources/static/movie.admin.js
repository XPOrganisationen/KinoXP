import { MovieForm } from "./movie.form.js";
import { MovieStore } from "./movie.store.js";
import { MovieTable } from "./movie.table.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("#movieForm");
    const table = document.querySelector("#movieTableBody");
    const movieStore = MovieStore();

    const movieForm = MovieForm(form, movieStore);
    const movieTable = MovieTable(table, movieStore, (movie) => movieForm.fillForm(movie));

    movieStore.init()
});