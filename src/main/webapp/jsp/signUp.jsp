<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.epam.component.flash.FlashMessage"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default sign-up-panel">
			<div class="panel-heading">
				<h3 class="panel-title">${requestScope.lang.getValue('sign_up')}</h3>
			</div>
			
			<c:if test="${fm.hasMsg() == true}">
			<div class="alert alert-info" role="alert">
				<c:out value="${fm.getMsg()}" />
			</div>
			</c:if>
			
			<form action="/BookShop/sign-up-process.html" method="post" data-toggle="validator">
				<div class="panel-body">
				
			  		<div class="form-group">
			    		<label for="username">${requestScope.lang.getValue('username')}</label>
			    		<input type="text" required pattern="^[a-zA-Z1-9_]+$" data-minlength="4" maxlength="14" data-error="${requestScope.lang.getValue('login_validator_hint')}" name="username" class="form-control" id="username" placeholder="${requestScope.lang.getValue('username')}">
			    		<div class="help-block with-errors"></div>
			  		</div>
			  		
			  		<div class="form-group">
			    		<label for="password">${requestScope.lang.getValue('password')}</label>
			    		<input type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}" data-maxlength="16" data-minlength="8" data-error="${requestScope.lang.getValue('password_validator_hint')}" name="password" class="form-control" id="password" placeholder="${requestScope.lang.getValue('password')}">
			    		<div class="help-block with-errors"></div>
			  		</div>
					
					<div class="form-group">
			    		<label for="first_name">${requestScope.lang.getValue('first_name')}</label>
			    		<input type="text" required name="first_name" class="form-control" data-error="${requestScope.lang.getValue('required_hint')}" id="first_name" placeholder="${requestScope.lang.getValue('first_name')}">
			    		<div class="help-block with-errors"></div>
			  		</div>
			  			
			  		<div class="form-group">
			    		<label for="last_name">${requestScope.lang.getValue('last_name')}</label>
			    		<input type="text" required name="last_name" class="form-control" data-error="${requestScope.lang.getValue('required_hint')}" id="last_name" placeholder="${requestScope.lang.getValue('last_name')}">
			    		<div class="help-block with-errors"></div>
			  		</div>
					
					<div class="form-group">
			    		<label for="gender">${requestScope.lang.getValue('gender')}</label>
			    		<select class="form-control" required name="gender">
			    			<option value="1">${requestScope.lang.getValue('gender_man')}</option>
			    			<option value="2">${requestScope.lang.getValue('gender_woman')}</option>
			    		</select>
			    		<div class="help-block with-errors"></div>
			  		</div>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-md-12">
							<input type="submit" class="btn btn-primary pull-right" value="${requestScope.lang.getValue('sign_up')}" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>