<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-danger" role="alert">
				<strong>${requestScope.lang.getValue('error_was_accurred')}</strong>
				<span>
					<c:out value="${requestScope.errMsg}" />
				</span>
			</div>
		</div>
	</div>
</div>