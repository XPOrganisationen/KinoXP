window.addEventListener('DOMContentLoaded', initApp);

async function initApp() {
    let movies = await fetchDataFrom('http://localhost:8080/api/movies/');
    let categories = await fetchDataFrom('http://localhost:8080/api/movies/categories');
    document.querySelector('#sidebar-filter').addEventListener('submit', handleCategorySubmit);
    document.querySelector('#search-input').onblur = handleSearchSubmit;
    document.querySelector('.search-results').addEventListener('click', handleMovieClick);
    renderPage(movies, categories);
}

async function handleCategorySubmit(event) {
    event.preventDefault();
    let categories = [...document.querySelectorAll('input[type=checkbox]:checked')].map(checkbox => checkbox.id);
    let searchTerm = document.querySelector('#search-input').value;
    let ageLimit = Number.parseInt(document.querySelector('#age-input').value);
    let filter = {categories: categories, ageLimit: ageLimit,  title: searchTerm};

    let options = {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
        },
        body: JSON.stringify(filter)
    };

    let movies = await fetchDataFrom('http://localhost:8080/api/movies/by-filter', options);
    updatePage(movies);
}

async function handleSearchSubmit() {
    let searchTerm = document.querySelector('#search-input').value;
    if (searchTerm === "") {
        updatePage(await fetchDataFrom('http://localhost:8080/api/movies/'));
        return;
    }

    let movies = await fetchDataFrom('http://localhost:8080/api/movies/by-title/' + searchTerm);
    updatePage(movies);
}

function renderPage(movies, categories) {
    let sidebar = document.querySelector('#sidebar-filter');

    categories.forEach(category => {
        let checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'categories';
        checkbox.id = category;

        let label = document.createElement('label');
        label.htmlFor = `${category}`
        label.textContent = category;
        label.appendChild(checkbox);
        sidebar.insertBefore(label, sidebar.firstChild);
    });

    let grid = document.querySelector('.search-results');
    movies.forEach(movie => grid.appendChild(buildMovieCard(movie)));
}

function handleMovieClick(event) {
    let clickedMovieCard = event.target.closest(".movie-card");
    let clickedID = clickedMovieCard.getAttribute("data-movie-id");
    window.location.href = `book_movie_time_slot.html?movieId=${clickedID}`;
}

function buildMovieCard(movie) {
    let card = document.createElement("div");
    card.classList.add("movie-card");
    card.setAttribute("data-movie-id", movie.movieId);

    let card_img = document.createElement("img");
    card_img.classList.add("movie-card-img");
    card_img.setAttribute("src", movie.movieImage);
    card_img.setAttribute("alt", `Poster for ${movie.movieTitle}`);

    let card_body = document.createElement("div");
    card_body.classList.add("movie-card-body");

    let card_title = document.createElement("h3");
    card_title.classList.add("movie-card-title");
    card_title.textContent = movie.movieTitle;

    let card_description = document.createElement("p");
    card_description.classList.add("movie-card-description");
    card_description.textContent = movie.movieDescription;
    card_body.appendChild(card_title);
    card_body.appendChild(card_description);

    card.appendChild(card_img);
    card.appendChild(card_body);
    return card;
}

function updatePage(newMovies) {
    let grid = document.querySelector('.search-results');
    grid.innerHTML = '';
    newMovies.forEach(movie => grid.appendChild(buildMovieCard(movie)));
}

async function fetchDataFrom(URL, options) {
    let response;

    try {
        response = await fetch(URL, options);
    } catch (error) {
        console.error("Got error: ", error);
    }

    return await response.json();
}