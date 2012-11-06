$(document).ready(function() {
	// Remove the click handler to the login button
	// added by facebook.
	$(".fb-login-button").unbind('click');
});

function login() {
    FB.login(function(response) {
        if (response.authResponse) {
        	console.log(JSON.stringify(response.authResponse, null, 2))
            // connected
        	FB.api("/me",{fields:"first_name,last_name"} ,function(user) {
        		if (!user || user.error) {
					alert("Error getting user data from Facebook");					
				} else {
					user.accessToken = response.authResponse.accessToken;
					loginRequest(JSON.stringify(user));
				}
        	});
        } else {
            // cancelled
        }
    });
}

function loginRequest(response) {}