fetch('http://localhost:8080/posts')
    .then(response => response.json())
    .then(data => {
        // Get the container to display posts
        const postsContainer = document.getElementById('posts-container');

        // Iterate over the list of posts
        data.forEach(post => {
            // Create elements for user and content
            const postDiv = document.createElement('div');
            postDiv.classList.add("post");
            const userParagraph = document.createElement('p');
            userParagraph.classList.add("post_userSection");
            const contentParagraph = document.createElement('p');
            contentParagraph.classList.add("post_contentSection");

            // Set user and content text
            userParagraph.textContent = post.username;
            contentParagraph.textContent = post.postContent;

            // Append user and content elements to postDiv
            postDiv.appendChild(userParagraph);
            postDiv.appendChild(contentParagraph);

            // Append postDiv to postsContainer
            postsContainer.appendChild(postDiv);
        });
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });