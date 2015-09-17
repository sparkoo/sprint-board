var currentSprintId = null;


$(document).ready(function() {
    $.ajax({
        url: "/rest/sprint/getCurrent"
    }).done(function(data) {
        currentSprintId = data
        load()
    })
});

function load() {
    getGoals(currentSprintId, printGoalsTable)

    $('#team-name').editable("/rest/team", {
        callback: function(value) {
            document.title = value + " - Sprintboard"
        }
    })
    $('#sprint-name').editable("/rest/sprint/saveName")
}
