var currentSprintId = null;

$(document).ready(function() {
    loadSprint(loadSprintRelated)
    loadReleases()
    loadSlas()
});

function loadSprintRelated() {
    loadGoals(currentSprintId, printGoalsTable)
}