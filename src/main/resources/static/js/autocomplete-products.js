$("#find_product").autocomplete({

	source: function(request, response) {
		if (request.term.length > 2) {
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
		}
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
		item = item.replace(/{FORMATTED_PRICE}/g, itemsHelper.formatCurrency(ui.item.price));

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
		var subtotal = parseInt(price) * parseInt(quantity);
		$("#total_amount_" + id).html(subtotal);
		$("#formatted_amount_" + id).html(itemsHelper.formatCurrency(subtotal));
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

		$('#total').val(this.formatCurrency(total));
	},
	formatCurrency: function(amount) {
		return (amount).toLocaleString('es-CR', {
			style: 'currency',
			currency: 'CRC',
		});
	}
}