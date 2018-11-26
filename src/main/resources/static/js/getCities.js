
var startingCities = [];
var destinationCities = [];

function fetchStartingCities(state) {
    fetch("/data/city/names/" + state)
        .then(response => response.json())
        .then(data => {
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
            destinationCities = data;
            autocomplete(document.getElementById("destinationCityInput"), destinationCities);
        })
        .catch(err => {
            console.error('An error occurred: ', err);
        });
}