<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-danger" role="alert">
				<strong>Error was occurred!</strong>
				<span>
					<c:out value="${requestScope.errMsg}" />
				</span>
			</div>
		</div>
	</div>
</div>