window.addEventListener('DOMContentLoaded', initApp);

async function initApp() {
    let popular = await fetchDataFrom('http://localhost:8080/api/movies/by-popularity');
    let availableShownSoon = await fetchDataFrom('http://localhost:8080/api/movies/by-available-seats-shown-soon');
    document.querySelector('.scroll--content').addEventListener('click', handleMovieClick);
    document.querySelector('#soon-grid').addEventListener('click', handleMovieClick);
    renderPage(popular, availableShownSoon);
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

function handleMovieClick(event) {
    let clickedMovieCard = event.target.closest(".movie-card");
    let clickedID = clickedMovieCard.getAttribute("data-movie-id");
    window.location.href = `/api/movies/${clickedID}`;
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
    card_body.appendChild(card_description);
    card_body.appendChild(card_title);

    card.appendChild(card_img);
    card.appendChild(card_body);
    return card;
}

function renderPage(popularMovies, moviesAvailableShownSoon) {
    let carrouselContents = document.querySelector(".scroll--content");
    let gridContents = document.querySelector("#soon-grid");
    let carrouselMovieCards = popularMovies.map(movie => buildMovieCard(movie));
    let availableShownSoonCards = moviesAvailableShownSoon.map(movie => buildMovieCard(movie));

    carrouselMovieCards.forEach((movieCard) => carrouselContents.appendChild(movieCard));
    availableShownSoonCards.forEach((movieCard) => gridContents.appendChild(movieCard));
}