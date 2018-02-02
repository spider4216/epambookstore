<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
	<div class="row">
		<c:if test="${not empty books}">
		
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="input-group">
									<span class="input-group-addon">&#x1F50D;</span>
									<input type="text" placeholder="${lang.getValue('search_for')}" name="search_field" class="form-control" />
								</div>
  							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="book-container">
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
													<a href="/BookStore/book.html?id=${item.getId()}" class="btn btn-default" role="button">${lang.getValue('more')}</a>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
	
							<div class="row">
								<nav aria-label="...">
									<ul class="pager">
										<c:if test="${pager.isPreviousDisabled() == true}">
											<li class="disabled">
												<a href="/BookStore/#" class="disabled">${lang.getValue('pager_previous')}</a>
											</li>
										</c:if>
										
										<c:if test="${pager.isPreviousDisabled() == false}">
											<li>
												<a href="/BookStore/?page=${pager.getPreviousPageNumber()}">${lang.getValue('pager_previous')}</a>
											</li>
										</c:if>
										
										<c:if test="${pager.isNextDisabled() == true}">
											<li class="disabled">
												<a href="/BookStore/?page=${pager.getCurrentPageNumber()}">${lang.getValue('pager_next')}</a>
											</li>
										</c:if>
										
										<c:if test="${pager.isNextDisabled() == false}">
											<li>
												<a href="/BookStore/?page=${pager.getNextPageNumber()}">${lang.getValue('pager_next')}</a>
											</li>
										</c:if>
									
									</ul>
								</nav>
							</div>
						</div>
					</div>
				</div>
			</div>

			
		</c:if>
		
		<c:if test="${empty books}">
			<div class="col-md-12">
				<span>${lang.getValue('books_does_not_exist_page_hint')}</span>
			</div>
		</c:if>
	</div>
</div>