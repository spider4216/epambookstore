<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.epam.component.flash.FlashMessage"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default sign-in-panel">
			<div class="panel-heading">
				<h3 class="panel-title">${lang.getValue('sign_in')}</h3>
			</div>
			<c:if test="${fm.hasMsg() == true}">
			<div class="alert alert-info" role="alert">
				<c:out value="${fm.getMsg()}" />
			</div>
			</c:if>
			
			<form method="post" action="/sign-in-process.html">
				<div class="panel-body">
					
					<div class="form-group">
			    		<label for="username">${lang.getValue('username')}</label>
			    		<input type="text" class="form-control" name="username" id="username" placeholder="${requestScope.lang.getValue('username')}">
			  		</div>
			  			
			  		<div class="form-group">
			    		<label for="password">${lang.getValue('password')}</label>
			    		<input type="password" name="password" class="form-control" id="password" placeholder="${requestScope.lang.getValue('password')}">
			  		</div>

					<div class="sign-up-info">
						<span>${lang.getValue('you_havent_have_account_yet_q')}</span>
						<a href="/sign-up.html">${lang.getValue('sign_up')}</a>
					</div>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-md-12">
							<input type="submit" class="btn btn-primary pull-right" value="${requestScope.lang.getValue('sign_in')}" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>