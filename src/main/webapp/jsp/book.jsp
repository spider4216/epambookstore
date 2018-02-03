<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${isInBasket == true}">
				<div class="alert alert-info">
					${lang.getValue('book_detail_has_already_in_basket')}
				</div>
			</c:if>
		
			<div class="panel panel-default book-detail">
				<div class="panel-heading">
					<h3 class="panel-title">${book.getName()}</h3>
				</div>
				
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3">
							<img src="${book.getImgPath()}" alt="" class="img-thumbnail">
						</div>
						
						<div class="col-md-9">
							<div class="row">
								<span>${lang.getValue('book_detail_name')}:</span>
								<span>${book.getName()}</span>
							</div>
							
							<div class="row">
								<span>ISBN:</span>
								<span>${book.getIsbn()}</span>
							</div>

							<div class="row">
								<span>${lang.getValue('book_detail_author')}:</span>
								<span>${book.getAuthor()}</span>
							</div>

							<div class="row">
								<span>${lang.getValue('book_detail_year')}:</span>
								<span>${book.getYear()}</span>
							</div>

							<div class="row">
								<span>${lang.getValue('book_detail_page')}:</span>
								<span>${book.getPage()}</span>
							</div>

							<div class="row">
								<span>${lang.getValue('book_detail_category')}:</span>
								<a href="/category.html?id=${category.getId()}">${category.getName()}</a>
							</div>
							
							<br />
							
							<div class="row">
								<span class="price-label">${lang.getValue('book_detail_price')}:</span>
								<span class="price">${book.getPrice()}</span>
							</div>
						</div>
					</div>
					
					<div class="row">
						<br />
						<div class="col-md-12">
							<c:if test="${isInBasket == true}">
								<a href="/basket.html" class="btn btn-warning">${lang.getValue('book_detail_go_to_basket')}</a>
							</c:if>
							
							<c:if test="${isInBasket == false}">
								<div class="input-group">
	      							<span class="input-group-btn">
	        							<button class="btn btn-primary add-to-basket-btn" type="button">${lang.getValue('add_to_basket')}</button>
	      							</span>
	      							<input type="number" name="count_product" value="1" min="1" max="9" class="form-control count-field" placeholder="0">
	    						</div>
							</c:if>
						</div>
					</div>
					
					<div class="row">
						<br />
						<div class="col-md-12">
							<p>${book.getDescription()}</p>
						</div>
					</div>
					
					<div class="hidden">
						<input type="hidden" name="product_id" value="${book.getId()}" />
					</div>

				</div>
			</div>
		</div>
	</div>
</div>