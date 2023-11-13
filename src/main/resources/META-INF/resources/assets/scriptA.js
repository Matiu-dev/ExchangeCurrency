document.addEventListener("DOMContentLoaded", function () {
    const sendButton = document.getElementById("sendButton");

    const amountInput = document.getElementById("amount");
    const myCurrencyInput = document.getElementById("myCurrency");
    const targetCurrencyInput = document.getElementById("targetCurrency");

    const responseDiv = document.getElementById("response");

    sendButton.addEventListener("click", function () {

        console.log("siema");
        const amount = amountInput.value;
        const myCurrency = myCurrencyInput.value;
        const targetCurrency = targetCurrencyInput.value;

        const data = {
            amount: amount,
            myCurrency: myCurrency,
            targetCurrency: targetCurrency
        };

        // Wysłanie żądania POST za pomocą AJAX
        fetch("/currency/currencyExchangeValue", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                responseDiv.innerHTML = "Odpowiedź serwera: " + data.response;
            })
            .catch(error => {
                responseDiv.innerHTML = "Błąd: " + error.message;
            });
    });
});
