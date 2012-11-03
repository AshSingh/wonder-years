function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            // connected
        	FB.api("/me",{fields:"first_name,last_name"} ,function(response) {
        		if (!response || response.error) {
					alert("Error getting user data from Facebook");					
				} else {
					loginRequest(JSON.stringify(response));
				}
        	});
        } else {
            // cancelled
        }
    });
}

function loginRequest(response) {}