package com.javausergroupcr.springdata.app.controllers;

import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javausergroupcr.springdata.app.models.entity.DBUser;
import com.javausergroupcr.springdata.app.models.entity.Product;
import com.javausergroupcr.springdata.app.models.service.IProductService;
import com.javausergroupcr.springdata.app.models.service.IUserService;
import com.javausergroupcr.springdata.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/product")
@SessionAttributes("product")
public class ProductController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "term", defaultValue = "") String term,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, size);
		Page<Product> products = null;
		PageRender<Product> pageRender = null;

		if ("".equals(term)) {
			products = productService.findAll(pageRequest);
		} else {
			products = productService.findAllByTerm(term, pageRequest);
		}
		pageRender = new PageRender<Product>("/product/list", products);

		model.addAttribute("title", "Listado de productos");
		model.addAttribute("term", term);
		model.addAttribute("size", size);
		model.addAttribute("page", pageRender);
		model.addAttribute("products", products);
		return "product/list";
	}

	@Secured("ROLE_USER")
	@GetMapping(value = "/find-product/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> findProduct(@PathVariable String term) {
		return productService.findAllByTerm(term);
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {

		Product product = new Product();
		model.put("product", product);
		model.put("title", "Registrar producto");
		return "product/form";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/form/{id}")
	public String update(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Product product = null;
		product = productService.findById(id);
		if (null == product) {
			flash.addFlashAttribute("error", "El producto no existe");
			return "redirect:/product/list";
		}
		model.put("product", product);
		model.put("title", "Editar producto");
		return "product/form";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Product product, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status, Authentication authentication) {

		String title = null;
		
		if (null == product.getId()) {
			title = "Registrar producto";
		} else {
			title = "Editar producto";
		}

		if (result.hasErrors()) {
			model.addAttribute("title", title);
			return "product/form";
		}

		String message = null;
		User user = (User) authentication.getPrincipal();
		DBUser changedBy = userService.findByUsername(user.getUsername());
		LocalDateTime changedAt = LocalDateTime.now();

		if (null == product.getId()) {
			message = "Producto creado con éxito";
			product.setCreatedBy(changedBy);
			product.setCreatedAt(changedAt);
		} else {
			message = "Producto editado con éxito";
			product.setUpdatedBy(changedBy);
			product.setUpdatedAt(changedAt);
		}

		product.setEnabled(true);
		productService.save(product);
		status.setComplete();
		flash.addFlashAttribute("success", message);
		return "redirect:/product/list";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, Authentication authentication, RedirectAttributes flash) {

		User user = (User) authentication.getPrincipal();
		DBUser updatedBy = userService.findByUsername(user.getUsername());
		LocalDateTime updatedAt = LocalDateTime.now();

		if (null != id && id > 0) {
			Product product = productService.findById(id);
			product.setEnabled(false);
			product.setUpdatedBy(updatedBy);
			product.setUpdatedAt(updatedAt);

			productService.save(product);

			flash.addFlashAttribute("success", "Producto eliminado con éxito");
		}

		return "redirect:/product/list";
	}
}
