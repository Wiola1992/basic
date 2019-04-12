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
 
	<form:form method="POST" modelAttribute="user"  > 
			<table border="1">
				<tr>
					<td> Imię:</td>
					<td> <form:input path="firstName"/> </td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="firstName" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						Nazwisko:
					</td>
					<td>
						<form:input path="lastName"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="lastName" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						Email:
					</td>
					<td>
						<form:input path="email"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="email" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						Hasło:
					</td>
					<td>
						<form:password path="password"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="password" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						Potwierdzenie hasła:
					</td>
					<td>
						<form:password  path="confirmPassword"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="confirmPassword" />
						</c:if>
					</td>
				</tr>
		
				
			</table>
		
			<br />
			
			
			<input type="submit" value="Zarejestruj się" />
		</form:form> 
 <a href="/basicP"> Powrót do strony głównej</a>
</body>
</html>