function loadSprint(whenLoaded) {
    $.ajax({
        url: "/rest/sprint/getCurrent"
    }).done(function(data) {
        currentSprintId = data
        whenLoaded()
    })

    loadDates()

    $('#team-name').editable("/rest/team", {
        callback: function(value) {
            document.title = value + " - Sprintboard"
            loadSlas()
        }
    })
    $('#sprint-name').editable("/rest/sprint/saveName", {
        callback: function () {
            loadSlas()
        }
    })
}

function loadDates() {
    $.ajax({
        url: "/rest/sprint/dates"
    }).done(function(date) {
        $('#sprintFrom').text(formatDate(new Date(date._1)))
        $('#sprintTo').text(formatDate(new Date(date._2)))
    })

    $('#sprintFrom').datepicker({
        format: "d.m.yyyy",
        weekStart: 1,
        todayBtn: "linked",
        autoclose: true,
        todayHighlight: true,
        toggleActive: true
    }).on("changeDate", function(e) {
        $.ajax({
            url: "/rest/sprint/updateFrom",
            method: "POST",
            data: {
                "sprintId": currentSprintId,
                "fromValue": e.date.toJSON()
            }
        }).done(function() {
            $('#sprintFrom').text(e.format())
        })
    })

    $('#sprintTo').datepicker({
        format: "d.m.yyyy",
        weekStart: 1,
        todayBtn: "linked",
        autoclose: true,
        todayHighlight: true
    }).on("changeDate", function(e) {
        $.ajax({
            url: "/rest/sprint/updateTo",
            method: "POST",
            data: {
                "sprintId": currentSprintId,
                "toValue": e.date.toJSON()
            }
        }).done(function() {
            $('#sprintTo').text(e.format())
        })
    })
}

function formatDate(date) {
    return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear()
}