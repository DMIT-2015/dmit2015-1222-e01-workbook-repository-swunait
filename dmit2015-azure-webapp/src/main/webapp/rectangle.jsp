<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%--
Add to pom.xml the following the Maven dependencies to rid of the runtime errors with JSTL:
  The absolute uri: [http://java.sun.com/jsp/jstl/core] cannot be resolved in either web.xml or the jar files deployed with this application

<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>2.0.0</version>
</dependency>
--%>

<%-- JSTL core library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- JSTL formatting library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- JSTL functions library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--JSP Tag Reference https://docs.oracle.com/cd/E13226_01/workshop/docs81/pdf/files/workshop/JSPTagsReference.pdf  --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <title>Rectangle Shape Calculator</title>
</head>

<body>

<div class="container">
    <h1>Rectangle Shape Calculator</h1>

    <jsp:useBean id="formBean" class="dmit2015.model.Rectangle" scope="request">
        <jsp:setProperty name="formBean" property="*"/>
    </jsp:useBean>

    <form id="form1" method="post">
        <div class="form-group">
            <label for="length">Length</label>
            <input type="number" class="form-control" id="length"
                   name="length" value="${formBean.length}"/>
        </div>

        <div class="form-group">
            <label for="width">Width</label>
            <input type="number" class="form-control" id="width"
                   name="width" value="${formBean.width}"/>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>

    </form>

    <c:if test="${pageContext.request.method == 'POST'}">
        <b>Area:</b>    ${formBean.area()} <br/>
        <b>Diagonal:</b> ${formBean.diagonal()} <br/>

    </c:if>

    Your IP address is ${pageContext.request.remoteAddr} <br/>

</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V"
        crossorigin="anonymous"></script>

</body>
</html>