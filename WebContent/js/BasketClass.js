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
			addBaketHandler();
		});
	}
	
	/**
	 * @private
	 */
	var addBaketHandler = function() {
		var productCount = $(productCountElementName).val();
		
		$.ajax({
			url: "/BookShop/ajax/add-to-basket.html",
			method: "post",
			data: { id : productId, count : productCount },
			dataType: "json",
			success: function(res) {
				
			}
		})
	}
}