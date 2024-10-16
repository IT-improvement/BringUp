// Show popup
document.getElementById('openSkillPopup').addEventListener('click', function() {
    document.getElementById('skillPopup').style.display = 'flex';
});

// Hide popup
document.getElementById('closePopup').addEventListener('click', function() {
    document.getElementById('skillPopup').style.display = 'none';
});

// Save selected skills and display them as boxes
document.getElementById('saveSkills').addEventListener('click', function() {
    const selectedSkillsDiv = document.getElementById('selectedSkills');
    selectedSkillsDiv.innerHTML = ''; // Clear previous selected skills

    const checkboxes = document.querySelectorAll('.skills-checkbox input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            const skillBox = document.createElement('div');
            skillBox.className = 'skill-box';
            skillBox.textContent = checkbox.value;
            selectedSkillsDiv.appendChild(skillBox);
        }
    });

    document.getElementById('skillPopup').style.display = 'none'; // Close popup after saving
});
