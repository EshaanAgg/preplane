async function userFetch(){
    
    const authToken = localStorage.getItem("preplane-authHeader");
    
    const response = await fetch('/api/users/me', {
      method: 'GET',
      headers: {
        "Authorization": authToken,
        'Content-Type': 'application/json'
      },
    });

    userData = await response.json();

    user = userData;
    var sign = document.getElementById('sign');
    var profile = document.getElementById('profile');
    var name = "Hi " + user.firstName
    var dropdownButton = document.getElementById('dropdownButton');
    var dropdownMenu = document.getElementById('dropdownMenu');
    var profilePage = document.getElementById('profilePage');
    var logged = document.getElementById('logged');
    if(user.firstName !== undefined){
      dropdownButton.innerHTML = name
      if(!sign.classList.contains('hidden')){
        sign.classList.add('hidden');
        logged.classList.add('hidden');
      }
      if(profile.classList.contains('hidden')){
        profile.classList.remove('hidden');
        profilePage.classList.remove('hidden');
      }
    }
    else{
      // var homeUrl 
      // if(!(window.location.href == "/" || window.location.href == "/auth/login" || window.location.href == "/auth/signup")){
      //   alert("You must be logged in to view this page");
      //   window.location.href = '/';
      // }
      if(sign.classList.contains('hidden')){
        sign.classList.remove('hidden');
        logged.classList.remove('hidden');
      }
      if(!profile.classList.contains('hidden')){
        profile.classList.add('hidden');
        profilePage.classList.add('hidden');
      }
    }

      dropdownButton.addEventListener('click', function() {
      dropdownMenu.classList.toggle('hidden');
    });

}

userFetch();

function logout(){
  console.log(localStorage)
  localStorage.clear();
  console.log(localStorage)
  window.location.href = "/";
}