var activeSprint;
$.ajax({
    url: "/rest/sprint/getActive",
}).done(function(data) {
    activeSprint = data
})

$(document).ready(function() {
    $('#team-name').editable("/rest/team")
    $('#sprint-name').editable("/rest/sprint/saveName")
});