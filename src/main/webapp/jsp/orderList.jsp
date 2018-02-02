<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-default history">
				<div class="panel-heading">
					<h3 class="panel-title">${lang.getValue('admin_order_title')}</h3>
				</div>
				
				<div class="panel-body">
					<c:if test="${fm.hasMsg() == true}">
						<div class="alert alert-info" role="alert">
							<c:out value="${fm.getMsg()}" />
						</div>
					</c:if>
					
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
														<input type="hidden" name="orderId" value="${order.getId()}" />
													</div>
																										
				  								</div>
											</div>
											
											<br />
											
											<div class="row">
												<div class="col-md-12">
													<div>
														<div>
															<b>${lang.getValue('username')}:</b>
															<span>${order.getUser().getUsername()}</span>
														</div>

														<div>
															<b>${lang.getValue('order_create_date')}:</b>
															<span>${order.getCreateDate()}</span>
														</div>
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
											
											<br />
											
											<div class="row">
												<div class="col-md-12">
													<input type="button" value="${lang.getValue('order_accept')}" id="accept_order" class="btn btn-primary pull-right" />
												</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						
					</c:if>
					
					<c:if test="${empty orders}">
						<span>${lang.getValue('admin_orders_is_empty')}</span>
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</div>