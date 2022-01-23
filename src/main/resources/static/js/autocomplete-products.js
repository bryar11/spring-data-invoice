$("#find_product").autocomplete({

	source: function(request, response) {
		$.ajax({
			url: "/invoice/find-product/" + request.term,
			dataType: "json",
			data: {
				term: request.term
			},
			success: function(data) {
				response($.map(data, function(item) {
					return {
						value: item.id,
						label: item.name,
						price: item.price,
					};
				}));
			},
		});
	},
	select: function(event, ui) {
		if (itemsHelper.hasProduct(ui.item.value)) {
			itemsHelper.increaseQuantity(ui.item.value, ui.item.price);
			return false;
		}

		var item = $("#invoiceItemsTemplate").html();

		item = item.replace(/{ID}/g, ui.item.value);
		item = item.replace(/{NAME}/g, ui.item.label);
		item = item.replace(/{PRICE}/g, ui.item.price);

		$("#findItems tbody").append(item);
		itemsHelper.calculateAmount(ui.item.value, ui.item.price, 1);

		return false;
	}
});

$("form").submit(function() {
	$("#invoiceItemsTemplate").remove();
	return;
});

var itemsHelper = {
	calculateAmount: function(id, price, quantity) {
		$("#total_amount_" + id).html(parseInt(price) * parseInt(quantity));
		this.calculateTotal();
	},
	hasProduct: function(id) {
		var result = false;

		$('input[name="item_id[]"]').each(function() {
			if (parseInt(id) == parseInt($(this).val())) {
				result = true;
			}
		});

		return result;
	},
	increaseQuantity: function(id, price) {
		var quantity = $("#quantity_" + id).val() ? parseInt($("#quantity_" + id).val()) : 0;
		$("#quantity_" + id).val(++quantity);
		this.calculateAmount(id, price, quantity);
	},
	deleteInvoiceItem: function(id) {
		$("#row_" + id).remove();
		this.calculateTotal();
	},
	calculateTotal: function() {
		var total = 0;

		$('span[id^="total_amount_"]').each(function() {
			total += parseInt($(this).html());
		});

		$('#total').html(total);
	}
}