window.addEventListener('DOMContentLoaded', initApp);

let seatsPicked = [];
let ticketTypes = [];

async function initApp() {
    let url = new URL(window.location.href);
    let showId = Number.parseInt(url.searchParams.get('showId'));
    let showSeats = await fetchDataFrom(`http://localhost:8080/api/seats/show/${showId}`);
    let theaterId = Number.parseInt(url.searchParams.get('theaterId'));
    let theater = await fetchDataFrom(`http://localhost:8080/api/theaters/${theaterId}`);
    buildPickSeatsPage(theater.numberOfRows, theater.seatsPerRow, showSeats);
    document.querySelector('#seat-grid').addEventListener('click', event => handleReserveSeat(event, showSeats));
    let seatGrid = document.querySelector('#seat-grid');
    seatGrid.style.setProperty('--cols', theater.seatsPerRow);
    seatGrid.style.setProperty('--rows', theater.numberOfRows);
    let allTicketTypes = await fetchDataFrom('http://localhost:8080/api/tickets/all-ticket-types');
    buildTicketTypeModal(allTicketTypes);

    let payButton = document.createElement('button');
    payButton.type = 'button';
    payButton.classList.add('pay-button');
    payButton.addEventListener('click', event => handlePayClicked(event, showId));
    payButton.textContent = 'Pay';
    document.querySelector('#indicator-list').after(payButton);
}

async function fetchDataFrom(URL, options) {
    let response;

    try {
        response = await fetch(URL, options);
    } catch (error) {
        console.error('Got error: ', error);
    }

    return await response.json();
}

async function handlePayClicked(event, showId) {
    for (let i = 0; i < seatsPicked.length; i++) {
        let sp = seatsPicked[i];
        let reservationDTO = {showId: showId, seatId: sp.seatId, ticketType: ticketTypes[i]};

        let options = {
            method: 'POST',
            headers: {
                'content-type': 'application/json',
            },
            body: JSON.stringify(reservationDTO)
        };

        await fetchDataFrom('http://localhost:8080/api/tickets/reserve', options);
    }

    // Then save the two global lists in browser storage
    // and redirect to pay.html.
    localStorage.setItem('payData', JSON.stringify({seatsPicked: seatsPicked, ticketTypes: ticketTypes}));
    // Patrick skal bruge JSON.parse(localStorage.getItem('payData');
    window.location.href = 'pay.html';
}

function getTicketType() {
    let modal = document.querySelector('.modal');
    modal.classList.remove('hidden');

    return new Promise(resolve => {
        function done(value) {
            cleanup();
            resolve(value);
        }

        function resetRadioButtons() {
            let buttons = modal.querySelectorAll('input[type="radio"]');
            buttons.forEach(button => button.checked = false);
        }

        function onCloseClick() {
            let checked = document.querySelector('input[type=radio]:checked');
            let chosenType = checked ? checked.parentNode.textContent : '';
            done(chosenType);
        }

        function cleanup() {
            resetRadioButtons();
            modal.classList.add('hidden');
            modal.querySelector('.btn-close').removeEventListener('click', onCloseClick);
        }

        modal.querySelector('.btn-close').addEventListener('click', onCloseClick);
    });
}

function buildTicketTypeModal(allTicketTypes) {
    let ticketTypeList = document.querySelector("#ticket-type-list");

    allTicketTypes.forEach(ticketType => {
       let li = document.createElement("li");
       li.classList.add('ticket-type-item');
       li.textContent = ticketType;
       let radioButton = document.createElement("input");
       radioButton.type = 'radio';
       radioButton.name = 'ticket-type';
       li.appendChild(radioButton);
       ticketTypeList.appendChild(li);
    });
}

async function handleReserveSeat(event) {
    let seatDivClicked = event.target.closest('div');

    let isSeat = Array.from(seatDivClicked.classList).some(s => s.startsWith('seat-'));
    if (!isSeat) {
        return;
    }

    let id = Number.parseInt(seatDivClicked.getAttribute('data-seat-id'));
    let availability = seatDivClicked.getAttribute('data-availability');
    let seat = await fetchDataFrom(`http://localhost:8080/api/seats/${id}`);

    if (availability === 'reserved-by-you') {
        let index = seatsPicked.indexOf(seat);
        seatsPicked.splice(index, 1);
        seatDivClicked.setAttribute('data-availability', 'vacant');
        seatDivClicked.classList.remove(`seat-reserved-by-you`);
        seatDivClicked.classList.add(`seat-vacant`);
        ticketTypes.splice(index, 1);
        return;
    }

    if (availability !== 'reserved' && availability !== 'out_of_service') {
        let chosenTicketType = await getTicketType();

        if (chosenTicketType === null || chosenTicketType === '') {
            return;
        }

        ticketTypes.push(chosenTicketType);
        seatsPicked.push(seat);
    } else {
        return;
    }

    seatDivClicked.setAttribute('data-availability', 'reserved-by-you');
    seatDivClicked.classList.remove(`seat-${availability}`);
    seatDivClicked.classList.add(`seat-reserved-by-you`);
}

async function buildPickSeatsPage(numRows, numSeats, showSeats) {
    buildSeatGrid(numRows, numSeats, showSeats);
    let availabilityOptions = await fetchDataFrom("http://localhost:8080/api/seats/get-all-seat-availability-options");
    buildColorIndicatorGrid(availabilityOptions);
}


function buildSeatGrid(numRows, numSeats, showSeats) {
    let seatGrid = document.querySelector('#seat-grid');

    for (let i = 0; i < numRows; i++) {
        for (let j = 0; j < numSeats; j++) {
            let showSeat = showSeats[j * i + j];
            let seatBox = document.createElement('div');
            seatBox.classList.add('seat-' + showSeat.seatAvailability.toLowerCase());
            seatBox.setAttribute('data-seat-id', showSeat.showSeatId);
            seatBox.setAttribute('data-availability', showSeat.seatAvailability.toLowerCase());
            seatGrid.appendChild(seatBox);
        }
    }
}

function buildColorIndicatorGrid(availabilityOptionList) {
    let availabilityGrid = document.querySelector('#seat-availability-indicator-grid');
    let indicatorList = document.createElement('ul');
    indicatorList.setAttribute('id', 'indicator-list');

    availabilityOptionList.forEach(ao => {
       let indicator = document.createElement('li');
       indicator.classList.add('indicator-' + ao.toLowerCase());
       indicatorList.appendChild(indicator);
       indicator.textContent = ao.toLowerCase();
    });

    let indicator = document.createElement('li');
    indicator.classList.add('indicator-reserved-by-you');
    indicatorList.appendChild(indicator);
    indicator.textContent = 'reserved by you'.toLowerCase();
    availabilityGrid.appendChild(indicatorList);
}