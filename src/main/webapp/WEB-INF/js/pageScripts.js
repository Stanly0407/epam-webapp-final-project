function addArtist() {
    var itm = document.getElementById("authorSelector");
    var cln = itm.cloneNode(true);
    document.getElementById("additionalAuthorSelector").appendChild(cln);
}