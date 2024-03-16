const searchBar = document.getElementById("searchBar");


function onFocus () {
    document.getElementById("dropDown").classList.add("show");
}
function onFocusOut () {
    setTimeout(function() {
    document.getElementById("dropDown").classList.remove("show");
    document.getElementById("searchBar").value = null;
    removeAllChildNodes(document.getElementById("dropDown"));
    }, 100);
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function loadPostsFromSelectedUser(user) {
    removeAllChildNodes(document.getElementById('posts-container'));
    document.getElementById('newPostButton').remove();
    loadPostsByUser(user);
}

document.addEventListener("DOMContentLoaded", function() {
    searchBar.addEventListener('input', function() {
        let dropDown = document.getElementById('dropDown');

        removeAllChildNodes(dropDown);

        if (searchBar.value.length === 0) {
            removeAllChildNodes(dropDown);
        } else {
            fetch(`http://localhost:8080/searchForUser?username=${searchBar.value}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.length !== 0) {
                        data.forEach(user => {
                            let currentResult = document.createElement('a');
                            currentResult.innerText = user.username;
                            currentResult.setAttribute('href', `javascript:void(0);`);
                            currentResult.addEventListener('click', function() {
                                loadPostsFromSelectedUser(user.username);
                            });
                            dropDown.appendChild(currentResult);
                        })
                    }
                })
        }
    });
});
