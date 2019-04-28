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

<p> Nie pamiętasz hasła? </br>	
 ${inf}
 
	<form:form method="POST" modelAttribute="emailForm"  > 
			<table border="1">
				
				<tr>
					<td>
						Email:
					</td>
					<td>
						<form:input path="email"/>
					</td>
				  	
				</tr>
				
				
			</table>
		
			<br />
			
			
			<input type="submit" value="Zmień hasło" />
		</form:form> 
 <a href="/basicP"> Powrót do strony głównej</a>
</body>
</html>