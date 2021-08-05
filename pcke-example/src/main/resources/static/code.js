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