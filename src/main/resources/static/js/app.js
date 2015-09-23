var currentSprintId = null;


$(document).ready(function() {
    $.ajax({
        url: "/rest/sprint/getCurrent"
    }).done(function(data) {
        currentSprintId = data
        load()
    })
    $('#from').datepicker({
        format: "dd.mm.yyyy",
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
                "fromValue": parseInt(e.timeStamp / 1000)
            }
        }).done(function() {
            $('#from').text(e.format())
        })
        $('#from').text(e.format())
    })

    $('#to').datepicker({
        format: "dd.mm.yyyy",
        weekStart: 1,
        todayBtn: "linked",
        autoclose: true,
        todayHighlight: true
    }).on("changeDate", function(e) {
        console.log(e)
        $.ajax({
            url: "/rest/sprint/updateTo",
            method: "POST",
            data: {
                "sprintId": currentSprintId,
                "toValue": parseInt(e.timeStamp)
            }
        }).done(function() {
            $('#to').text(e.format())
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
