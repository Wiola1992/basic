<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>

 </br>
 ${msg}
 </br>
 </br>
 <c:if test="${not empty linkResetPassword}">
 	  <a href="user/resetPassword"> Zmień hasło</a>
 </c:if>
 
    <form name='login' action="login" method='POST'>
        <table>
            <tr>
                <td>Email:</td>
                <td><input type='text' name='username' value=''></td>
                <div id="username.errors" class="error">username is required!</div>
            </tr>
            <tr>
                <td>Hasło:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit" value="Zaloguj się" /></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
  </br>
  </br>
 <a href="/basicP"> Powrót do strony głównej</a> 
 </br>
  <a href="registration"> Zarejestruj się</a>
</body>
</html>