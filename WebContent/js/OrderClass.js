function OrderClass() {
	var acceptBtn = $("#accept_order");
	
	this.run = function() {
		acceptEvent();
	}
	
	var acceptEvent = function() {
		acceptBtn.click(function() {
			acceptHandler($(this));
		});
	}
	
	var acceptHandler = function(element) {
		var orderId = element.parents(".panel").find("input[name='orderId']").val();

		$.ajax({
			url: "/BookShop/ajax/accept-order.html",
			method: "post",
			dataType: "json",
			data: {orderId: orderId},
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