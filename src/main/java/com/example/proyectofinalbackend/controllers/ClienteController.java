package com.example.proyectofinalbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.proyectofinalbackend.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;








import com.example.proyectofinalbackend.appdata.AppData;
import com.example.proyectofinalbackend.models.entities.Cliente;
import com.example.proyectofinalbackend.models.services.ClienteService;




@Controller
@SessionAttributes("cliente")
@RequestMapping("/cliente")
public class ClienteController {

	private final AppData appData;
	private final ClienteService clienteService;
	
	
	
	
	
	
		
	public static final String OPGEN = "CLIENTE"; 
	
	public ClienteController(
										 
										 
									     ClienteService clienteService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.clienteService = clienteService;
		
		
		

		
	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Cliente> pageCliente = clienteService.findAll(pageRequest); 
		PageRender<Cliente> paginator = new PageRender<>("/cliente/list",pageCliente,5);
		

		model.addAttribute("numcliente", clienteService.count());
		model.addAttribute("listcliente", pageCliente);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "cliente/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Cliente cliente = new Cliente();		
		model.addAttribute("cliente",cliente);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "cliente/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Cliente cliente = clienteService.findOne(id);
		if(cliente==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/cliente/list/" + page;
		}
		
		model.addAttribute("cliente", cliente);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "cliente/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Cliente cliente,  
			           BindingResult result, 
					   
					   Model model,
					   
					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(cliente.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (cliente.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "cliente/form";
		}
		
		
		
		
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/cliente/list/" + page;
	}
	
	
	
	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Cliente cliente = clienteService.findOne(id);
			
			if(cliente != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				clienteService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/cliente/list/" + page;
			}
			
			
						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/cliente/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			if (cliente == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/cliente/list/" + page;
			}

			model.addAttribute("cliente", cliente);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "cliente/view";
			
		}

		return "redirect:/cliente/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			if (cliente == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/cliente/list";
			}

			model.addAttribute("cliente", cliente);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "cliente/viewimg";
			
		}

		return "redirect:/cliente/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = clienteService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
