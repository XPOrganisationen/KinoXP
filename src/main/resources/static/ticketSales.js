window.addEventListener("DOMContentLoaded", init);


async function init() {
    try {
        const movies = await fetchMovies();
        const allSales = await fetchAllTicketSales();

        for (const movie of movies) {
            const sales = allSales.find(s => s.movieTitle === movie.movieTitle) || { ticketsSold: 0, revenue: 0};
            renderMovieRow(movie, sales);
        }
    } catch (err) {
        console.error("error loading ticket sales:", err);
    }
}

async function fetchMovies() {
    const response = await fetch("http://localhost:8080/api/movies/")
    if (!response.ok) throw new Error("Failed to fetch movies");
    return await response.json();
}

async function fetchAllTicketSales() {
    const response = await fetch(`http://localhost:8080/api/tickets/salesPerMovie`);
    if (!response.ok) throw new Error("Failed to fetch ticket sales");
    return await response.json()
}

function renderMovieRow(movie, ticketSales) {
    const tableBody = document.querySelector("#ticketTableBody");
    const tr = document.createElement("tr");
    tr.innerHTML = `
    <td>${movie.movieTitle}</td>
    <td>${movie.movieDescription}</td>
    <td>${ticketSales.ticketsSold}</td>
    <td>${ticketSales.revenue} kr </td>
`;
    tableBody.appendChild(tr);
}
