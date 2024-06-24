document.addEventListener("DOMContentLoaded", function() {
    // Validates the login form
    function validateLoginForm() {
        var username = document.forms["loginForm"]["username"].value.trim();
        var password = document.forms["loginForm"]["password"].value.trim();

        if (!username || !password) {
            displayError("Username and Password must be filled out");
            return false;
        }

        return true;
    }

    // Validates the signup form
    function validateSignUpForm() {
        var name = document.forms["signUpForm"]["name"].value.trim();
        var email = document.forms["signUpForm"]["email"].value.trim();
        var password = document.forms["signUpForm"]["password"].value.trim();
        var confirmPassword = document.forms["signUpForm"]["confirmPassword"].value.trim();

        if (!name || !email || !password || !confirmPassword) {
            displayError("All fields must be filled out");
            return false;
        }

        if (password !== confirmPassword) {
            displayError("Passwords do not match");
            return false;
        }

        return true;
    }

    // Function to display errors more subtly
    function displayError(message) {
        alert(message); // You can replace this with a more sophisticated error display mechanism
    }

    // Attach validation functions to forms if they exist
    var loginForm = document.forms["loginForm"];
    if (loginForm) {
        loginForm.onsubmit = validateLoginForm;
    }

    var signUpForm = document.forms["signUpForm"];
    if (signUpForm) {
        signUpForm.onsubmit = validateSignUpForm;
    }
});
