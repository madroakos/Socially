fetch('http://localhost:8080/posts')
    .then(response => response.json())
    .then(data => {
        // Get the container to display posts
        const postsContainer = document.getElementById('posts-container');

        if (data.length !== 0) {
            data.forEach(post => {
                const postDiv = document.createElement('div');
                postDiv.classList.add("post");
                const userParagraph = document.createElement('p');
                userParagraph.classList.add("post_userSection");
                const contentParagraph = document.createElement('p');
                contentParagraph.classList.add("post_contentSection");

                userParagraph.textContent = post.username;
                contentParagraph.textContent = post.postContent;

                postDiv.appendChild(userParagraph);
                postDiv.appendChild(contentParagraph);

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