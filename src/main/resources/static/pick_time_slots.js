window.addEventListener('DOMContentLoaded', initApp);

async function initApp() {
    let url = new URL(window.location.href);
    let movieId = Number.parseInt(url.searchParams.get('movieId'));
    let cinemaGroups = await fetchDataFrom(`http://localhost:8080/api/shows/grouped-by-cinema/${movieId}`);
    let movie = await fetchDataFrom(`http://localhost:8080/api/movies/${movieId}`);
    buildShowSummaryPage(cinemaGroups, movie);
}

function buildShowSummaryPage(cinemaGroups, movie) {
    let main = document.querySelector('main');
    let h1 = document.createElement('h1');
    h1.textContent = `Vælg biograf, sal og tidspunkt for ${movie.movieTitle}`;
    let gridDiv = document.createElement('div');
    gridDiv.classList.add('cinema-grid');
    main.appendChild(h1);
    main.appendChild(gridDiv);

    cinemaGroups.forEach(cinemaGroup => {
        const cinemaBox = buildCinemaGroupBox(cinemaGroup);
        cinemaGroup.theaters.forEach(theaterGroup => {
            cinemaBox.appendChild(buildTheaterBoxes(theaterGroup));
        });

        gridDiv.appendChild(cinemaBox);
    });
}

function buildTheaterBoxes(theaterGroup) {
    let boxLabelContainer = document.createElement('div');
    boxLabelContainer.classList.add('box-with-label');
    let timeBox = document.createElement('div');
    timeBox.classList.add('timeBox');
    let timeBoxLabel = document.createElement('h3');
    timeBoxLabel.textContent = theaterGroup.theaterName;
    let ul = document.createElement('ul');
    ul.classList.add('timeBoxList');
    timeBox.appendChild(ul);

    theaterGroup.shows.forEach(show => {
       let linkToSeats = document.createElement('a');
       linkToSeats.href = `book_seats.html?theaterId=${theaterGroup.theaterId}&showId=${show.showId}`;
       let timeField = document.createElement('li');
       timeField.classList.add('timeField');
       timeField.setAttribute("data-show-id", show.showId);
       linkToSeats.textContent = show.startTime.replace('T', ' kl. ');
       timeField.appendChild(linkToSeats);
       ul.appendChild(timeField);
    });

    boxLabelContainer.appendChild(timeBoxLabel);
    boxLabelContainer.appendChild(timeBox);
    return boxLabelContainer;
}

function buildCinemaGroupBox(cinemaGroup) {
    let boxLabelContainer = document.createElement('div');
    boxLabelContainer.classList.add('box-with-label');
    let cinemaBox = document.createElement('div');
    let cinemaLabel = document.createElement('h3');
    cinemaLabel.textContent = cinemaGroup.cinemaName;
    cinemaBox.classList.add('cinemaBox');
    cinemaBox.setAttribute("data-cinema-id", cinemaGroup.cinemaId);
    boxLabelContainer.appendChild(cinemaLabel);
    boxLabelContainer.appendChild(cinemaBox);
    return boxLabelContainer;
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