<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.epam.component.service_locator.ServiceLocator"%>
<%@page import="com.epam.component.service_locator.ServiceLocatorEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${requestScope.lang.getValue('book_store')}</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
<link href="css/jquery.jgrowl.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/BookShop">${requestScope.lang.getValue('book_store')}</a>
		</div>
	
		<div class="navbar-collapse collapse">
			<c:if test="${not empty categories}">
				<ul class="nav navbar-nav">
					<c:forEach items="${categories}" var="item">
						<li>
							<a href="/BookShop/category.html?id=${item.getId()}">${item.getName()}</a>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			
			<ul class="nav navbar-nav navbar-right">
			
				<c:if test="${empty ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER)}">
					<li>
						<a href="/BookShop/sign-in.html">${requestScope.lang.getValue('sign_in')}</a>
					</li>
				</c:if>
				
				<c:if test="${not empty ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER)}">
					<li>
						<a href="/BookShop/history.html">${requestScope.lang.getValue('history_title')}</a>
					</li>
					
					<li>
						<a href="/BookShop/basket.html">${requestScope.lang.getValue('basket_title')}</a>
					</li>
					
					<c:if test="${ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER).getRole().getId() == requestScope.adminRoleId}">
						<li>
							<a href="/BookShop/order-list.html">${requestScope.lang.getValue('order_list')}</a>
						</li>
					</c:if>
					
					<li>
						<a href="/BookShop/sign-out.html">${requestScope.lang.getValue('sign_out')}</a>
					</li>
				</c:if>
			
				<li class="dropdown">
         			<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${lang.getValue('language')} <span class="caret"></span></a>
        			<ul class="dropdown-menu">
          				<li><a href="/BookShop/change-lang.html?lang=en">EN</a></li>
           				<li><a href="/BookShop/change-lang.html?lang=ru">RU</a></li>
       				</ul>
       			</li>
			</ul>
		</div>
	</div>
</nav>

<jsp:include page='<%=(String) request.getAttribute("includeJsp")%>' />

<div class="scripts">
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/validator.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.blockUI.js"></script>
	<script src="js/jquery.jgrowl.min.js"></script>
	<script src="js/BasketClass.js"></script>
	<script src="js/BasketRun.js"></script>
	<script src="js/SearchClass.js"></script>
	<script src="js/SearchRun.js"></script>
	<script src="js/OrderClass.js"></script>
	<script src="js/OrderRun.js"></script>
</div>
</body>
</html>