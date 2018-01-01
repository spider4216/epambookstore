<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
	<div class="row">

		<c:forEach items="${books}" var="item">
			<div class="col-md-3">
				<div class="thumbnail thumbnail-book-card">
					<div class="thumb-img">
						<img src="${item.getImgPath()}" alt="" class="img-thumbnail">
					</div>
					
					<div class="thumb-content">
						<div class="thumb-title">
							<h3>
								<c:out value="${item.getName()}" />
							</h3>
						</div>
						
						<div class="thumb-description">
							<p>
								<c:out value="${item.getDescription()}" />
							</p>
						</div>
						
						<div class="thumb-footer">
							<a href="#" class="btn btn-default" role="button">${lang.getValue('more')}</a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>