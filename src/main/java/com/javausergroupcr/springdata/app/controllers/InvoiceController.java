package com.javausergroupcr.springdata.app.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
import com.javausergroupcr.springdata.app.models.entity.DBUser;
import com.javausergroupcr.springdata.app.models.entity.Invoice;
import com.javausergroupcr.springdata.app.models.entity.InvoiceItem;
import com.javausergroupcr.springdata.app.models.entity.Payment;
import com.javausergroupcr.springdata.app.models.entity.Product;
import com.javausergroupcr.springdata.app.models.service.IClientService;
import com.javausergroupcr.springdata.app.models.service.IInvoiceService;
import com.javausergroupcr.springdata.app.models.service.IProductService;
import com.javausergroupcr.springdata.app.models.service.IUserService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	@Autowired
	private IClientService clientService;
	@Autowired
	private IInvoiceService invoiceService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@Secured("ROLE_USER")
	@GetMapping("/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Invoice invoice = invoiceService.fetchByIdWithClientWithItemInvoiceWithProduct(id);

		if (invoice == null) {
			flash.addFlashAttribute("error", "La factura no existe");
			return "redirect:/client/list";
		}

		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Detalles de la factura #" + invoice.getId());
		return "invoice/view";
	}

	@Secured("ROLE_USER")
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
			RedirectAttributes flash) {

		Client client = clientService.findById(clientId);

		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/client/list";
		}

		Invoice invoice = new Invoice();
		invoice.setItems(new ArrayList<InvoiceItem>());
		invoice.setPayments(new ArrayList<Payment>());
		invoice.setClient(client);

		model.put("invoice", invoice);
		model.put("title", "Registrar factura");

		return "invoice/form";
	}

	@Secured("ROLE_USER")
	@GetMapping(value = "/find-product/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> findProduct(@PathVariable String term) {
		return productService.findAllByName(term);
	}

	@Secured("ROLE_USER")
	@PostMapping("/form")
	public String save(@Valid Invoice invoice, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "quantity[]", required = false) int[] quantity,
			@RequestParam(name = "isPaid", required = false) boolean isPaid, Authentication authentication,
			RedirectAttributes flash, SessionStatus status) {

		User user = (User) authentication.getPrincipal();
		DBUser createdBy = userService.findByUsername(user.getUsername());
		LocalDateTime createdAt = LocalDateTime.now();

		if (result.hasErrors()) {
			model.addAttribute("title", "Registrar factura");
			return "invoice/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Registrar factura");
			model.addAttribute("error", "La factura debe tener líneas");
			return "invoice/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Product product = productService.findById(itemId[i]);
			InvoiceItem item = new InvoiceItem();
			item.setQuantity(quantity[i]);
			item.setProduct(product);
			invoice.addItem(item);
		}

		if (isPaid) {
			Payment payment = new Payment();
			payment.setAmount(invoice.getTotal());
			payment.setEnabled(true);
			payment.setCreatedBy(createdBy);
			payment.setCreatedAt(createdAt);
			invoice.addPayment(payment);
		}

		invoice.setEnabled(true);
		invoice.setCreatedBy(createdBy);
		invoice.setCreatedAt(createdAt);

		invoiceService.save(invoice);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito");

		return "redirect:/client/view/" + invoice.getClient().getId();
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, Authentication authentication, RedirectAttributes flash) {

		Invoice invoice = invoiceService.findById(id);
		User user = (User) authentication.getPrincipal();
		DBUser updatedBy = userService.findByUsername(user.getUsername());
		LocalDateTime updatedAt = LocalDateTime.now();

		if (null != invoice) {
			invoice.setEnabled(false);
			invoice.setUpdatedBy(updatedBy);
			invoice.setUpdatedAt(updatedAt);

			for (Payment payment : invoice.getPayments()) {
				payment.setEnabled(false);
				payment.setUpdatedBy(updatedBy);
				payment.setUpdatedAt(updatedAt);
			}

			invoiceService.save(invoice);

			flash.addFlashAttribute("success", "Factura eliminada con éxito");
			return "redirect:/client/view/" + invoice.getClient().getId();
		}
		flash.addFlashAttribute("error", "La factura no existe, no se pudo eliminar");

		return "redirect:/client/list";
	}

}
