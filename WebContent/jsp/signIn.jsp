<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.epam.component.flash.FlashMessage"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default sign-in-panel">
			<div class="panel-heading">
				<h3 class="panel-title">Sign In</h3>
			</div>
			<c:if test="${FlashMessage.getInstance().hasMsg() == true}">
			<div class="alert alert-info" role="alert">
				<%= FlashMessage.getInstance().getMsg() %>
			</div>
			</c:if>
			
			<form>
				<div class="panel-body">
					
					<div class="form-group">
			    		<label for="username">Username</label>
			    		<input type="text" class="form-control" id="username" placeholder="Username">
			  		</div>
			  			
			  		<div class="form-group">
			    		<label for="password">Password</label>
			    		<input type="password" class="form-control" id="password" placeholder="Password">
			  		</div>
					
					<div class="sign-up-info">
						<span>You haven't have account yet?</span>
						<a href="/BookShop/sign-up.html">Sign Up</a>
					</div>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-md-12">
							<input type="submit" class="btn btn-primary pull-right" value="sign in" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>