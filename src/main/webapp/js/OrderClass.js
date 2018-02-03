/**
 * Class for order products
 * 
 * @author Yuriy Sirotenko
 */
function OrderClass() {
	
	/**
	 * @private
	 */
	var acceptBtn = $("#accept_order");
	
	/**
	 * @public
	 */
	this.run = function() {
		acceptEvent();
	}
	
	/**
	 * Accept order
	 * 
	 * @private
	 */
	var acceptEvent = function() {
		acceptBtn.click(function() {
			acceptHandler($(this));
		});
	}
	
	/**
	 * Ajax request for accept order
	 * 
	 * @private
	 */
	var acceptHandler = function(element) {
		var orderId = element.parents(".panel").find("input[name='orderId']").val();

		$.ajax({
			url: "/ajax/accept-order.html",
			method: "post",
			dataType: "json",
			data: {orderId: orderId},
			beforeSend: function() {
				element.prop('disabled', true);
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