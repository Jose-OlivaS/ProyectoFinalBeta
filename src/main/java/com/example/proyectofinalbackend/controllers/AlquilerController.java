package com.example.proyectofinalbackend.controllers;

import com.example.proyectofinalbackend.paginator.PageRender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.proyectofinalbackend.models.entities.Alquiler;
import com.example.proyectofinalbackend.models.services.AlquilerService;




@Controller
@SessionAttributes("alquiler")
@RequestMapping("/alquiler")
public class AlquilerController {

	private final AppData appData;
	private final AlquilerService alquilerService;
	
	
	
	
	
	
		
	public static final String OPGEN = "ALQUILER"; 
	
	public AlquilerController(
										 
										 
									     AlquilerService alquilerService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.alquilerService = alquilerService;
		
		
		

		
	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Alquiler> pageAlquiler = alquilerService.findAll(pageRequest); 
		PageRender<Alquiler> paginator = new PageRender<>("/alquiler/list",pageAlquiler,5);
		

		model.addAttribute("numalquiler", alquilerService.count());
		model.addAttribute("listalquiler", pageAlquiler);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "alquiler/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Alquiler alquiler = new Alquiler();		
		model.addAttribute("alquiler",alquiler);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "alquiler/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Alquiler alquiler = alquilerService.findOne(id);
		if(alquiler==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/alquiler/list/" + page;
		}
		
		model.addAttribute("alquiler", alquiler);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "alquiler/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Alquiler alquiler,  
			           BindingResult result, 
					   
					   Model model,
					   
					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(alquiler.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (alquiler.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "alquiler/form";
		}
		
		
		
		
		
		alquilerService.save(alquiler);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/alquiler/list/" + page;
	}
	
	
	
	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Alquiler alquiler = alquilerService.findOne(id);
			
			if(alquiler != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				alquilerService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/alquiler/list/" + page;
			}
			
			
						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/alquiler/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Alquiler alquiler = alquilerService.findOne(id);

			if (alquiler == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/alquiler/list/" + page;
			}

			model.addAttribute("alquiler", alquiler);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "alquiler/view";
			
		}

		return "redirect:/alquiler/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Alquiler alquiler = alquilerService.findOne(id);

			if (alquiler == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/alquiler/list";
			}

			model.addAttribute("alquiler", alquiler);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "alquiler/viewimg";
			
		}

		return "redirect:/alquiler/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = alquilerService.count();
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
