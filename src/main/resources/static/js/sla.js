function loadSlas() {
    $.ajax({
        url: "/rest/issue/sla",
            method: "GET",
    }).done(function(slas) {
        $("#slaTable").find("tbody").empty()
        var slaLines = []
        for (var k in slas) {
            slaLines.push(generateSlaLine(slas[k], k))
        }
        $("#slaTable").find("tbody").html(slaLines.join("\n"))
    }).error(function() {
        $("#slaTable").find("tbody").html("<tr><td></td><td>error when getting SLAs ...</td><td></td><td></td><td></td></tr>")
    })
}

function generateSlaLine(sla) {
    var line = []
    line.push("<tr>")
        line.push("<td>" + sla.task + "</td>")
        line.push("<td>" + sla.name + "</td>")
        line.push("<td>" + sla.assignee + "</td>")
        line.push("<td>" + sla.state + "</td>")
        line.push("<td>" + sla.fixVersion + "</td>")
    line.push("</tr>")
    return line.join("\n")
}