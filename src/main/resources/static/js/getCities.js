
var cities = [];

fetch('/data/city/names')
    .then(response => response.json())
    .then(data => {
        cities = data;
        autocomplete(document.getElementById("startingCityInput"), cities);
        autocomplete(document.getElementById("destinationCityInput"), cities);
    })
    .catch(err => {
        console.error('An error ocurred', err);
});

/*
function httpGet(url, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        console.log(xmlHttp.readyState);
        console.log(xmlHttp.status);
        if (xmlHttp.readyState === 4  && xmlHttp.status === 200)
            callback(xmlHttp.responseText);
        else
            console.log("Unable to retrieve xmlHttp response.");
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function handleCityData(responseText) {
    //cities = JSON.parse(responseText);
    console.log(responseText);
}

/*
function getCities(url, success) {
    var ud = '_' + +new Date,
        script = document.createElement('script'),
        head = document.getElementsByTagName('head')[0]
            || document.documentElement;

    window[ud] = function(data) {
        head.removeChild(script);
        success && success(data);
    };

    script.src = url.replace('callback=?', 'callback=' + ud);
    head.appendChild(script);
}

getCities("localhost:8080/data/city/names", function(data){
    console.log(data);
});*/

/*
var getJSON = function(url) {
    return new Promise(function(resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open('get', url, true);
        xhr.responseType = 'json';
        xhr.onload = function() {
            var status = xhr.status;
            if (status == 200) {
                resolve(xhr.response);
            } else {
                reject(status);
            }
        };
        xhr.send();
    });
};

getJSON('localhost:8080/data/city/names').then(function(data) {
    alert('Your Json result is:  ' + data.result); //you can comment this, i used it to debug

    result.innerText = data.result; //display the result in an HTML element
}, function(status) { //error detection....
    alert('Something went wrong.');
});
*/