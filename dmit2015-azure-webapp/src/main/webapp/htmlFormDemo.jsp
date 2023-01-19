<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

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

    <title>HTML Forms</title>
</head>

<body>

<div class="container">
    <h1>HTML Form</h1>

    <jsp:useBean id="formBean" class="demo.jsp.DemoHtmlForm" scope="request">
        <jsp:setProperty name="formBean" property="*"/>
    </jsp:useBean>

    <form id="form1" method="post">
        <div class="form-group">
            <label for="textValue">Text Value</label>
            <input type="text" class="form-control" id="textValue"
                   name="textValue" value="${formBean.textValue}"/>
        </div>

        <div class="form-group">
            <label for="numberValue">Number Value</label>
            <input type="number" class="form-control" id="numberValue"
                   name="numberValue" value="${formBean.numberValue}"/>
        </div>

        <div class="form-group">
            <label for="selectValue">Select Value</label>
            <select class="form-control" id="selectValue"
                    name="selectValue">
                <option ${formBean.selectSelectionAttribute("Select 1")}>Select 1</option>
                <option ${formBean.selectSelectionAttribute("Select 2")}>Select 2</option>
                <option ${formBean.selectSelectionAttribute("Select 3")}>Select 3</option>
                <option ${formBean.selectSelectionAttribute("Select 4")}>Select 4</option>
                <option ${formBean.selectSelectionAttribute("Select 5")}>Select 5</option>
            </select>
        </div>

        <div class="form-group">
            <label for="multipleSelectValues">Multiple Select Value</label>
            <select multiple class="form-control" id="multipleSelectValues"
                    name="multipleSelectValues">
                <option ${formBean.multipleSelectSelection("Multiple Select 1")}>Multiple Select 1</option>
                <option ${formBean.multipleSelectSelection("Multiple Select 2")}>Multiple Select 2</option>
                <option ${formBean.multipleSelectSelection("Multiple Select 3")}>Multiple Select 3</option>
                <option ${formBean.multipleSelectSelection("Multiple Select 4")}>Multiple Select 4</option>
                <option ${formBean.multipleSelectSelection("Multiple Select 5")}>Multiple Select 5</option>
            </select>
        </div>

        <div class="form-group">
            <strong>Multiple Checkboxes</strong>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox1"
                       name="multipleCheckboxValues" value="check1"
                ${formBean.multipleCheckboxSelection("check1")}>
                <label class="form-check-label" for="inlineCheckbox1">Multiple Check 1</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox2"
                       name="multipleCheckboxValues" value="check2"
                ${formBean.multipleCheckboxSelection("check2")}>
                <label class="form-check-label" for="inlineCheckbox2">Multiple Check 2</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox3"
                       name="multipleCheckboxValues" value="check3"
                ${formBean.multipleCheckboxSelection("check3")}>
                <label class="form-check-label" for="inlineCheckbox3">Multiple Check 3</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox4"
                       name="multipleCheckboxValues" value="check4"
                ${formBean.multipleCheckboxSelection("check4")}>
                <label class="form-check-label" for="inlineCheckbox4">Multiple Check 4</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox5"
                       name="multipleCheckboxValues" value="check5"
                ${formBean.multipleCheckboxSelection("check5")}>
                <label class="form-check-label" for="inlineCheckbox5">Multiple Check 5</label>
            </div>
        </div>

        <div class="form-group">
            <strong>Radios</strong>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="inlineRadio1" value="radio1"
                       name="radioValue"
                ${formBean.radioSelectionAttribute("radio1")}>
                <label class="form-check-label" for="inlineRadio1">Radio 1</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="inlineRadio2" value="radio2"
                       name="radioValue"
                ${formBean.radioSelectionAttribute("radio2")}>
                <label class="form-check-label" for="inlineRadio2">Radio 2</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="inlineRadio3" value="radio3"
                       name="radioValue"
                ${formBean.radioSelectionAttribute("radio3")}>
                <label class="form-check-label" for="inlineRadio3">Radio 3</label>
            </div>
        </div>

        <div class="form-group">
            <label for="textareaValue">Textarea Value</label>
            <textarea class="form-control" id="textareaValue" rows="3"
                      name="textareaValue">${formBean.textareaValue}</textarea>
        </div>

        <div class="form-group">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="checkbox"
                       name="checkbox" ${formBean.isCheckbox() ? "checked='checked'" : ""}>
                <label class="form-check-label" for="checkbox">
                    Check me out
                </label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>

    </form>

    <c:if test="${pageContext.request.method == 'POST'}">
        <b>Text Value:</b>    ${formBean.textValue} <br/>
        <b>Number Value:</b> ${formBean.numberValue} <br/>
        <b>Select Value:</b> ${formBean.selectValue} <br/>
        <b>Checkbox:</b> ${formBean.checkbox} <br/>
        <b>Radio: </b> ${formBean.radioValue} <br/>

        <c:if test="${formBean.multipleSelectValues != null}">
            <strong>Multiple Select Values:</strong>
            <c:forEach var="selectedValue" items="${formBean.multipleSelectValues}">
                ${selectedValue}
            </c:forEach>
            <br/>
        </c:if>

        <c:if test="${formBean.multipleCheckboxValues != null}">
            <strong>Multiple Checkbox Values:</strong>
            <c:forEach var="checkedValue" items="${formBean.multipleCheckboxValues}">
                ${checkedValue}
            </c:forEach>
            <br/>
        </c:if>

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