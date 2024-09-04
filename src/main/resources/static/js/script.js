console.log("Script loaded");

//change theme work
let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
})

function changeTheme(){
    //Set the webpage
    changePageTheme(currentTheme,"");
    //Set listener to the change theme button
    const changeThemeButton = document.querySelector('#theme_change_button');

    changeThemeButton.addEventListener("click", function(event){

        let oldTheme = currentTheme;

        if(currentTheme === "dark"){
            currentTheme = "light";
        }else{
            currentTheme = "dark";
        }
        changePageTheme(currentTheme,oldTheme);
});
}


//Setting theme in local storage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

//Getting Theme from the local storage
function getTheme(){
    let theme = localStorage.getItem("theme");
     return theme ? theme : "light";
}

//Change Page theme

function changePageTheme(theme,oldTheme){
    //update current theme in local storage
    setTheme(currentTheme);
    //remove current theme
    if(oldTheme){
    document.querySelector("html").classList.remove(oldTheme);
    }
    // Set the current theme
    document.querySelector("html").classList.add(theme);

    //Change the text of button
   document.querySelector("#theme_change_button").querySelector("span").textContent = theme ==="light" ? "dark" : "light";
}