function createPostElement(post) {
    const postDiv = document.createElement('div');
    postDiv.classList.add('post');

    const upperDiv = document.createElement('div');
    upperDiv.classList.add('upperDiv');

    const userParagraph = document.createElement('a');
    userParagraph.classList.add('post_userSection');
    userParagraph.textContent = post.username;
    userParagraph.addEventListener('click', () => loadPostsFromSelectedUser(post.username));

    const submitTime = document.createElement('p');
    submitTime.classList.add('post_submitTimeSection');
    submitTime.textContent = post.timeSince;

    upperDiv.append(userParagraph, submitTime);

    const lowerDiv = document.createElement('div');
    lowerDiv.classList.add('lowerDiv');

    const contentParagraph = document.createElement('p');
    contentParagraph.classList.add('post_contentSection');
    contentParagraph.textContent = post.postContent;

    lowerDiv.append(contentParagraph);

    postDiv.append(upperDiv, lowerDiv);

    return postDiv;
}

function createEmptyElement() {
    const emptyContainer = document.createElement('div');
    emptyContainer.classList.add('emptyContainer');

    const emptyParagraph = document.createElement('p');
    emptyParagraph.textContent = 'No activity yet';

    emptyContainer.append(emptyParagraph);

    return emptyContainer;
}

fetch('http://localhost:8080/posts')
    .then(response => response.json())
    .then(data => {
        const elements = data.length !== 0 ? data.map(createPostElement) : [createEmptyElement()];
        postsContainer.append(...elements);
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });