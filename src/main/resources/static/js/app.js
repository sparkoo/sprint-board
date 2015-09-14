var currentSprintId;
$.ajax({
    url: "/rest/sprint/getCurrent"
}).done(function(data) {
    currentSprintId = data
})

$(document).ready(function() {
    $('#team-name').editable("/rest/team")
    $('#sprint-name').editable("/rest/sprint/saveName")
    $('.removeGoal').on("click", function(event) {
        $.ajax({
            url: "/rest/sprint/removeGoal",
            method: "POST",
            data: {
                "sprintId": currentSprintId,
                "goalId": $(event.target).attr("data-goal-id")
            }
        }).done(function(data) {
            location.reload()
        })
    })

    $('#addGoal').on("click", function() {
        var attr = $('#goalsTable tr:last').attr('data-goal-id')
        if ($('#goalsTable tr').length <= 1 || (typeof attr !== typeof undefined && attr !== false)) {
            var lastNo = $('#goalsTable tr:last td:first').text()
                if (lastNo.length > 0) {
                    lastNo = parseInt(lastNo.substring(0, lastNo.length - 1)) + 1
                } else {
                    lastNo = 1
                }
                $('#goalsTable > tbody:last-child').append(goalInlineForm(lastNo))
                $(document).keypress(function(e) {
                    if(e.which == 13) {
                        addGoalRequest()
                    }
                });
                $('#saveGoal').on("click", function() {
                    addGoalRequest()
                })
        }
    })
});

function addGoalRequest() {
    $.ajax({
        url: "/rest/sprint/addGoal",
        method: "POST",
        data: {
            "sprintId": currentSprintId,
            "goalName": $("#newGoalName").val(),
            "goalOwners": $("#newGoalOwners").val()
        }
    }).done(function(data) {
        location.reload()
    })
}

function goalInlineForm(lineNo) {
    var form = []
    form.push("<tr><td>" + lineNo + ".</td>")
    form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalName' placeholder='Goal name' /></td>")
    form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalOwners' placeholder='Owners' /></td>")
    form.push("<td></td>")
    form.push("<td><button type='button' class='btn btn-success' id='saveGoal'><span class='glyphicon glyphicon-floppy-disk' aria-hidden='true'></span></button></td></tr>")
    return form.join("")
}