$(function() {
    $('#computerForm').on('submit', function(event) {
        var name = document.forms["computerForm"]["computerName"].value;
        if (name == "") {
            event.preventDefault();
            alert("Name must be filled out");
            return false;
        }
        var introduced = document.forms["computerForm"]["introduced"].value;
        if (introduced != "" && isNaN(Date.parse(introduced))) {
            console.log("wrong date");
            event.preventDefault();
            alert("Introduced date is incorrect");
            return false;
        }
        var discontinued = document.forms["computerForm"]["discontinued"].value;
        if (discontinued != "" && isNaN(Date.parse(discontinued))) {
            console.log("wrong date");
            event.preventDefault();
            alert("Discontinued date is incorrect");
            return false;
        }
    });
    
    $('#computerName').on('input', function() {
        var elem = $(this);
        var parent = elem.parent().first();

        if (elem.val() == "") {
            parent.addClass('has-error');
            parent.removeClass('has-success');
        } else {
            parent.addClass('has-success');
            parent.removeClass('has-error');
        }
    });
});