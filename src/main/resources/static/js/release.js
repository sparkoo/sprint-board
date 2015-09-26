function loadReleases() {
    $("#addReleaseRow").off().on("click", addReleaseRow)
    loadReleasesTable()
}

function loadReleasesTable() {
    $.ajax({
        url: "/rest/release/getAll"
    }).done(printReleaseTable)
}

function printReleaseTable(data) {
    $("#releaseTable").find("> tbody").empty()
    var rlsLines = []
    for (var k in data) {
        rlsLines.push("<tr data-release-id='" + data[k].id + "'>")
        rlsLines.push("<td>" + data[k].version + "</td>")
        rlsLines.push("<td>" + data[k].codefreeze + "</td>")
        rlsLines.push("<td>" + data[k].release + "</td>")
        rlsLines.push("<td>")
            rlsLines.push("<button type='button' class='btn btn-xs btn-danger removeRelease'>")
                rlsLines.push("<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>")
            rlsLines.push("</button>")
        rlsLines.push("</td>")
        rlsLines.push("</tr>")
    }
    $("#releaseTable").find("> tbody").html(rlsLines.join("\n"))

    $(".removeRelease").off().on("click", removeRelease)
}

function removeRelease(event) {
    $.ajax({
        url: "/rest/release/remove",
        method: "POST",
        data: {
            "id": $(event.target.closest('tr')).attr("data-release-id")
        }
    }).done(loadReleasesTable)
}

function addReleaseRow() {
    if ($("#newReleaseRow").length == 0) {
        $('#releaseTable').find('> tbody:last-child').append(releaseRowForm())
        $('#newReleaseVersion').focus()

        $("#newCodefreezeDate").off().datepicker({
            format: "d.m.yyyy",
            weekStart: 1,
            autoclose: true,
            todayHighlight: true
        })

        $("#newReleaseDate").off().datepicker({
            format: "d.m.yyyy",
            weekStart: 1,
            autoclose: true,
            todayHighlight: true
        })

        $("#releaseForm").off().on("submit", function() {
            $.ajax({
                url: "/rest/release/save",
                method: "POST",
                data: {
                    "version": $("#newReleaseVersion").val(),
                    "codefreeze": $("#newCodefreezeDate").val(),
                    "release": $("#newReleaseDate").val()
                }
            }).done(function(data) {
                loadReleasesTable()
            })
            return false
        })
    }
}

function releaseRowForm() {
    var rlsForm = []
    rlsForm.push("<tr id='newReleaseRow'>")
        rlsForm.push("<td>")
            rlsForm.push("<input type='text' class='form-control col-lg-12' id='newReleaseVersion' placeholder='x.xx' required='true' />")
        rlsForm.push("</td>")
        rlsForm.push("<td>")
            rlsForm.push("<input type='text' class='form-control col-lg-12' id='newCodefreezeDate' required='true' />")
        rlsForm.push("</td>")
        rlsForm.push("<td>")
            rlsForm.push("<input type='text' class='form-control col-lg-12' id='newReleaseDate' required='true' />")
        rlsForm.push("</td>")
        rlsForm.push("<td>")
            rlsForm.push("<button type='submit' class='btn btn-xs btn-info' id='newReleaseSave'>")
                rlsForm.push("<span class='glyphicon glyphicon-floppy-disk' aria-hidden='true'></span>")
            rlsForm.push("</button")
        rlsForm.push("</td>")
    rlsForm.push("</tr>")
    return rlsForm.join("\n")
}


//<!--<tr id="newReleaseRow">-->
//    <!--<td>-->
//        <!--<input type='text' class='form-control col-lg-12' id='newReleaseVersion' placeholder='x.xx' required="true" />-->
//    <!--</td>-->
//    <!--<td>-->
//        <!--<input type='text' class='form-control col-lg-12' id='newCodefreezeDate' required="true" />-->
//    <!--</td>-->
//    <!--<td>-->
//        <!--<input type='text' class='form-control col-lg-12' id='newReleaseDate' required="true" />-->
//    <!--</td>-->
//    <!--<td>-->
//        <!--<button type="button" class="btn btn-xs btn-info" id="newReleaseAdd">-->
//            <!--<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>-->
//        <!--</button>-->
//    <!--</td>-->
//<!--</tr>-->