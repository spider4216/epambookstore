<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.epam.component.flash.FlashMessage"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default sign-up-panel">
			<div class="panel-heading">
				<h3 class="panel-title">Sign Up</h3>
			</div>
			
			<c:if test="${FlashMessage.getInstance().hasMsg() == true}">
			<div class="alert alert-info" role="alert">
				<%= FlashMessage.getInstance().getMsg() %>
			</div>
			</c:if>
			
			<form action="/BookShop/sign-up-process.html" method="post" data-toggle="validator">
				<div class="panel-body">
				
			  		<div class="form-group">
			    		<label for="username">Username</label>
			    		<input type="text" required pattern="^[a-zA-Z1-9_]+$" data-minlength="4" maxlength="14" data-error="Minimum four characters, special character not permitted" name="username" class="form-control" id="username" placeholder="Username">
			    		<div class="help-block with-errors"></div>
			  		</div>
			  		
			  		<div class="form-group">
			    		<label for="password">Password</label>
			    		<input type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}" data-maxlength="16" data-minlength="8" data-error="Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character" name="password" class="form-control" id="password" placeholder="Password">
			    		<div class="help-block with-errors"></div>
			  		</div>
					
					<div class="form-group">
			    		<label for="first_name">First Name</label>
			    		<input type="text" required name="first_name" class="form-control" id="first_name" placeholder="First Name">
			    		<div class="help-block with-errors"></div>
			  		</div>
			  			
			  		<div class="form-group">
			    		<label for="last_name">Last Name</label>
			    		<input type="text" required name="last_name" class="form-control" id="last_name" placeholder="Last Name">
			    		<div class="help-block with-errors"></div>
			  		</div>
					
					<div class="form-group">
			    		<label for="gender">Gender</label>
			    		<select class="form-control" required name="gender">
			    			<option value="1">Man</option>
			    			<option value="2">Woman</option>
			    		</select>
			    		<div class="help-block with-errors"></div>
			  		</div>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-md-12">
							<input type="submit" class="btn btn-primary pull-right" value="sign up" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>