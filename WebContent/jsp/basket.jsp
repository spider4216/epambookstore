<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-default basket">
				<div class="panel-heading">
					<h3 class="panel-title">${lang.getValue('basket_title')}</h3>
				</div>
				
				<div class="panel-body">
					<c:if test="${empty basket}">
						<div class="alert alert-info">
							${lang.getValue('basket_is_empty_hint')}
						</div>
					</c:if>
					
					<c:if test="${not empty basket}">
						<table class="table">
							<tr>
								<th>${lang.getValue('book_detail_name')}</th>
								<th>${lang.getValue('basket_book_count')}</th>
								<th>${lang.getValue('book_detail_price')}</th>
								<th>${lang.getValue('basket_book_manage')}</th>
							</tr>
						
							<c:forEach items="${basket}" var="item">
								<tr>
									<td>${item.book.getName()}</td>
									<td>${item.getCount()}</td>
									<td>${item.book.getPrice()}</td>
									<td>
										<a href="/BookShop/delete-book-from-cart.html?id=${item.book.getId()}">${lang.getValue('basket_book_delete')}</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>					
				</div>
			</div>
		</div>
	</div>
</div>