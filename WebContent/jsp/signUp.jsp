<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default sign-up-panel">
			<div class="panel-heading">
				<h3 class="panel-title">Sign Up</h3>
			</div>
			
			<form action="/BookShop/sign-up-process.html" method="post">
				<div class="panel-body">
				
			  		<div class="form-group">
			    		<label for="username">Username</label>
			    		<input type="text" name="username" class="form-control" id="username" placeholder="Username">
			  		</div>
			  		
			  		<div class="form-group">
			    		<label for="password">Password</label>
			    		<input type="text" name="password" class="form-control" id="password" placeholder="Password">
			  		</div>
					
					<div class="form-group">
			    		<label for="first_name">First Name</label>
			    		<input type="text" name="first_name" class="form-control" id="first_name" placeholder="First Name">
			  		</div>
			  			
			  		<div class="form-group">
			    		<label for="last_name">Last Name</label>
			    		<input type="text" name="last_name" class="form-control" id="last_name" placeholder="Last Name">
			  		</div>
					
					<div class="form-group">
			    		<label for="gender">Gender</label>
			    		<select class="form-control" name="gender">
			    			<option value="1">Man</option>
			    			<option value="2">Woman</option>
			    		</select>
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