document.addEventListener("DOMContentLoaded", function() {
    // Function to update notification count
    function updateNotificationCount() {
        const notificationItems = document.querySelectorAll('.list-group-item-action');
        const notificationCount = notificationItems.length;
        const badge = document.querySelector('.badge');
        badge.textContent = notificationCount + " New";

        // Show/Hide badge based on notification count
        if (notificationCount > 0) {
            badge.style.display = 'inline-block';
        } else {
            badge.style.display = 'none';
        }
    }

    // Function to update dot visibility
    function updateDotVisibility() {
        const notificationItems = document.querySelectorAll('.list-group-item-action');
        const notificationDot = document.querySelector('.notif-badge');

        // Show/Hide dot based on notification items
        if (notificationItems.length > 0) {
            notificationDot.style.display = 'block';
        } else {
            notificationDot.style.display = 'none';
        }
    }

    // "Clear all" button click event
    const clearAllBtn = document.querySelector('.card-header a[href="#"]');
    clearAllBtn.addEventListener('click', function(event) {
        event.preventDefault();
        const notificationList = document.querySelector('.list-group');
        notificationList.innerHTML = ''; // Clear all notification items
        updateNotificationCount(); // Update badge count to 0
        updateDotVisibility(); // Update dot visibility
    });

    // "x" button click event (works for all buttons)
    const removeNotificationBtns = document.querySelectorAll('.remove-notification');
    removeNotificationBtns.forEach(function(btn) {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            const listItem = btn.closest('li');
            listItem.parentNode.removeChild(listItem); // Remove specific notification item
            updateNotificationCount(); // Update badge count after removal
            updateDotVisibility(); // Update dot visibility
        });
    });

    // Call updateNotificationCount on page load to set initial badge state
    updateNotificationCount();
    // Call updateDotVisibility on page load to set initial dot visibility
    updateDotVisibility();
});
