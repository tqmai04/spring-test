<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <spring:url value="/style.css" var="springCss" />
    <link href="${springCss}" rel="stylesheet" />
</head>
<body>
<div class="container-fluid" style="padding: 0">
    <div id="header">
        <nav>
            <ul>
                <li><a href="landing_page.html">Projects</a></li>
                <li><a href="create.html">Create</a></li>
            </ul>
        </nav>
    </div>
    <div id="detail">
        <h2 id="heading1">Create new project</h2>
        <div class="form-group row">
            <label class="col-2 col-form-label">Name</label>
            <div class="col-10">
                <input class="form-control" type="text" />
            </div>

        </div>
        <div class="form-group row">
            <label class="col-2 col-form-label">Tags</label>
            <div class="col-8">
                <input class="form-control" type="text" name="tags" id="tags"/>
            </div>
            <div class="col-1">
                <button class="btn" id="add">Add</button>
            </div>
            <ul id="info">

            </ul>
        </div>
        <p align="center"><a href="create-3.html"><button class="btn" id="create">Create</button></a></p>
    </div>
</div>
<script type="text/javascript">
    $( document ).ready( function () {
        $( "#add" ).click( function () {
            var value = $( "#tags" ).val();
            $( "#info" ).append( "<li><button class='btn'>" + value + "</button></li>" );
            $( "#tags" ).val( null );
            $( "#tags" ).attr( "placeholder", "add another tag" );
        } );
    } );
</script>
</body>
</html>