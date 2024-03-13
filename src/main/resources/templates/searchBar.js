const searchBar = document.getElementById("searchBar");


function onFocus () {
    document.getElementById("dropDown").classList.add("show");
}
function onFocusOut () {
    document.getElementById("dropDown").classList.remove("show");
    document.getElementById("searchBar").value = null;
    removeAllChildNodes(document.getElementById("dropDown"));
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    searchBar.addEventListener('input', function() {
        let dropDown = document.getElementById('dropDown');

        removeAllChildNodes(dropDown);

        if (searchBar.value.length === 0) {
            removeAllChildNodes(dropDown);
        } else {
            fetch(`http://localhost:8080/searchForUser?username=${searchBar.innerText}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.length !== 0) {
                        data.forEach(user => {
                            let currentResult = document.createElement('a');
                            currentResult.innerText = user.username;
                            dropDown.appendChild(currentResult);
                        })
                    }
                })
        }
    });
});
