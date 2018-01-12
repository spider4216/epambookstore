function SearchClass() {
	
	/**
	 * @private
	 */
	var searchSelector = $("input[name='search_field']");
	
	/**
	 * @private
	 */
	var  bookContainer = $(".book-container");
	
	/**
	 * @public
	 */
	this.run = function() {
		typeEvent();
	}
	
	/**
	 * @private
	 */
	var typeEvent = function() {
		searchSelector.keyup(function() {
			typeHandler();
		});
	}
	
	var typeHandler = function() {
		var text = searchSelector.val();
		var countCharacter = text.length;
		
		if (countCharacter < 4) {
			return;
		}
		
		$.ajax({
			url: "/BookShop/ajax/search.html",
			method: "post",
			dataType: "html",
			data: {text: text},
			beforeSend: function() {
				// TODO disabled container and show loader
			},
			complete: function() {
				// TODO enabled container
			},
			success: function(res) {
				bookContainer.html(res);
			}
		});
	}
}