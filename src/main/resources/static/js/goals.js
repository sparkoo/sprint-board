function printGoalsTable(goals) {
    $("#goalsTable tbody").empty();
    var goalLines = []
    for (var k in goals) {
        goalLines.push(generateGoalLine(goals[k], k))
    }
    $("#goalsTable tbody").html(goalLines.join("\n"));
    handleEvents()
}

function handleEvents() {
    $('.removeGoal').off().on("click", function(event) {
        $.ajax({
            url: "/rest/sprint/removeGoal",
            method: "POST",
            data: {
                "sprintId": currentSprintId,
                "goalId": $(event.target).attr("data-goal-id")
            }
        }).done(function(data) {
            getGoals(currentSprintId, printGoalsTable)
        })
    })

    $('#addGoal').off().on("click", function() {
        var attr = $('#goalsTable tr:last').attr('data-goal-id')
        if ($('#goalsTable tr').length <= 1 || (typeof attr !== typeof undefined && attr !== false)) {
            // next goal number
            var lastNo = $('#goalsTable tr:last td:first').text()
            if (lastNo.length > 0) {
                lastNo = parseInt(lastNo.substring(0, lastNo.length - 1)) + 1
            } else {
                lastNo = 1
            }

            // create new goal form table line
            $('#goalsTable > tbody:last-child').append(goalInlineForm(lastNo))
            $('#newGoalName').focus()

            // submit new goal form
            $('#goalsForm').off().submit("click", function() {
                addGoalRequest()
                return false
            })

            // cancel button in new goal form
            $('#cancelNewGoalForm').off().on("click", function() {
                $("#newGoalLine").remove()
            })
        }
    })
}

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
        getGoals(currentSprintId, printGoalsTable)
    })
}

function generateGoalLine(goal, count) {
    var line = []
    line.push("<tr data-goal-id='" + goal.id + "'>")
        line.push("<td>" + (parseInt(count)+1) + ".</td>")
        line.push("<td>" + goal.name + "</td>")
        line.push("<td>" + goal.owners + "</td>")
        line.push("<td>" + goal.state + "</td>")
        line.push("<td>")
            line.push("<button type='button' class='btn btn-danger removeGoal' data-goal-id='" + goal.id + "'>")
                line.push("<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>")
            line.push("</button>")
            line.push("<button type='button' class='btn btn-warning' data-goal-id='" + goal.id + "'>")
                line.push("<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>")
            line.push("</button>")
            line.push("<button type='button' class='btn btn-info' data-goal-id='" + goal.id + "'>")
                line.push("<span class='glyphicon glyphicon-chevron-right' aria-hidden='true'></span>")
            line.push("</button>")
        line.push("</td>")
    line.push("</tr>")
    return line.join("\n")
}

function goalInlineForm(lineNo) {
    var form = []
    form.push("<tr id='newGoalLine'>")
        form.push("<td>" + lineNo + ".</td>")
        form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalName' placeholder='Goal name' required /></td>")
        form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalOwners' placeholder='Owners' /></td>")
        form.push("<td></td>")
        form.push("<td>")
            form.push("<button type='submit' class='btn btn-success' id='saveGoal'>")
                form.push("<span class='glyphicon glyphicon-floppy-disk' aria-hidden='true'></span>")
            form.push("</button>")
            form.push("<button type='button' class='btn btn-danger' id='cancelNewGoalForm'>")
                form.push("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>")
            form.push("</button>")
        form.push("</td>")
    form.push("</tr>")
    return form.join("\n")
}

function getGoals(sprintId, printFunction) {
    $.ajax({
        url: "/rest/goal/goals",
            method: "GET",
            data: {
                "sprintId": currentSprintId
            }
    }).done(function(data) {
        printFunction(data)
    })
}