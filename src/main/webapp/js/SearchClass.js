/**
 * Class for search books
 * 
 * @author Yuriy Sirotenko
 */
function SearchClass() {
	
	/**
	 * @private
	 */
	var searchSelector = $("input[name='search_field']");

	/**
	 * @private
	 */
	var searchByCategorySelector = $("input[name='search_field_by_category']");
	
	/**
	 * @private
	 */
	var bookContainer = $(".book-container");
	
	/**
	 * @private
	 */
	var categoryId = $("input[name='category_id']").val();
	
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
			typeMainHandler();
		});
		
		searchByCategorySelector.keyup(function() {
			typeCategoryHandler();
		});
	}
	
	/**
	 * @private
	 */
	var typeCategoryHandler = function() {
		typeHandler(searchByCategorySelector);
	}
	
	/**
	 * @private
	 */
	var typeMainHandler = function() {
		typeHandler(searchSelector);
	}
	
	/**
	 * @private
	 */
	var typeHandler = function(field) {
		var text = field.val();
		var countCharacter = text.length;
		
		if (countCharacter < 4) {
			return;
		}
		
		$.ajax({
			url: "/ajax/search.html",
			method: "post",
			dataType: "html",
			data: {text: text, categoryId: categoryId},
			beforeSend: function() {
				blockElement(bookContainer);
			},
			complete: function() {
				unblockElement(bookContainer);
			},
			success: function(res) {
				bookContainer.html(res);
			}
		});
	}
	
	var blockElement = function(element) {
		element.block({
			message: null,
			overlayCSS: {backgroundColor: '#efefef'}
		});
	}
	
	var unblockElement = function(element) {
		element.unblock();
	}
}