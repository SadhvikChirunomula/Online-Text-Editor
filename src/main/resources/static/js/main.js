let defaultFontSize= 16;
const placeholder = document.querySelector('.placeholder');
let fileSelected = "";
var userId = 0;

window.onload=function(){
let defaultFontSize = 16;
const placeholder = document.querySelector('.placeholder');
const text = document.querySelector('#text');

text.addEventListener('blur', function () {
    if (text.value == "" || text.value == null) {
        placeholder.style.animation = 'TEXTin 0.8s steps(20, end) both';
    }
})

text.addEventListener('focus', function () {
    placeholder.style.animation = 'TEXT 0.8s steps(20, end) both';
})
}

function isActive(butt) {
    return document.querySelector('.' + butt).classList.toggle('active');
}

function color(colorId) {
    text.style.color = colorId;
}

const SmallFont = function () {
    defaultFontSize -= 2;
    text.style.fontSize = defaultFontSize + 'px';
}

const Biggerfont = function () {
    defaultFontSize += 2;
    text.style.fontSize = defaultFontSize + 'px';
}

const BoldFont = function bold(bold) {
    if (isActive("bold")) {
        text.style.fontWeight = "bold";
    } else {
        text.style.fontWeight = "normal";
    }
}

const ItalicFont = function italic(italic) {
    if (isActive("italic")) {
        text.style.fontStyle = "italic";
    } else {
        text.style.fontStyle = "normal";
    }
}

const TextLeft = function textLeft(textleft) {
    if (isActive("left")) {
        pTextAlign = text.style.textAlign;
        text.style.textAlign = "left";
        if (document.querySelector(".center").classList.contains("active")) {
            isActive("center");
        }
        if (document.querySelector(".right").classList.contains("active")) {
            isActive("right");
        }
    } else {
        text.style.textAlign = pTextAlign;
    }
}

const TextCenter = function center(center) {
    if (isActive("center")) {
        pTextAlign = text.style.textAlign;
        text.style.textAlign = "center";
        if (document.querySelector(".left").classList.contains("active")) {
            isActive("left");
        }
        if (document.querySelector(".right").classList.contains("active")) {
            isActive("right");
        }
    } else {
        text.style.textAlign = pTextAlign;
    }
}

const TextRight = function textRight(textRight) {
    if (isActive("right")) {
        pTextAlign = text.style.textAlign;
        text.style.textAlign = "right";
        if (document.querySelector(".left").classList.contains("active")) {
            isActive("left");
        }
        if (document.querySelector(".center").classList.contains("active")) {
            isActive("center");
        }
    }
    else { text.style.textAlign = pTextAlign };
}

const MoDe = document.querySelector('.mode i');
const Tools = document.querySelectorAll('button , input ,  label');
const Container = document.querySelector('.container');
let i;

const Mode = function mode(mode) {
    if (isActive("mode")) {
        document.body.style.background = "#f5f5f5";
        text.style.boxShadow = '0 4px 10px rgba(0,0,0,0.2)';
        text.style.color = '#1f1f1f';
        text.style.border = '1px solid rgba(0, 0, 0, 0.6)';
        placeholder.style.color = '#1f1f1f';
        MoDe.setAttribute("class", "fas fa-moon");
        MoDe.classList.add("ModeAnimations");
        Container.classList.add('container-sun');
        for (i = 0; i < Tools.length; i++) {
            Tools[i].style.color = '#1f1f1f';
            Tools[i].style.boxShadow = '0 4px 10px rgba(0,0,0,0.2)';
        }
    } else {
        document.body.style.background = "#1f1f1f";
        text.style.boxShadow = '0 0 28px rgba(0, 0, 0, 0.5)';
        text.style.color = '#f5f5f5';
        text.style.border = '1px solid rgba(255, 255, 255, 0.6)';
        placeholder.style.color = '#f5f5f5';
        MoDe.setAttribute("class", "fas fa-sun");
        MoDe.classList.remove("ModeAnimations");
        Container.classList.remove('container-sun');
        for (i = 0; i < Tools.length; i++) {
            Tools[i].style.color = '#f5f5f5';
            Tools[i].style.boxShadow = '0 0 28px rgba(0, 0, 0, 0.5)';
        }
    }
}

const SaveFile = function () {
    userName =  document.getElementById("username-textarea").value;
    filetext= document.getElementById("text").value
    console.log(filetext)
    console.log("Selected File: "+fileSelected)

    if(fileSelected==""){
    fileSelected="new_"+Math.floor(1000 + Math.random() * 9000)+".txt";
    }

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({  "fileContent": filetext ,  "fileName": fileSelected,  "userId": userId,  "userName": userName});

    console.log("Sending : "+raw)

    var requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
      redirect: 'follow'
    };

    fetch("http://localhost:8080/file/save", requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));

    alert("File Saved Successfully")
//        const parsedResponse=JSON.parse(fileContent)

    document.getElementById("files-list").innerHTML = ""
    getFilesList()
}

function newFile(){
fileSelected=""
document.getElementById("text").value=""
}

async function getFilesList() {
    userName =  document.getElementById("username-textarea").value;
    console.log("Fetching files of User: "+userName+ "with userId"+ userId)
    document.getElementById("files-list").innerHTML = ""
    var filesArray = []

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
      method: 'GET',
      headers: myHeaders,
      redirect: 'follow'
    };

    const response = await fetch("http://localhost:8080/list/files?userId="+userId, requestOptions)
      .then(response => response.json())
      .then(result => filesArray = result)
      .catch(error => console.log('error', error));
      console.log(response);

    console.log("Files Array: "+filesArray)
    filesArray.forEach(async function(item) {
        var fileContent = ""
        let button = document.createElement("button");
        button.innerText = item.file_name
        button.name = item.file_name
        button.id = "file-button"
        button.onclick = async function () {
            console.log("Pressed File: "+item.file_name)
            getFileContent(item)
        };

        var listItem = document.createElement("div");
        listItem.append(button)

    document.getElementById("files-list").appendChild(listItem);
    });
}

async function getFileContent(item){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
//    var raw = JSON.stringify({"fileId":fileId,"userId":userId});
    fileContent = ""
    fileSelected = item.file_name
    var requestOptions = {
      method: 'GET',
      headers: myHeaders,
      redirect: 'follow',
//      body: raw
    };

    const response = await fetch("http://localhost:8080/file/content/id?fileId="+item.file_id+"&userId="+userId, requestOptions)
      .then(response => response.text())
      .then(result => fileContent = result)
      .catch(error => console.log('error', error));

    const parsedResponse=JSON.parse(fileContent)

    console.log("Response of getFileContent : "+parsedResponse)
    console.log("Content in file: "+parsedResponse.file_data);
    document.getElementById("text").value=parsedResponse.file_data;
}


async function registerUser(){
   registerUserResponse = ""
   userName =  document.getElementById("username-textarea").value;
   password =  document.getElementById("password-textarea").value;
   console.log("Trying to Register User "+userName+ " with password "+password)

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({"userName":userName,"password":password,  "userEmail": "string"});

    var requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
      redirect: 'follow'
    };

    const response = await fetch("http://localhost:8080/user/register", requestOptions)
      .then(response => response.text())
      .then(result => registerUserResponse = result)
      .catch(error => console.log('error', error));
    const parsedResponse=JSON.parse(registerUserResponse)

    console.log("Response of Registering User :"+JSON.parse(registerUserResponse).statusMessage)

    if (parsedResponse.statusMessage=="Added user successfully"){
        alert("Registration Successful, Please login to continue")
        refreshUserDetailsRegPage()
        window.location.assign("http://www.localhost:8080/home.html")
    }else if(parsedResponse.statusMessage=="User Already Exists"){
        alert("User Already Exists, try registering with different username")
        refreshUserDetails()
    }
}

async function loginUser(){
   loginUserResponse = ""
   userName =  document.getElementById("username-textarea").value;
   password =  document.getElementById("password-textarea").value;
   console.log("Trying to login User "+userName+ " with password "+password)

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({"userName":userName,"password":password});

    var requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
      redirect: 'follow'
    };

    const response = await fetch("http://localhost:8080/user/login", requestOptions)
      .then(response => response.text())
      .then(result => loginUserResponse = result)
      .catch(error => console.log('error', error));

    console.log("Response of Login User :"+JSON.parse(loginUserResponse))
    const parsedResponse=JSON.parse(loginUserResponse)

    if (parsedResponse.statusMessage=="User Not found"){
        alert("User not found, Please register to continue")
        refreshUserDetails()
    }else if(parsedResponse.statusMessage=="Auth Success"){
        alert("Logged in Successfully")
        userId=parsedResponse.userId
        getFilesList()
    }else if(parsedResponse.statusMessage=="Wrong Password"){
        alert("Authentication Error")
        refreshUserDetails()
    }
}

function refreshUserDetails(){
    document.getElementById("username-textarea").value=""
    document.getElementById("password-textarea").value=""
    document.getElementById("files-list").innerHTML = ""
}

function refreshUserDetailsRegPage(){
    document.getElementById("username-textarea").value=""
    document.getElementById("password-textarea").value=""
}