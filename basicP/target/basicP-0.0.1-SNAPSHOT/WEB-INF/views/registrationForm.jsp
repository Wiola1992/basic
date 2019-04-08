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
 
	<form:form method="POST" modelAttribute="formLogin"  > 
			<table border="1">
				<tr>
					<td>
						Login:
					</td>
					<td>
						<form:input path="login"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="login" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						Hasło:
					</td>
					<td>
						<form:input path="password"/>
					</td>
				  	<td>
						<c:if test="${pageContext.request.method=='POST'}">
							<form:errors path="password" />
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