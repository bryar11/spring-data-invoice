package com.javausergroupcr.springdata.app.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import com.javausergroupcr.springdata.app.models.service.IClientService;
import com.javausergroupcr.springdata.app.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Client client = clientService.fetchByIdWithInvoices(id);
		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/list";
		}

		model.put("client", client);
		model.put("title", "Detalle cliente: " + client.getName());
		return "view";
	}

	@RequestMapping(value = { "/list", "/" }, method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Client> clients = clientService.findAll(pageRequest);

		PageRender<Client> pageRender = new PageRender<Client>("/list", clients);
		model.addAttribute("title", "Listado de clientes");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
		return "list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {

		Client client = new Client();
		model.put("client", client);
		model.put("title", "Crear cliente");
		return "form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String update(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Client client = null;
		client = clientService.findOne(id);
		if (null == client) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/list";
		}
		model.put("client", client);
		model.put("title", "Editar cliente");
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		String message = "Cliente editado con éxito";
		String title = "Editar cliente";

		if (null == client.getId()) {
			message = "Cliente creado con éxito";
			title = "Crear cliente";
		}

		if (result.hasErrors()) {
			model.addAttribute("title", title);
			return "form";
		}

		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", message);
		return "redirect:list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			clientService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		}

		return "redirect:/list";
	}
}
