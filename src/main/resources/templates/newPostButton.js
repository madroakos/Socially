const newPost = document.getElementById("newPost");
const newPostButton = document.getElementById("newPostButton");
const newPostComment = document.getElementById("newPostComment");
const remainingCounter = document.getElementById('remainingCounter');
const submitButton = document.getElementById('submitButton');

function toggleNewPostDropDown() {
    newPost.classList.toggle('hidden');
    newPostButton.classList.toggle('rotateNewPostButton');
}

function updateRemainingCounter() {
    remainingCounter.innerText = (200 - newPostComment.value.length).toString();
}

function sendPostRequest() {
    fetch('http://localhost:8080/submitPost', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: 'Péter', postContent: newPostComment.value })
    })
        .then(response => console.log(response.ok ? 'Post saved successfully' : response.json()));
    toggleNewPostDropDown();
    location.reload();
}

newPostButton.addEventListener('click', () => {toggleNewPostDropDown()});
newPostComment.addEventListener('input', updateRemainingCounter);
submitButton.addEventListener('click', sendPostRequest);