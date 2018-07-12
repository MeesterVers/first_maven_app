var city = '';
var directions = ["Noord", "Noordoost", "Oost", "Zuidoost", "Zuid", "Zuidwest", "West", "Noordwest"];

function initPage() {
    var keys = ["country", "country_name", "region", "city", "postal", "latitude", "longitude", "ip"];
    fetch('https://ipapi.co/json/')
        .then(response => response.json())
        .then(function (data) {
            for (const key of keys) {
                document.querySelector("#" + key).appendChild(document.createTextNode(data[key]));
            }
            document.getElementById("location").addEventListener('click', function () {
                showWeather(data["city"]);
            });
            showWeather(data["city"]);
            loadCountries();
            toevoegenLand();
            wijzigenLand();
            inloggen();
        });
}

function loadCountries() {
    fetch('restservices/countries/')
        .then(response => response.json())
        .then(function (data) {
            for (const country of data) {
                var row = document.createElement("tr");
                row.setAttribute("id", country.capital);
                row.addEventListener("click", function () {
                    if (this.id === "Antarctica") {
                        alert("No weather found for this city!")
                    } else {
                        showWeather(this.id);
                    }
                });

                var countryCollumn = document.createElement("td");
                var name = document.createTextNode(country.name);
                countryCollumn.appendChild(name);
                row.appendChild(countryCollumn);

                var capitalCollumn = document.createElement("td");
                var capital = document.createTextNode(country.capital);
                capitalCollumn.appendChild(capital);
                row.appendChild(capitalCollumn);

                var regionCollumn = document.createElement("td");
                var region = document.createTextNode(country.region);
                regionCollumn.style.whiteSpace = "nowrap";
                regionCollumn.appendChild(region);
                row.appendChild(regionCollumn);

                var surfaceCollumn = document.createElement("td");
                var surface = document.createTextNode(country.surface);
                surfaceCollumn.appendChild(surface);
                row.appendChild(surfaceCollumn);

                var populationCollumn = document.createElement("td");
                var population = document.createTextNode(country.population);
                populationCollumn.appendChild(population);
                row.appendChild(populationCollumn);

                var deleteCollumn = document.createElement("td");
                var deleteElement = document.createElement("input");
                deleteElement.setAttribute("type", "button");
                deleteElement.setAttribute("id", country.code);
                deleteElement.setAttribute("value", "Verwijder");
                deleteCollumn.appendChild(deleteElement);
                row.appendChild(deleteCollumn);

                deleteElement.addEventListener('click', function () {
                	var fetchOptions = {method: 'DELETE', headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")}};
                    fetch("restservices/countries/" + this.id, fetchOptions)
                        .then(function (response) {
                            if (response.ok) {
                                alert("Country deleted, refresh the page!");
                                loadCountries();
                            } else alert("Country could not be deleted");
                        })

                });

                var updateCollumn = document.createElement("td");
                var updateElement = document.createElement("input");
                updateElement.setAttribute("type", "button");
                updateElement.setAttribute("id", country.code);
                updateElement.setAttribute("value", "Wijzig");
                updateCollumn.appendChild(updateElement);
                row.appendChild(updateCollumn);

                updateElement.addEventListener('click', function () {
                    document.getElementById("codeInput").value = country.code;
                    document.getElementById("landInput").value = country.name;
                    document.getElementById("hoofdstadInput").value = country.capital;
                    document.getElementById("regioInput").value = country.region;
                    document.getElementById("oppervlakteInput").value = country.surface;
                    document.getElementById("inwonersInput").value = country.population;
                    document.getElementById("landInput").focus();

                });


                document.querySelector("#tabel").appendChild(row);
                var table, rows, switching, i, x, y, shouldSwitch;
                table = document.getElementById("tabel");
                switching = true;
                while (switching) {
                    switching = false;
                    rows = table.getElementsByTagName("tr");
                    for (i = 1; i < (rows.length - 1); i++) {
                        shouldSwitch = false;
                        x = rows[i].getElementsByTagName("td")[0];
                        y = rows[i + 1].getElementsByTagName("td")[0];
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break;
                        }
                    }
                    if (shouldSwitch) {
                        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                        switching = true;
                    }
                }
            }
        });
}

