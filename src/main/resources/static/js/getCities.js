
var startingCities = [];
var destinationCities = [];

function fetchStartingCities(state) {
    fetch("/data/city/names/" + state)
        .then(response => response.json())
        .then(data => {
            console.log("Setting starting cities for state " + state);
            startingCities = data;
            autocomplete(document.getElementById("startingCityInput"), startingCities);
        })
        .catch(err => {
            console.error('An error occurred: ', err);
        });
}

function fetchDestinationCities(state) {
    fetch("/data/city/names/" + state)
        .then(response => response.json())
        .then(data => {
            console.log("Setting destination cities for state " + state);
            destinationCities = data;
            autocomplete(document.getElementById("destinationCityInput"), destinationCities);
        })
        .catch(err => {
            console.error('An error occurred: ', err);
        });
}