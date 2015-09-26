var currentSprintId = null;

function formatDate(date) {
    return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear()
}

$(document).ready(function() {
    $.ajax({
        url: "/rest/sprint/getCurrent"
    }).done(function(data) {
        currentSprintId = data
        load()
    })
    $.ajax({
        url: "/rest/sprint/getFrom"
    }).done(function(date) {
        $('#sprintFrom').text(formatDate(new Date(date)))
    })
    $.ajax({
        url: "/rest/sprint/getTo"
    }).done(function(date) {
        $('#sprintTo').text(formatDate(new Date(date)))
    })

    $('#sprintFrom').datepicker({
        format: "d.m.yyyy",
        weekStart: 1,
        todayBtn: "linked",
        autoclose: true,
        todayHighlight: true
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
        console.log(e)
        console.log(e.date.getTime())
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

    $("#newCodefreezeDate").datepicker({
          format: "dd.mm.yyyy",
          weekStart: 1,
          autoclose: true,
          todayHighlight: true
      })

      $("#newReleaseDate").datepicker({
          format: "dd.mm.yyyy",
          weekStart: 1,
          autoclose: true,
          todayHighlight: true
      })
});

function load() {
    loadGoals(currentSprintId, printGoalsTable)
    loadSlas()

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
