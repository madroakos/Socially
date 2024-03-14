
function onNewPostButtonClick () {
    document.getElementById("newPost").classList.toggle('hidden');
    document.getElementById("newPostButton").classList.toggle('rotateNewPostButton');
}

function onSubmitButtonClick() {
    onNewPostButtonClick();
    sendPostRequest();
    document.getElementById("newPostComment").value = null;
    document.getElementById('remainingCounter').innerText = String(200);
}

function countCharacters() {
    let currentCharacterNumber = document.getElementById('newPostComment').value.length;
    document.getElementById('remainingCounter').innerText = (200 - currentCharacterNumber).toString();
}

function sendPostRequest() {
    const url = 'http://localhost:8080/submitPost';

    const data = {
        username: 'Péter',
        postContent: document.getElementById("newPostComment").value
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                console.log('Post saved successfully');
            } else {
                console.log(response.json());
            }
        })

}