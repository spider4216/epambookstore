<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.epam.component.flash.FlashMessage"%>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-default basket">
				<div class="panel-heading">
					<h3 class="panel-title">${lang.getValue('basket_title')}</h3>
				</div>
				
				<div class="panel-body">
					<c:if test="${FlashMessage.getInstance().hasMsg() == true}">
						<div class="alert alert-info" role="alert">
							<c:out value="${FlashMessage.getInstance().getMsg()}" />
						</div>
					</c:if>
					
					<c:if test="${empty basket}">
						<span>${lang.getValue('basket_is_empty_hint')}</span>
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
										<a href="/BookShop/delete-book-from-basket.html?id=${item.book.getId()}">${lang.getValue('basket_book_delete')}</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						
						<div class="row">
							<div class="col-md-12">
								<div class="total-price pull-right">
									<span class="total-sum-label">${lang.getValue('basket_total_sum')}:</span>
									<span class="total-price">${totalSum}</span>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12">
								<div class="buttons-block">
									<a href="/BookShop/clear-basket.html" class="btn btn-danger">${lang.getValue('basket_clear_btn_caption')}</a>
									<a href="/BookShop/order-books.html" class="btn btn-primary">${lang.getValue('basket_order_btn_caption')}</a>
								</div>
							</div>
						</div>
						
						
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</div>