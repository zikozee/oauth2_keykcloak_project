function generateCodeVerifier(){
    let randomByteArray = new Uint8Array(32);
    window.crypto.getRandomValues(randomByteArray);
    return base64urlEncode(randomByteArray);
}

function generateCodeChallenge(){
    const codeVerifier = generateCodeVerifier();

    let textEncoder = new TextEncoder("US-ASCII");
    let encodedValue = textEncoder.encode(codeVerifier);
    let digest = window.crypto.subtle.digest('SHA-256', encodedValue);

    return base64urlEncode(Array.from(new Uint8Array(digest)));
}


function generateState(length){
    let stateValue = "";
    const alphaNumericCharacters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const alphaNumericCharactersLength = alphaNumericCharacters.length;
    for(let i =0; i < length; i++)
        stateValue += alphaNumericCharacters.charAt(Math.floor(Math.random() * alphaNumericCharactersLength))

    return stateValue
}


function base64urlEncode(sourceValue) {
    let stringValue = String.fromCharCode.apply(null, sourceValue);
    let base64Encoded = btoa(stringValue);
    return base64Encoded.replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
}


function getAuthCode(){
    let state = generateState(30);
    let codeChallenge = generateCodeChallenge();

    let authorizationURL = "http://localhost:8180/auth/realms/appsdeveloperblog/protocol/openid-connect/auth";
    authorizationURL += "?client_id=photo-app-PKCE-client";
    authorizationURL += "&response_type=code";
    authorizationURL += "&scope=openid";
    authorizationURL += "&redirect_uri=http://localhost:8181/authcodeReader.html";
    authorizationURL += "&state=" + state;
    authorizationURL += "&code_challenge=" + codeChallenge;
    authorizationURL += "&code_challenge_method=S256"

    //new browser window
    window.open(authorizationURL, "authorizationRequestWindow", "width=800, height=600, left=200, top=200")
}



                //GENERATING TOKEN

function requestToken(authCode) {
    let codeVerifier = generateCodeVerifier();
    let data = {
        "grant_type": "authorization_code",
        "client_id": "photo-app-PKCE-client",
        "code": authCode,
        "code_verifier": codeVerifier,
        "redirect_uri": "http://localhost:8181/authcodeReader.html"
    };

    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset-UTF-8");
        },
        type: "POST",
        url: "http://localhost:8180/auth/realms/appsdeveloperblog/protocol/openid-connect/token",
        data: data,
        success: postRequestAccessToken,
        dataType: "json"
    });
}

function postRequestAccessToken(data, status, jqXHR){
    console.log(data["access_token"]);
    console.log(data["expires_in"]);
    console.log(data["id_token"]);
    console.log(data["refresh_expires_in"]);
    console.log(data["refresh_token"]);
    console.log(data["scope"]);
    console.log(data["session_state"]);
    console.log(data["token_type"]);
}