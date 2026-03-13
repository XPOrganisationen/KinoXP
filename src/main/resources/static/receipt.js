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

    const seatsMarkup = Array.isArray(order.seats)
        ? order.seats.map(seat => `<li>Row ${seat.row}, Seat ${seat.number} - ${seat.ticketType || "Ticket"} - ${formatPrice(seat.price)}</li>`
        ).join(""):"";

    container.innerHTML = `
        <p><strong>Order number:</strong> ${order.orderNumber}</p>
        <p><strong>Name:</strong> ${order.customerName}</p>
        <p><strong>Email:</strong> ${order.customerEmail}</p>
        <p><strong>Movie:</strong> ${order.movieTitle}</p>
        <p><strong>Cinema:</strong> ${order.cinemaName || "-"}</p>
        <p><strong>Theater:</strong> ${order.theaterName || "-"}</p>
        <p><strong>Show time:</strong> ${formatDateTime(order.showTime)}</p>
        <p><strong>Paid at:</strong> ${formatDateTime(order.paidAt)}</p>
        <p><strong>Seats:</strong></p>
        <ul>${seatsMarkup}</ul>
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
