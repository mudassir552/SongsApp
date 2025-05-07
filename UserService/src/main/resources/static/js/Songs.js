
const songsData = document.querySelector('.SongsContainer').getAttribute('data-songs');
const songsContainer = document.querySelector('.SongsContainer');
const songs = JSON.parse(songsData);

const searchBtn=document.querySelector('.search-btn');
console.log(songs);
let audio=null;

console.log(songs.length);

let html="";





searchBtn.addEventListener("click", (e) => {
  e.preventDefault();



  const inputText = document.querySelector(".input-text").value;

 if (!inputText || inputText.trim().length === 0) {
   window.location.href = "/user/userSongs";
   return;
 }


  console.log(inputText);

 console.log("Hitting backend from 2");
 fetch(`/user/api/youtube/videos/${encodeURIComponent(inputText)}`)

    .then(res => res.json())
    .then(data => {
      songsContainer.innerHTML = "";

      data.items.forEach(video => {
        const videoId = video.id.videoId;


        const videoDiv = document.createElement("div");
        videoDiv.className = "video-card";

        videoDiv.innerHTML = `
          <br>
          <iframe width="560" height="315" style="margin: 30px 40px;"
            src="https://www.youtube.com/embed/${videoId}"
            frameborder="0" allowfullscreen>
          </iframe>
          <br>
        `;

        songsContainer.appendChild(videoDiv);
      });
    })
    .catch(error => console.error("Error fetching YouTube data:", error));
});












for(let i=0;i<songs.length;i++){
	html+=`
  <br>
   <div class="card" >
  <div class="card-body">

     <h2 style="text-align:center;">${songs[i].song}</h2>

                <div class="imageContainer">
                <img  class="Songimage" src="data:image/png;base64,${songs[i].image}"  style="height:300px;"alt="Song Image">

                </div>
                 <audio controls preload="none" class="audio" style="max-width:80%; display:block; margin:20px auto;">
                               <source src="data:audio/mpeg;base64,${songs[i].songFile}" type="audio/mpeg">
                               Your browser does not support the audio element.
                           </audio>

  </div>
</div>
<br>
   `
   console.log(songs[i].songFile);

   }


     songsContainer.innerHTML=html;


   function playAudioSongs(){
   document.querySelectorAll('.audio').forEach(clickedAudio => {
       clickedAudio.addEventListener('click', () => {
           document.querySelectorAll('.audio').forEach(audio => {
               if (audio !== clickedAudio) {
                   audio.pause();
                   audio.currentTime = 0;
               }
           });

           clickedAudio.play().catch(error => {
               console.error("Playback failed:", error);
           });
       });
   });
  playAudioSongs();
   }








    setTimeout(function() {

     //SongsContainer.innerHTML=html;


    // Your code here
    audio = document.querySelectorAll(".audio");

      console.log("from dom content")
            audio.forEach(S =>{
			S.addEventListener('play',()=>{

				audio.forEach(other=>{
					if(other!==S){
						other.pause();

					}
				});
			});
		});
},200);






function openOverlay() {
  document.getElementById("overlay").classList.add("active");
}

function closeOverlay() {
  document.getElementById("overlay").classList.remove("active");
}






