
const container = document.querySelector(".songs-container");

function loadTrendingYoutubeVideos() {

console.log("Hitting backend from 1");
    fetch("/user/api/videos/trending")


        .then(res => res.json())
        .then(data => {
          const songsContainer = document.querySelector(".songs-container");

            data.items.forEach(video => {
                const videoId = video.id.videoId;



                // Create a video card
                const videoDiv = document.createElement("div");
                videoDiv.className = "video-card";

                videoDiv.innerHTML = `
           <br>
                    <iframe width="560" height="315"   style="margin: 30px 40px; "src="https://www.youtube.com/embed/${videoId}"
                        frameborder="0" allowfullscreen></iframe>
                        <br>
                `;

                songsContainer.appendChild(videoDiv);
            });
        })
        .catch(error => {
            console.error("Error loading videos:"+error);
        });
}

 window.onload = loadTrendingYoutubeVideos;


