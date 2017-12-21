<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book Store</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Book Store</a>
		</div>
	
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="/BookShop/sign-in.html">Sign-in</a>
				</li>
			</ul>
		</div>
	</div>
</nav>

<jsp:include page='<%=(String) request.getAttribute("includeJsp")%>' />
</body>
</html>