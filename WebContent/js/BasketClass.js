function BasketClass() {
	
	/**
	 * @private
	 */
	var basketSelector = $(".add-to-basket-btn");
	
	var productId = $("input[name='product_id']").val();
	
	var productCountElementName = "input[name='count_product']";
	
	/**
	 * @public
	 */
	this.run = function() {
		attachBasketEvent();
	}
	
	/**
	 * @private
	 */
	var attachBasketEvent = function() {
		basketSelector.click(function() {
			addBaketHandler($(this));
		});
	}
	
	/**
	 * @private
	 */
	var addBaketHandler = function(element) {
		var productCount = $(productCountElementName).val();
		if (productCount <= 0) {
			$.jGrowl("Count of product cannot be less or equals than 0");
			return;
		}
		
		$.ajax({
			url: "/BookShop/ajax/add-to-basket.html",
			method: "post",
			data: { id : productId, count : productCount },
			dataType: "json",
			beforeSend: function() {
				element.prop('disabled', true);
			},
			complete: function() {
				element.prop('disabled', false);
			},
			success: function(res) {
				$.jGrowl("Book was successfully dropped to the basket");
				setTimeout(function() {
					location.reload();
				}, 1500);
			}
		})
	}
}