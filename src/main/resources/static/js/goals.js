function printGoalsTable(goals) {
    $("#goalsTable tbody").empty();
    var goalLines = []
    for (var k in goals) {
        goalLines.push(generateGoalLine(goals[k], k))
    }
    $("#goalsTable").find("tbody").html(goalLines.join("\n"))
    handleEvents()
}

function handleEvents() {
    $('.removeGoal').off().on("click", removeGoalRequest)
    $('.changeState').off().on("click", changeStateRequest)
    $('#addGoal').off().on("click", addGoalForm)
    $('.goalName').off().editable("/rest/goal/updateName", {
        id: "goalId",
        name: "name",
//        event: "mouseover",
        onblur: "submit",
        placeholder: "...",
        callback: function(value) {
            loadGoals(currentSprintId, printGoalsTable)
        }
    })
    $('.goalOwners').off().editable("/rest/goal/updateOwners", {
        id: "goalId",
        name: "owners",
//        event: "mouseover",
        onblur: "submit",
        placeholder: "...",
        callback: function(value) {
            loadGoals(currentSprintId, printGoalsTable)
        }
    })
}

function addGoalForm(event) {
    var attr = $('#goalsTable').find('tr:last').attr('data-goal-id')
    if ($('#goalsTable').find('tr').length <= 1 || (typeof attr !== typeof undefined && attr !== false)) {
        // create new goal form table line
        $('#goalsTable').find('> tbody:last-child').append(goalInlineForm())
        $('#newGoalName').focus()

        // submit new goal form
        $('#goalsForm').off().on("submit", function() {
            addGoalRequest()
            return false
        })

        // cancel button in new goal form
        $('#cancelNewGoalForm').off().on("click", function() {
            $("#newGoalLine").remove()
        })
    }
}

function changeStateRequest(event) {
    $.ajax({
        url: "/rest/goal/toggleState",
        method: "POST",
        data: {
            "goalId": $(event.target.closest('tr')).attr("data-goal-id")
        }
    }).done(function() {
        loadGoals(currentSprintId, printGoalsTable)
    })
}

function removeGoalRequest(event) {
    $.ajax({
        url: "/rest/sprint/removeGoal",
        method: "POST",
        data: {
            "sprintId": currentSprintId,
            "goalId": $(event.target.closest('tr')).attr("data-goal-id")
        }
    }).done(function() {
        loadGoals(currentSprintId, printGoalsTable)
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
        loadGoals(currentSprintId, printGoalsTable)
    })
}

function generateGoalLine(goal, count) {
    var line = []
    var btn = "default"
    var icon = "knight"
    switch(goal.state) {
        case "Healthy":
            btn = "info"
            icon = "leaf"
            break;
        case "Risk":
            btn = "danger"
            icon = "warning-sign"
            break;
        case "Done":
            btn = "success"
            icon = "ok"
            break;
    }
    line.push("<tr data-goal-id='" + goal.id + "'>")
        line.push("<td>")
            line.push("<button type='button' class='btn btn-" + btn + " btn changeState' role='button'>")
                line.push("<span class='glyphicon glyphicon-" + icon + "' aria-hidden='true'></span>")
            line.push("</button>")
        line.push("</td>")
        line.push("<td class='goalName' id='" + goal.id + "-name'>" + goal.name + "</td>")
        line.push("<td class='goalOwners' id='" + goal.id + "-name'>" + goal.owners + "</td>")
        line.push("<td>")
            line.push("<button type='button' class='btn-xs btn-danger removeGoal'>")
                line.push("<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>")
            line.push("</button>")
        line.push("</td>")
    line.push("</tr>")
    return line.join("\n")
}

function goalInlineForm(lineNo) {
    var form = []
    form.push("<tr id='newGoalLine'>")
        form.push("<td></td>")
        form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalName' placeholder='Goal name' required /></td>")
        form.push("<td><input type='text' class='form-control col-lg-12' id='newGoalOwners' placeholder='Owners' /></td>")
        form.push("<td>")
            form.push("<button type='submit' class='btn btn-success hidden' id='saveGoal'>")
                form.push("<span class='glyphicon glyphicon-floppy-disk' aria-hidden='true'></span>")
            form.push("</button>")
            form.push("<button type='button' class='btn btn-danger' id='cancelNewGoalForm'>")
                form.push("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>")
            form.push("</button>")
        form.push("</td>")
    form.push("</tr>")
    return form.join("\n")
}

function loadGoals(sprintId, printFunction) {
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