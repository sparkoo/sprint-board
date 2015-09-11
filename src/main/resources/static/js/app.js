var activeSprint;
$.ajax({
    url: "/rest/sprint/getCurrent"
}).done(function(data) {
    activeSprint = data
})

$(document).ready(function() {
    $('#team-name').editable("/rest/team")
    $('#sprint-name').editable("/rest/sprint/saveName")
});