<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
                <li class="active"><a href="#">Projects</a></li>
                <li><a href="#create">Create</a></li>
            </ul>
        </nav>
    </div>
    <div class="row">
        <table class="table" id="listing">
            <thead>
            <tr>
                <th>ID</th>
                <th>Project Name</th>
                <th>Date</th>
                <th>Size</th>
                <th>Created by</th>
                <th>Last used</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>Project X</td>
                <td>03/02/2017</td>
                <td>50</td>
                <td>QA Tester 1</td>
                <td>04/05/2018</td>
                <td><a href="#">View Detail</a></td>
            </tr>
            <tr>
                <td>2</td>
                <td>Project X</td>
                <td>03/02/2017</td>
                <td>50</td>
                <td>QA Tester 1</td>
                <td>04/05/2018</td>
                <td><a href="#">View Detail</a></td>
            </tr>
            <tr>
                <td>3</td>
                <td>Project X</td>
                <td>03/02/2017</td>
                <td>50</td>
                <td>QA Tester 1</td>
                <td>04/05/2018</td>
                <td><a href="#">View Detail</a></td>
            </tr>
            <tr>
                <td>4</td>
                <td>Project X</td>
                <td>03/02/2017</td>
                <td>50</td>
                <td>QA Tester 1</td>
                <td>04/05/2018</td>
                <td><a href="#">View Detail</a></td>
            </tr>
            </tbody>
        </table></div>
</div>
</body>
</html>