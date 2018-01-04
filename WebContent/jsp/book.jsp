<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
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
								<a href="/BookShop/category.html?id=${category.getId()}">${category.getName()}</a>
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
							<div class="input-group">
      							<span class="input-group-btn">
        							<button class="btn btn-primary" type="button">${lang.getValue('add_to_basket')}</button>
      							</span>
      							<input type="number" min="0" max="9" class="form-control count-field" placeholder="0">
    						</div>
						</div>
					</div>
					
					<div class="row">
						<br />
						<div class="col-md-12">
							<p>${book.getDescription()}</p>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>