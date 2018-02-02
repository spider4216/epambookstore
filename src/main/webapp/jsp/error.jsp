<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-danger" role="alert">
				<strong>${lang.getValue('error_was_accurred')}</strong>
				<span>
					<c:out value="${errMsg}" />
				</span>
			</div>
		</div>
	</div>
</div>