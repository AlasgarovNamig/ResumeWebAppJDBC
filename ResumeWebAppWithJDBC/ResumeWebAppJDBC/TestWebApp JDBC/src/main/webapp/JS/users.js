function writeWhatIamTyping() {
    var input = document.getElementById("whatIamTyping");
    var text = document.getElementById("typing");

    var inputStr = input.value;
    text.innerHTML = inputStr;
}


function changeColor() {
    var btnSearch =   document.getElementById("btnSearch");
    btnSearch.style = "background-color:red";
}

function showHide() {
    var btnSearch =   document.getElementById("btnSearch");
    btnSearch.style = "display:none";

}

function setIdForDelete(id) {
    var elem = document.getElementById("idForDelete");
    elem.value = id;
}