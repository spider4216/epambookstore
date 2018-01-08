// TODO DRY wrap ajax methods
function BasketClass() {
	
	/**
	 * @private
	 */
	var basketSelector = $(".add-to-basket-btn");
	
	/**
	 * @private
	 */
	var productId = $("input[name='product_id']").val();
	
	/**
	 * @private
	 */
	var productCountElementName = "input[name='count_product']";
	
	/**
	 * @private
	 */
	var basketClearBtn = $(".clear-basket-btn");
	
	/**
	 * @private
	 */
	var basketLinkDeleteItem = $(".basket-book-item");
	
	/**
	 * @private
	 */
	var orderBasketBtn = $(".order-basket-btn");
	
	/**
	 * @public
	 */
	this.run = function() {
		attachBasketAddEvent();
		attachBasketClearEvent();
		attachBasketItemDeleteEvent();
		attachOrderEvent();
	}
	
	/**
	 * @private
	 */
	var attachBasketAddEvent = function() {
		basketSelector.click(function() {
			addBasketHandler($(this));
		});
	}
	
	/**
	 * @private
	 */
	var attachBasketClearEvent = function() {
		basketClearBtn.click(function() {
			clearBasketHandler($(this));
		});
	}
	
	/**
	 * @private
	 */
	var attachBasketItemDeleteEvent = function() {
		basketLinkDeleteItem.click(function() {
			deleteItemBasketHandler($(this));
		});
	}
	
	/**
	 * @private
	 */
	var attachOrderEvent = function() {
		orderBasketBtn.click(function() {
			orderBasketHandler($(this));
		});
	}
	
	/**
	 * @private
	 */
	var addBasketHandler = function(element) {
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
				$.jGrowl(res.message);
				setTimeout(function() {
					location.reload();
				}, 1500);
			}
		})
	}
	
	/**
	 * @private
	 */
	var clearBasketHandler = function(element) {
		$.ajax({
			url: "/BookShop/ajax/clear-basket.html",
			method: "post",
			dataType: "json",
			beforeSend: function() {
				element.prop('disabled', true);
			},
			complete: function() {
				element.prop('disabled', false);
			},
			success: function(res) {
				$.jGrowl(res.message);
				setTimeout(function() {
					location.reload();
				}, 1500);
			}
		})
	}
	
	/**
	 * @private
	 */
	var deleteItemBasketHandler = function(element) {
		var id = element.attr("data-book-id");
		
		$.ajax({
			url: "/BookShop/ajax/delete-book-from-basket.html",
			method: "get",
			data : {id: id},
			dataType: "json",
			beforeSend: function() {
				element.prop('disabled', true);
			},
			complete: function() {
				element.prop('disabled', false);
			},
			success: function(res) {
				$.jGrowl(res.message);
				setTimeout(function() {
					location.reload();
				}, 1500);
			}
		})
	}
	
	/**
	 * @private
	 */
	var orderBasketHandler = function(element) {
		$.ajax({
			url: "/BookShop/ajax/order-books.html",
			method: "post",
			dataType: "json",
			beforeSend: function() {
				element.prop('disabled', true);
			},
			complete: function() {
				element.prop('disabled', false);
			},
			success: function(res) {
				$.jGrowl(res.message);
				setTimeout(function() {
					location.reload();
				}, 1500);
			}
		})
	}
}