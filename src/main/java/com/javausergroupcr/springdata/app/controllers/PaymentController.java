package com.javausergroupcr.springdata.app.controllers;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javausergroupcr.springdata.app.models.entity.DBUser;
import com.javausergroupcr.springdata.app.models.entity.Invoice;
import com.javausergroupcr.springdata.app.models.entity.Payment;
import com.javausergroupcr.springdata.app.models.service.IInvoiceService;
import com.javausergroupcr.springdata.app.models.service.IPaymentService;
import com.javausergroupcr.springdata.app.models.service.IUserService;

@Controller
@RequestMapping("/payment")
@SessionAttributes("payment")
public class PaymentController {

	@Autowired
	private IPaymentService paymentService;
	@Autowired
	private IInvoiceService invoiceService;
	@Autowired
	private IUserService userService;

	@Secured("ROLE_USER")
	@GetMapping("/form/{invoiceId}")
	public String create(@PathVariable(value = "invoiceId") Long invoiceId, Map<String, Object> model,
			RedirectAttributes flash) {

		Invoice invoice = invoiceService.findById(invoiceId);

		if (invoice == null) {
			flash.addFlashAttribute("error", "La factura no existe");
			return "redirect:/client/list";
		}

		if (invoice.getTotal() - invoice.getPaid() <= 0) {
			flash.addFlashAttribute("error", "La factura ya está pagada");
			return "redirect:/invoice/view/" + invoiceId;
		}

		model.put("invoice", invoice);
		model.put("title", "Registrar pago");

		return "payment/form";
	}

	@Secured("ROLE_USER")
	@PostMapping("/form/")
	public String save(@Valid Payment payment, BindingResult result, Model model,
			@RequestParam(name = "balance", required = false) double balance, Authentication authentication,
			RedirectAttributes flash, SessionStatus status) {
		
		Invoice invoice = invoiceService.findById(payment.getInvoiceId());

		if (payment.getAmount() <= 0) {
			model.addAttribute("invoice", invoice);
			model.addAttribute("title", "Registrar pago");
			model.addAttribute("error", "El monto debe de ser mayor a 0");
			return "payment/form";
		}

		if (result.hasErrors()) {
			model.addAttribute("invoice", invoice);
			model.addAttribute("title", "Registrar pago");
			return "payment/form";
		}

		if (payment.getAmount() > balance) {
			model.addAttribute("invoice", invoice);
			model.addAttribute("title", "Registrar pago");
			model.addAttribute("error", "El monto debe de ser menor al monto pendiente por pagar");
			return "payment/form";
		}

		User user = (User) authentication.getPrincipal();
		DBUser createdBy = userService.findByUsername(user.getUsername());
		LocalDateTime createdAt = LocalDateTime.now();

		payment.setEnabled(true);
		payment.setCreatedBy(createdBy);
		payment.setCreatedAt(createdAt);

		paymentService.save(payment);
		status.setComplete();
		flash.addFlashAttribute("success", "Pago registrado con éxito");

		return "redirect:/invoice/view/" + payment.getInvoiceId();
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{paymentId}")
	public String deletePayment(@PathVariable(value = "paymentId") long paymentId, Authentication authentication,
			RedirectAttributes flash) {

		User user = (User) authentication.getPrincipal();

		Payment payment = paymentService.findById(paymentId);
		if (null != payment) {
			payment.setEnabled(false);
			payment.setUpdatedBy(userService.findByUsername(user.getUsername()));
			payment.setUpdatedAt(LocalDateTime.now());
			paymentService.save(payment);

			flash.addFlashAttribute("success", "Pago eliminado con éxito");
			return "redirect:/invoice/view/" + payment.getInvoiceId();
		}
		flash.addFlashAttribute("error", "Pago no existe");

		return "redirect:/client/list";
	}

}
