<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>


    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>

    <title>Interjú kérdések és válaszok</title>


    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js'></script>
    <script type='text/javascript' src='../../resources/js/jquery.ba-hashchange.min.js'></script>
    <script type='text/javascript' src='../../resources/js/dynamicpage.js'></script>
    <link rel='stylesheet' type='text/css' href='../../resources/css/style.css'/>
</head>

<h1 class="mainTitle" >Interjú Kérdések és Válaszok</h1>
<body>

<form:form action="/reset" method="get">
    <button type="submit" class="ansButton">Reset</button>
</form:form>
right: 0;
<?php include('../header.php'); ?>


<form:form action="/nextQuestion" method="get" modelAttribute="question">
<form:hidden path="id"/>
<form:hidden path="answer"/>
<form:hidden path="question"/>

<div id="page-wrap" onclick="javascript:this.parentNode.submit();">
    </form:form>
    <header>
        <h1 align="center">${question.question}</h1>
        <nav>
            <ul class="group">
            </ul>
        </nav>
    </header>

    <section id="main-content">
        <div id="guts">
            <div id="answer" style="display: block"><h2>${question.answer}</h2></div>
        </div>
    </section>

</div>
<footer>
    <div style="color:white; text-align: right;">${count} kérdés van hátra.</div>
    &copy;PROGMasters - Nagy Norbert 2018
</footer>


<script>
    function showElement() {
        var x = document.getElementById("answer");
        x.style.display = "none";
    }</script>
<?php include('../footer.php'); ?>

</body>

</html>