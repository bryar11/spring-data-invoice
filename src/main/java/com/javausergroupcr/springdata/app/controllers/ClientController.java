package com.javausergroupcr.springdata.app.controllers;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javausergroupcr.springdata.app.models.entity.Client;
import com.javausergroupcr.springdata.app.models.entity.DBUser;
import com.javausergroupcr.springdata.app.models.entity.Invoice;
import com.javausergroupcr.springdata.app.models.entity.Payment;
import com.javausergroupcr.springdata.app.models.service.IClientService;
import com.javausergroupcr.springdata.app.models.service.IUserService;
import com.javausergroupcr.springdata.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/client")
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;
	@Autowired
	private IUserService userService;

	@Secured("ROLE_USER")
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash,
			Locale locale) {

		Client client = clientService.fetchByIdWithInvoices(id);
		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/client/list";
		}

		model.put("client", client);
		model.put("title", "Detalles del cliente: " + client.getName());
		System.out.println(1);
		return "client/view";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "term", defaultValue = "") String term,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, size);
		Page<Client> clients = null;
		PageRender<Client> pageRender = null;

		if ("".equals(term)) {
			clients = clientService.findAll(pageRequest);
		} else {
			clients = clientService.findAllByName(term, pageRequest);
		}
		pageRender = new PageRender<Client>("/client/list", clients);

		model.addAttribute("title", "Listado de clientes");
		model.addAttribute("term", term);
		model.addAttribute("size", size);
		model.addAttribute("page", pageRender);
		model.addAttribute("clients", clients);
		return "client/list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {

		Client client = new Client();
		model.put("client", client);
		model.put("title", "Registrar cliente");
		return "client/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form/{id}")
	public String update(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Client client = null;
		client = clientService.findById(id);
		if (null == client) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/client/list";
		}
		model.put("client", client);
		model.put("title", "Editar cliente");
		return "client/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status, Authentication authentication) {

		String message = null;
		String title = null;
		User user = (User) authentication.getPrincipal();
		DBUser changedBy = userService.findByUsername(user.getUsername());
		LocalDateTime changedAt = LocalDateTime.now();

		if (null == client.getId()) {
			message = "Cliente creado con éxito";
			title = "Registrar cliente";
			client.setCreatedBy(changedBy);
			client.setCreatedAt(changedAt);
		} else {
			message = "Cliente editado con éxito";
			title = "Editar cliente";
			client.setUpdatedBy(changedBy);
			client.setUpdatedAt(changedAt);
		}

		if (result.hasErrors()) {
			model.addAttribute("title", title);
			return "client/form";
		}

		client.setEnabled(true);
		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", message);
		return "redirect:/client/list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, Authentication authentication, RedirectAttributes flash) {

		User user = (User) authentication.getPrincipal();
		DBUser updatedBy = userService.findByUsername(user.getUsername());
		LocalDateTime updatedAt = LocalDateTime.now();

		if (null != id && id > 0) {
			Client client = clientService.findById(id);
			client.setEnabled(false);
			client.setUpdatedBy(updatedBy);
			client.setUpdatedAt(updatedAt);

			for (Invoice invoice : client.getInvoices()) {
				invoice.setEnabled(false);
				invoice.setUpdatedBy(updatedBy);
				invoice.setUpdatedAt(updatedAt);

				for (Payment payment : invoice.getPayments()) {
					payment.setEnabled(false);
					payment.setUpdatedBy(updatedBy);
					payment.setUpdatedAt(updatedAt);
				}
			}

			clientService.save(client);

			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		}

		return "redirect:/client/list";
	}
}
