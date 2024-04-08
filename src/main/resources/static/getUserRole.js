fetch('/api/userRole')
    .then(response => response.json())
    .then(roles => {
        if (roles.includes("ROLE_ADMIN")) {
            console.log("User is an admin.");
        } else {
            console.log("User is not an admin.");
        }

    })
    .catch(error => console.error('Error fetching user roles:', error));