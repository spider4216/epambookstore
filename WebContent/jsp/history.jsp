<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.epam.constant.OrderStatus"%>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-default history">
				<div class="panel-heading">
					<h3 class="panel-title">${lang.getValue('history_title')}</h3>
				</div>
				
				<div class="panel-body">
					
					<c:if test="${not empty orders}">
						<c:forEach items="${orders}" var="order">
							<div class="row">
								<div class="col-md-12">
									<div class="panel panel-default">
										<div class="panel-body">
											<div class="row">
											
												<div class="col-md-12">
													
													<div class="pull-left">
														<span class="order-number">${lang.getValue('order_label')}</span>
														<span class="order-number">#${order.getId()}</span>
													</div>
													
													<div class="pull-right">
														<c:choose>
															<c:when test="${order.getStatus() == approveStatus}">
																<span class="order-status order-status-done">${lang.getValue('order_status_approved')}</span>
															</c:when>
															
															<c:when test="${order.getStatus() == underConsiderationStatus}">
																<span class="order-status order-status-wait">${lang.getValue('order_status_under_consideration')}</span>
															</c:when>
															
															<c:otherwise>
																<span class="order-status">${lang.getValue('order_status_undefined')}</span>
															</c:otherwise>
														</c:choose>
													</div>
				  								</div>
											</div>
											
											<br />
											<br />
																						
											<div class="row">
												<div class="col-md-12">
													<table class="table">
														<tr>
															<th>${lang.getValue('book_detail_name')}</th>
															<th>${lang.getValue('basket_book_count')}</th>
															<th>${lang.getValue('book_detail_price')}</th>
														</tr>
													
														<c:forEach items="${order.products}" var="product">
															<tr>
																<td>${product.book.getName()}</td>
																<td>${product.getCount()}</td>
																<td>${product.book.getPrice()}</td>
															</tr>
														</c:forEach>
													</table>
												</div>
											</div>
											
											<div class="row">
												<div class="col-md-12">
													<div class="total-price pull-right">
														<span class="total-sum-label">${lang.getValue('basket_total_sum')}</span>
														<span class="total-price">${orderService.totalSumByCollection(order.products)}</span>
													</div>
												</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						
					</c:if>
					
					<c:if test="${empty orders}">
						<span>${lang.getValue('history_is_empty')}</span>
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</div>