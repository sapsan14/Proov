function logInUser(successfulLoginCallback) {

    var email = $('#email').val();
    var password = $('#password').val();

    $.ajax(
        {
            url: '/rest/authenticate_user',
            method: 'POST',
            data: {
                'email': email,
                'password': password
            },
            complete: function (result) {
                //loginprosteduur lõpetanud
                if (result.responseText == 'SUCCESS') {
                    if(window.location.href.includes("login.html")){
                        document.location = 'meeting.html';
                    } else {
                        if(successfulLoginCallback != null){
                            successfulLoginCallback();
                        }
                    }
                    $('#loginModal').modal('hide');
                } else {
                    $('#errorBox').show();
                }

            }
        }
    )

}

function logOutUser(){
    $.ajax(
        {
            url: '/rest/logout',
            method: 'GET',
            complete: function (result) {
                $('#loginBox').show();
                $('#logoutBox').hide();
                $('#errorBox').hide();
                document.location = "/login.html";
            }

        })
}

function getAuthenticatedUser() {
    $.ajax(
        {
            url: '/rest/get_authenticated_user',
            method: 'GET',
            complete: function (result) {
                var user = result.responseJSON;
                if(user != null && user.id > 0){
                    //kasutaja on sisse loginud, kuvame logout nuppu
                    $('#logoutBox').show();

                } else{
                    // kasutaja ei ole sisse loginud, kuvame login kasti.
                    $('#loginBox').show();

                }
            }

        }
    )
}

function getAuthenticatedUserNavBar(completeCallbackFunction) {
    $.ajax(
        {
            url: "/rest/get_authenticated_user",
            method: "GET",
            complete: function (result) {
                authenticatedUser = result.responseJSON;
                userId = authenticatedUser.id;
                $('#user_first_last_name').html(authenticatedUser.firstName + " " + authenticatedUser.lastName);
                if(authenticatedUser.persimissonsId == "admin"){
                    $('#users_box').show();
                    $('#all_meetings_box').show();
                }
                checkIfLoggedIn(authenticatedUser.id);
                if (completeCallbackFunction != null) {
                    completeCallbackFunction();
                }
            }
        }
    );
}

