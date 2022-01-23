package com.javausergroupcr.springdata.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javausergroupcr.springdata.app.models.entity.Client;
import com.javausergroupcr.springdata.app.models.entity.Invoice;
import com.javausergroupcr.springdata.app.models.entity.InvoiceItem;
import com.javausergroupcr.springdata.app.models.entity.Product;
import com.javausergroupcr.springdata.app.models.service.IClientService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	@Autowired
	private IClientService clientService;

	@GetMapping("/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Invoice invoice = clientService.fetchInvoiceByIdWithClientWithItemInvoiceWithProduct(id);

		if (invoice == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/list";
		}

		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Factura: ".concat(invoice.getDescription()));
		return "invoice/view";
	}

	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
			RedirectAttributes flash) {

		Client client = clientService.findOne(clientId);

		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/list";
		}

		Invoice invoice = new Invoice();
		invoice.setItems(new ArrayList<InvoiceItem>());
		invoice.setClient(client);

		model.put("invoice", invoice);
		model.put("title", "Facturar");

		return "invoice/form";
	}

	@GetMapping(value = "/find-product/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> findProduct(@PathVariable String term) {
		return clientService.findByName(term);
	}

	@PostMapping("/form")
	public String save(@Valid Invoice invoice, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "quantity[]", required = false) Integer[] quantity, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Facturar");
			return "invoice/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Facturar");
			model.addAttribute("error", "La factura debe tener líneas");
			return "invoice/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Product product = clientService.findProductById(itemId[i]);

			InvoiceItem item = new InvoiceItem();
			item.setQuantity(quantity[i]);
			item.setProduct(product);
			invoice.addInvoiceItem(item);
		}

		clientService.saveInvoice(invoice);
		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con éxito");

		return "redirect:/view/" + invoice.getClient().getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		Invoice invoice = clientService.findInvoiceById(id);

		if (null != invoice) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito");
			return "redirect:/view/" + invoice.getClient().getId();
		}
		flash.addFlashAttribute("error", "La factura no existe en la base de datos, no se pudo eliminar");

		return "redirect:/list";
	}

}
