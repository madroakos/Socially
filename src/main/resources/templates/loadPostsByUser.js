function loadPostsByUser(user) {
    fetch(`http://localhost:8080/postsByUser?username=${user}`)
        .then(response => response.json())
        .then(data => {
            const postsContainer = document.getElementById('posts-container');

            if (data.length !== 0) {

                const userDiv = document.createElement('div');
                userDiv.classList.add('user');
                userDiv.innerText = data[0].username;
                postsContainer.appendChild(userDiv);

                data.forEach(post => {

                    const postDiv = document.createElement('div');
                    postDiv.classList.add('post');

                    const upperDiv = document.createElement('div');
                    upperDiv.classList.add('upperDiv');

                    const lowerDiv = document.createElement('div');
                    lowerDiv.classList.add('lowerDiv');

                    const userParagraph = document.createElement('p');
                    userParagraph.classList.add('post_userSection');
                    const submitTime = document.createElement('p');
                    submitTime.classList.add('post_submitTimeSection');

                    const contentParagraph = document.createElement('p');
                    contentParagraph.classList.add('post_contentSection');

                    userParagraph.textContent = post.username;
                    contentParagraph.textContent = post.postContent;
                    submitTime.textContent = post.timeSince;

                    upperDiv.appendChild(userParagraph);
                    upperDiv.appendChild(submitTime);
                    lowerDiv.appendChild(contentParagraph);

                    postDiv.appendChild(upperDiv);
                    postDiv.appendChild(lowerDiv);

                    postsContainer.appendChild(postDiv);
                });
            } else {
                const emptyContainer = document.createElement('div');
                emptyContainer.classList.add('emptyContainer');
                const emptyParagraph = document.createElement('p');
                emptyParagraph.textContent = 'No activity yet';
                emptyContainer.appendChild(emptyParagraph);

                postsContainer.appendChild(emptyContainer);
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}