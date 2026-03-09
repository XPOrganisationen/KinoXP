const cinemaListDiv = document.getElementById("cinemaList");
const cinemaDetailsDiv = document.getElementById("cinemaDetails");

let selectedCinemaId = null;

async function loadCinemas()
{
    try
    {
        const response = await fetch("/api/cinemas");

        if (!response.ok)
        {
            throw new Error("Couldn't load cinemas");
        }

        const cinemas = await response.json();
        renderCinemaList(cinemas);
    }
    catch (error)
    {
        cinemaListDiv.innerHTML = `<p>Error while loading cinemas: ${error.message}</p>`;
    }
}

function renderCinemaList(cinemas)
{
    cinemaListDiv.innerHTML = "";

    if (cinemas.length === 0)
    {
        cinemaListDiv.innerHTML = "<p>No cinemas found.</p>";
        return;
    }

    cinemas.forEach(cinema =>
    {
        const button = document.createElement("button");
        button.className = "cinema-button";
        button.textContent = cinema.cinemaName;

        if (cinema.cinemaId === selectedCinemaId)
        {
            button.classList.add("selected");
        }

        button.addEventListener("click", () =>
        {
            selectedCinemaId = cinema.cinemaId;
            renderCinemaDetails(cinema);
            renderCinemaList(cinemas);
        });

        cinemaListDiv.appendChild(button);
    });
}

function renderCinemaDetails(cinema)
{
    const encodedAddress = encodeURIComponent(cinema.cinemaAddress);
    const mapUrl = `https://www.google.com/maps/search/?api=1&query=${encodedAddress}`;

    cinemaDetailsDiv.innerHTML = `
        <h3>${cinema.cinemaName}</h3>
        <p><strong>Address:</strong> ${cinema.cinemaAddress}</p>
        <a class="map-link" href="${mapUrl}" target="_blank">
            Open in Google Maps
        </a>
    `;
}

loadCinemas();