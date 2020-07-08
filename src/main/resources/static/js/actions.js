function toggle_visibility(e) {
    var content = e.target.nextElementSibling;
    if (content.getAttribute("data-state") === "closed") {
        content.setAttribute("data-state", "open");
        e.target.setAttribute("data-toggle", "on");
    }
    else {
        content.setAttribute("data-state", "closed");
        e.target.setAttribute("data-toggle", "off");
    }
}
var btns = document.querySelectorAll(".dropbtn");
for (var i = 0, len = btns.length; i < len; i++) {
    btns[i].addEventListener("click", toggle_visibility);
}

<!-- dropdown click -->

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}
document.getElementById("myDropdown").addEventListener('click', function (event) {
    event.stopPropagation();
});
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}