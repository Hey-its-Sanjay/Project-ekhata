const body = document.querySelector("body");
const darkLight = document.querySelector("#darkLight");
const sidebar = document.querySelector(".sidebar");
const submenuItems = document.querySelectorAll(".submenu_item");
const sidebarOpen = document.querySelector("#sidebarOpen");
const sidebarClose = document.querySelector(".collapse_sidebar");
const sidebarExpand = document.querySelector(".expand_sidebar");
const menuItems = document.querySelectorAll('.nav_link');

// Toggle sidebar visibility
sidebarOpen.addEventListener("click", () => sidebar.classList.toggle("close"));

sidebarClose?.addEventListener("click", () => {
    sidebar.classList.add("close", "hoverable");
});

sidebarExpand?.addEventListener("click", () => {
    sidebar.classList.remove("close", "hoverable");
});

// Show sidebar on hover if it's in hoverable mode
sidebar.addEventListener("mouseenter", () => {
    if (sidebar.classList.contains("hoverable")) {
        sidebar.classList.remove("close");
    }
});

// Hide sidebar when not hovering if it's in hoverable mode
sidebar.addEventListener("mouseleave", () => {
    if (sidebar.classList.contains("hoverable")) {
        sidebar.classList.add("close");
    }
});

// Toggle dark/light mode
darkLight.addEventListener("click", () => {
    body.classList.toggle("dark");
    if (body.classList.contains("dark")) {
        darkLight.classList.replace("bx-sun", "bx-moon");
    } else {
        darkLight.classList.replace("bx-moon", "bx-sun");
    }
});

// Toggle submenu visibility
submenuItems.forEach((item) => {
    item.addEventListener("click", () => {
        item.classList.toggle("show_submenu");
        submenuItems.forEach((item2) => {
            if (item !== item2) {
                item2.classList.remove("show_submenu");
            }
        });
    });
});

// Handle menu item click to set the active class
menuItems.forEach(item => {
    item.addEventListener('click', function() {
        menuItems.forEach(el => el.classList.remove('active'));
        this.classList.add('active');
    });
});

// Handle window resizing
window.addEventListener("resize", () => {
    if (window.innerWidth < 768) {
        sidebar.classList.add("close");
    } else {
        sidebar.classList.remove("close");
    }
});

// Initial check for responsive design
if (window.innerWidth < 768) {
    sidebar.classList.add("close");
} else {
    sidebar.classList.remove("close");
}


