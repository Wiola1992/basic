<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
 
	Witaj ${userName} w naszym sklepie internetowym
	</br>
	</br>
	<c:if test="${empty userName}">
		<a href="login"> Zaloguj się</a>
		</br>
		<a href="registration"> Zarejestruj się</a>
		</br>
	</c:if>
	</br>
	<c:if test="${not empty userName}">
		<a href="logout">Wyloguj się</a>
		</br>
	</c:if>
	
	<a href="user"> Użytkownik</a>
	</br>
 	<a href="admin"> Administrator</a>
 	
</body>
</html>