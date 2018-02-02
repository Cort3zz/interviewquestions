<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>


    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>

    <title>Interjú kérdések és válaszok</title>


    <link rel='stylesheet' type='text/css' href='../../resources/css/style.css'/>
</head>

<h1 class="mainTitle">Interjú Kérdések és Válaszok</h1>
<body>

<form:form action="/reset" method="get">
    <button type="submit" class="ansButton">Reset</button>
</form:form>
right: 0;
<?php include('../header.php'); ?>


<form:form action="/nextQuestion" method="post" modelAttribute="question">
<form:hidden path="id"/>
<form:hidden path="answer"/>
<form:hidden path="question"/>

<div id="page-wrap" onclick="showElementAndGetNextQuestion()">
    </form:form>
    <header>
        <h1 align="center">${question.question}</h1>
        <nav>
            <ul class="group">
            </ul>
        </nav>
    </header>

    <section id="main-content" style="display: none;">
        <div id="guts">
            <div id="answer"><h2>${question.answer}</h2></div>
        </div>
    </section>

</div>
<footer>
    <div style="color:white; text-align: right;">${count} kérdés van hátra.</div>
    &copy;PROGMasters - Nagy Norbert 2018
</footer>


<script>
    function showElementAndGetNextQuestion() {
        var x = document.getElementById("main-content");
        if(x.style.display=="none")
            return x.style.display = "block";

            return document.getElementById("page-wrap").parentNode.submit();


    }</script>
<?php include('../footer.php'); ?>

</body>

</html>