function toevoegenLand() {
	console.log("toevoegen");
    document.querySelector("#opslaan").addEventListener('click', function () {
        var formData = new FormData(document.querySelector("#formOpslaan"));
        var encData = new URLSearchParams(formData.entries());
        var fetchOptions = {method: "POST", body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")}};
        fetch("restservices/countries", fetchOptions)
            .then(response => response.json())
            .then(function (myJson) {
            	
                console.log(myJson);
                location.reload();
            })
    });
}

function wijzigenLand() {
	console.log("wijzigen");
    document.querySelector("#wijzigen").addEventListener('click', function () {
    	var code = document.querySelector("#codeInput").value;
        var formData = new FormData(document.querySelector("#formWijzigen"));
        var encData = new URLSearchParams(formData.entries());
        var fetchOptions = {method: "PUT", body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")}};
        fetch("restservices/countries/" + code, fetchOptions)
            .then(response => response.json())
            .then(function (myJson) {
                console.log(myJson);
                location.reload();
            })
    });
}

function inloggen(){
    document.querySelector("#inloggen").addEventListener('click', function () {
    	var user = document.querySelector("#gebruikersnaam").value;
    	var pass = document.querySelector("#wachtwoord").value;
    	window.sessionStorage.setItem("username", user);
    	window.sessionStorage.setItem("password", pass);
        var formData = new FormData(document.querySelector("#formuser"));
        var encData = new URLSearchParams(formData.entries());
        fetch("restservices/authentication", {method: 'POST', body: encData})
            .then(function (response) {
            	if (response.ok) {
                    location.reload();
                    alert("Je bent ingelogd.");
                    return response.json();
            	} else {
            		alert("Wrong username/password");
            		throw "Wrong username/password";
            	}})
            .then(myToken => window.sessionStorage.setItem("sessionToken", myToken.JWT))
            .catch(error => console.log(error));
    });
}

function parseWeather(data) {
    var keys = ["temp", "humidity", "speed", "deg", "sunrise", "sunset"];
    var value;
    for (const key of keys) {
        switch (key) {
            case "temp":
                value = Math.round((data["main"][key] - 273.15) * 10) / 10;
                break;
            case "speed":
                value = data["wind"][key];
                break;
            case "deg":
                value = getDirection(data["wind"][key]);
                break;
            case "sunrise":
            case "sunset":
                value = getTime(data["sys"][key]);
                break;
            default:
                value = data["main"][key];
                break;
        }
        document.getElementById(key).innerHTML = value;
    }
}

function showWeather(capital) {
    if (capital === "Washington DC") {
        capital = "Washington";
    }
    document.getElementById("weer").innerHTML = "Het weer in " + capital;
    if (window.localStorage.getItem(capital) != null && JSON.parse(window.localStorage.getItem(capital)).name === capital && JSON.parse(window.localStorage.getItem(capital)).time > new Date().getTime()) {
        console.log("LOCAL STORAGE DATA");
        var data = JSON.parse(window.localStorage.getItem(capital));
        parseWeather(data);
    } else {
        console.log("FETCH DATA");
        fetch('http://api.openweathermap.org/data/2.5/weather?q=' + capital + '&appid=a6cc5eacb0f2f64e891020834cc198c1')
            .then(response => response.json())
            .then(function (data) {
                data["time"] = new Date().getTime() + 600000;
                window.localStorage.setItem(capital, JSON.stringify(data));
                parseWeather(data);
            });
    }
}

function getDirection(degrees) {
    return directions[Math.floor((degrees % 360) / 45)];
}

function getTime(seconds) {
    var date = new Date(seconds * 1000);
    return date.toLocaleTimeString();
}

initPage();