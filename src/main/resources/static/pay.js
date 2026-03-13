window.addEventListener("DOMContentLoaded", initPaymentPage);

function initPaymentPage()
{
    const order = getPendingOrder();

    if (!order)
    {
        alert("No active order.");
        window.location.href="index.html";
        return;
    }

    renderOrder(order);

    document
        .getElementById("payment-form")
        .addEventListener("submit", handlePaymentSubmit);

    document
        .getElementById("cancel-payment-btn")
        .addEventListener("click", handleCancelPayment);
}

function getPendingOrder()
{
    const raw = localStorage.getItem("pendingOrder");
    if(!raw) return null;

    try
    {
        return JSON.parse(raw);
    }
    catch(error)
    {
        console.error("Could not read pendingOrder", error);
        return null;
    }
}

function renderOrder(order)
{
    document.getElementById("payment-movie-title").textContent = order.movieTitle || "Movie title missing";

    const showInfo =
        [
            order.cinemaName,
            order.theaterName,
            formatDateTime(order.showTime)
        ].filter(Boolean).join(" - ");

    document.getElementById("payment-show-info").textContent= showInfo || "Missing time of the movie";

    const summaryContent = document.getElementById("summary-content");
    summaryContent.innerHTML="";

    if (Array.isArray(order.seats) && order.seats.length > 0)
    {
        const seatList = document.createElement("ul");
        seatList.classList.add("payment-seat-list");

        order.seats.forEach(seat =>
        {
            const li = document.createElement("li");
            li.textContent = `Row ${seat.row}, Seat ${seat.number} - ${seat.ticketType || "Ticket"} - ${formatPrice(seat.price)}`;
            seatList.appendChild(li);
        });

        summaryContent.appendChild(seatList);
    }
    else
    {
        summaryContent.innerHTML = "<p>No seats selected.</p>";
    }

    if (order.reservationFee && order.reservationFee > 0)
    {
        const fee = document.createElement("p");
        fee.textContent = `Reservation fee: ${formatPrice(order.reservationFee)}`;
        summaryContent.appendChild(fee);
    }
    document.getElementById("payment-total").textContent = formatPrice(order.totalPrice || 0);
}

function handlePaymentSubmit(event)
{
    event.preventDefault();

    const order = getPendingOrder();
    if(!order)
    {
        alert("Order does not exist");
        window.location.href="index.html";
        return;
    }

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const cardNumber = document.getElementById("cardNumber").value.trim();
    const cvc = document.getElementById("cvc").value.trim();

    if (!fullName || !email || !cardNumber || !cvc)
    {
        alert("Fill all fields");
        return;
    }
    const completedOrder =
        {
            ...order,
            customerName: fullName,
            customerEmail: email,
            paymentStatus: "Order Completed! Success!(simulated)",
            orderNumber: generateOrderNumber(),
            paidAt: new Date().toISOString()
        };
    sessionStorage.setItem("completedOrder", JSON.stringify(completedOrder));
    sessionStorage.removeItem("pendingOrder");

    window.location.href="receipt.html";
}

function handleCancelPayment()
{
    const shouldCancel = confirm("Do you want to cancel the payment and return to previous side?")

    if(!shouldCancel) return;

    window.history.back();
}

function generateOrderNumber()
{
    return "KXP-" + Date.now();
}

function formatPrice(value)
{
    return `${Number(value).toFixed(2).replace(".",",")} DKK`;
}

function formatDateTime(value)
{
    if (!value) return "";

    const date = new Date(value);
    if(Number.isNaN(date.getTime()))
    {
        return value;
    }

    return date.toLocaleString("da-DK",
        {
            dateStyle: "short",
            timeStyle: "short"
        });
}
