function createTable() {
	// Insert whichever data you need in the array and it will push onto the front
	// Add more rows if needed for more data
	var table = new Array();
	table.push(["City, State", "City, State", "Days", "Email", "Phone number", "Preferred Compensation"]);
	table.push(["City, State", "City, State", "Days", "Email", "Phone number", "Preferred Compensation"]);
	table.push(["City, State", "City, State", "Days", "Email", "Phone number", "Preferred Compensation"]);
	table.push(["City, State", "City, State", "Days", "Email", "Phone number", "Preferred Compensation"]);
	table.push(["City, State", "City, State", "Days", "Email", "Phone number", "Preferred Compensation"]);

	var tableContents = "";
	for (var i = 0; i < table.length; i++) {
		tableContents += "<tr>";
		for (var j = 0; j < table[i].length; j++) {
			tableContents += "<td>" + table[i][j] + "</td>";
		}
		tableContents += "</tr>";
	}
	document.getElementById("tableContent").innerHTML = tableContents;
}