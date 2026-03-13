window.addEventListener("DOMContentLoaded", initReceiptPage);

function initReceiptPage()
{
    const raw = sessionStorage.getItem("completedOrder");

    if(!raw)
    {
        alert("No receipts was found")
        window.location.href = "index.html";
        return;
    }

    let order;
    try
    {
        order = JSON.parse(raw);
    }
    catch(error)
    {
        console.error("Could not read the completed order", error);
        window.location.href="index.html";
        return;
    }

    renderReceipt(order);

    document.getElementById("receipt-home-btn").addEventListener("click", () =>
    {
     sessionStorage.removeItem("completedOrder");
     window.location.href="index.html";
    });
}

function renderReceipt(order)
{
    const container = document.getElementById("receipt-content");
    let movieTitle = order.show.movie.movieTitle;
    let cinemaName = order.show.theater.cinema.cinemaName;
    let theaterName = order.show.theater.theaterName;
    let showTime = order.show.startTime;

    const seatsMarkup = Array.isArray(order.movieTickets)
        ? order.movieTickets.map(ticket => {
            let seat = ticket.showSeat.seat;
            return `<li>Row ${seat.rowNumber}, Seat ${seat.seatNumber} - ${ticket.ticketType || "Ticket"} - ${formatPrice(ticket.price)}</li>`
        }).join(""):"";

    let reservationFeeText = order.reservationFee > 0 ? 'Reservation fee: ' : order.reservationFee < 0 ? 'Discount: ' : '';
    let reservationFeeLine = reservationFeeText !== '' ? `<p><strong>${reservationFeeText}</strong>${formatPrice(order.reservationFee)}</p>` : '';
    container.innerHTML = `
        <p><strong>Order number:</strong> ${order.orderNumber}</p>
        <p><strong>Name:</strong> ${order.customerName}</p>
        <p><strong>Email:</strong> ${order.customerEmail}</p>
        <p><strong>Movie:</strong> ${movieTitle}</p>
        <p><strong>Cinema:</strong> ${cinemaName || "-"}</p>
        <p><strong>Theater:</strong> ${theaterName || "-"}</p>
        <p><strong>Show time:</strong> ${formatDateTime(showTime)}</p>
        <p><strong>Paid at:</strong> ${formatDateTime(order.paidAt)}</p>
        <p><strong>Seats:</strong></p>
        <ul>${seatsMarkup}</ul>
        ${reservationFeeLine}
        <p><strong>Total:</strong> ${formatPrice(order.totalPrice || 0)}</p>
    `;
}

function formatPrice(value)
{
    return `${Number(value).toFixed(2).replace(".", ",")} DKK`;
}

function formatDateTime(value)
{
    if (!value) return "-";

    const date = new Date(value);
    if (Number.isNaN(date.getTime())) {
        return value;
    }

    return date.toLocaleString("da-DK",
        {
            dateStyle: "short",
            timeStyle: "short"
        });
}
