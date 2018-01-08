<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-default history">
				<div class="panel-heading">
					<h3 class="panel-title">${lang.getValue('history_title')}</h3>
				</div>
				
				<div class="panel-body">
					
					<c:if test="${not empty history}">
						<table class="table">
							<tr>
								<th>${lang.getValue('book_detail_name')}</th>
								<th>${lang.getValue('basket_book_count')}</th>
								<th>${lang.getValue('book_detail_price')}</th>
								<th>${lang.getValue('history_date')}</th>
							</tr>
						
							<c:forEach items="${history}" var="item">
								<tr>
									<td>${item.book.getName()}</td>
									<td>${item.getCount()}</td>
									<td>${item.book.getPrice()}</td>
									<td>history_date</td>
								</tr>
							</c:forEach>
						</table>
						
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</div>