// Get the buttons and popup elements
const cashInBtn = document.getElementById('cashInBtn');
const cashOutBtn = document.getElementById('cashOutBtn');
const cashInPopup = document.getElementById('cashInPopup');
const cashOutPopup = document.getElementById('cashOutPopup');
const closeButtons = document.querySelectorAll('.closePopup');

// Show the Cash In popup when the Cash In button is clicked
cashInBtn.addEventListener('click', () => {
    showPopup(cashInPopup);
});

// Show the Cash Out popup when the Cash Out button is clicked
cashOutBtn.addEventListener('click', () => {
    showPopup(cashOutPopup);
});

// Hide the popup when the close button is clicked
closeButtons.forEach(button => {
    button.addEventListener('click', () => {
        hidePopups();
    });
});

// Function to show a specific popup
function showPopup(popup) {
    hidePopups(); // Hide any currently visible popups
    popup.classList.remove('hide');
    popup.classList.add('show');
}

// Function to hide all popups
function hidePopups() {
    cashInPopup.classList.remove('show');
    cashOutPopup.classList.remove('show');
    cashInPopup.classList.add('hide');
    cashOutPopup.classList.add('hide');
}
