/**
 * @author Yuriy Sirotenko
 */
function BasketClass() {
	
	/**
	 * Add to basket button element
	 * 
	 * @private
	 */
	var basketSelector = $(".add-to-basket-btn");
	
	/**
	 * Product id
	 * 
	 * @private
	 */
	var productId = $("input[name='product_id']").val();
	
	/**
	 * Count of product
	 * 
	 * @private
	 */
	var productCountElementName = "input[name='count_product']";
	
	/**
	 * Clear basket button element
	 * 
	 * @private
	 */
	var basketClearBtn = $(".clear-basket-btn");
	
	/**
	 * Item of book in basket. Collection's element
	 * 
	 * @private
	 */
	var basketLinkDeleteItem = $(".basket-book-item");
	
	/**
	 * Order basket button element
	 * 
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
	 * Add to basket event
	 * 
	 * @private
	 */
	var attachBasketAddEvent = function() {
		basketSelector.click(function() {
			addBasketHandler($(this));
		});
	}
	
	/**
	 * Clear basket event
	 * 
	 * @private
	 */
	var attachBasketClearEvent = function() {
		basketClearBtn.click(function() {
			clearBasketHandler($(this));
		});
	}
	
	/**
	 * Delete from basket event
	 * 
	 * @private
	 */
	var attachBasketItemDeleteEvent = function() {
		basketLinkDeleteItem.click(function() {
			deleteItemBasketHandler($(this));
		});
	}
	
	/**
	 * Order event
	 * 
	 * @private
	 */
	var attachOrderEvent = function() {
		orderBasketBtn.click(function() {
			orderBasketHandler($(this));
		});
	}
	
	/**
	 * Add to basket handler
	 * 
	 * @private
	 */
	var addBasketHandler = function(element) {
		var productCount = $(productCountElementName).val();
		if (productCount <= 0) {
			$.jGrowl("Count of product cannot be less or equals than 0");
			return;
		}
		
		ajaxHelperMethod("/BookShop/ajax/add-to-basket.html", "post", { id : productId, count : productCount }, element);
	}
	
	/**
	 * Clear basket handler
	 * 
	 * @private
	 */
	var clearBasketHandler = function(element) {
		ajaxHelperMethod("/BookShop/ajax/clear-basket.html", "post", null, element);
	}
	
	/**
	 * Delete item from basket handler
	 * 
	 * @private
	 */
	var deleteItemBasketHandler = function(element) {
		var id = element.attr("data-book-id");
		
		ajaxHelperMethod("/BookShop/ajax/delete-book-from-basket.html", "get", {id: id}, element);
	}
	
	/**
	 * Order handler
	 * 
	 * @private
	 */
	var orderBasketHandler = function(element) {
		ajaxHelperMethod("/BookShop/ajax/order-books.html", "post", null, element);
	}
	
	/**
	 * Ajax helper
	 * 
	 * @private
	 */
	var ajaxHelperMethod = function(url, method, data, element) {
		$.ajax({
			url: url,
			method: method,
			dataType: "json",
			data: data,
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
		});
	}
}