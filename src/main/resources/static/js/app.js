var currentSprintId = null;

$(document).ready(function() {
    loadSprint(loadSprintRelated)
    loadReleases()
    loadSlas()

    $.ajax({
        url: "/rest/noteboard"
    }).done(function(t) {
        $("#noteboard").val(t)
    })

    $("#noteboard").on("input propertychange", function(e) {
        $.ajax({
            url: "/rest/noteboard/save",
            method: "POST",
            data: {
                "text": $("#noteboard").val()
            }
        })
    })
})

function loadSprintRelated() {
    loadGoals(currentSprintId, printGoalsTable)
}