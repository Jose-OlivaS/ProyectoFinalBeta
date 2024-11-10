package com.example.proyectofinalbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.example.proyectofinalbackend.models.services.UploadService;

import com.example.proyectofinalbackend.appdata.AppData;
import com.example.proyectofinalbackend.models.entities.Vehiculo;
import com.example.proyectofinalbackend.models.services.VehiculoService;

@Controller
@SessionAttributes("vehiculo")
@RequestMapping("/vehiculo")
public class VehiculoController {

	private final AppData appData;
	private final VehiculoService vehiculoService;
	private final UploadService uploadService;

	public static final String OPGEN = "VEHICULO";

	public VehiculoController(UploadService uploadService, VehiculoService vehiculoService, AppData applicationData) {
		this.appData = applicationData;
		this.vehiculoService = vehiculoService;
		this.uploadService = uploadService;
	}

	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
		if (page == null) page = 0;

		fillApplicationData(model,"LIST");

		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Vehiculo> pageVehiculo = vehiculoService.findAll(pageRequest);
		PageRender<Vehiculo> paginator = new PageRender<>("/vehiculo/list",pageVehiculo,5);

		model.addAttribute("numvehiculo", vehiculoService.count());
		model.addAttribute("listvehiculo", pageVehiculo);
		model.addAttribute("paginator", paginator);
		model.addAttribute("actualpage", page);

		return "vehiculo/list";
	}

	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Vehiculo vehiculo = new Vehiculo();
		model.addAttribute("vehiculo", vehiculo);

		if (page == null) page = 0;
		model.addAttribute("actualpage", page);

		fillApplicationData(model,"CREATE");

		return "vehiculo/form";
	}

	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null) page = 0;
		Vehiculo vehiculo = vehiculoService.findOne(id);
		if(vehiculo==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/vehiculo/list/" + page;
		}

		model.addAttribute("vehiculo", vehiculo);
		model.addAttribute("actualpage", page);

		fillApplicationData(model,"UPDATE");

		return "vehiculo/form";
	}

	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Vehiculo vehiculo, BindingResult result, Model model,
					   @RequestAttribute("file") MultipartFile foto_formname,
					   @RequestParam("fotoImageText") String fotoImageText,
					   @RequestParam("fotoImageTextOld") String fotoImageTextOld,
					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash, SessionStatus status) {

		boolean creating;

		if(vehiculo.getId() == null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}

		String msg = (vehiculo.getId() == null) ? "Creation successful" : "Update successful";

		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "vehiculo/form";
		}

		if(!foto_formname.isEmpty()) {
			AddUpdateImageFoto(foto_formname, vehiculo);
		} else {
			if(fotoImageText.isEmpty() && !(fotoImageTextOld.isEmpty())) {
				uploadService.delete(fotoImageTextOld);
				vehiculo.setFoto(null);
			}
		}

		vehiculoService.save(vehiculo);
		status.setComplete();
		flash.addFlashAttribute("success", msg);

		if (creating) page = lastPage();

		return "redirect:/vehiculo/list/" + page;
	}

	private void AddUpdateImageFoto(MultipartFile image, Vehiculo vehiculo) {
		if(vehiculo.getId() != null && vehiculo.getId() > 0 && vehiculo.getFoto() != null && vehiculo.getFoto().length() > 0) {
			uploadService.delete(vehiculo.getFoto());
		}

		String uniqueName = null;
		try {
			uniqueName = uploadService.copy(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		vehiculo.setFoto(uniqueName);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		if (page == null) page = 0;

		if(id > 0) {
			Vehiculo vehiculo = vehiculoService.findOne(id);

			if(vehiculo != null) {
				vehiculoService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/vehiculo/list/" + page;
			}

			if(vehiculo.getFoto() != null)
				uploadService.delete(vehiculo.getFoto());

			flash.addFlashAttribute("success","Deletion successful");
		}

		return "redirect:/vehiculo/list/" + page;
	}

	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null) page = 0;

		if (id > 0) {
			Vehiculo vehiculo = vehiculoService.findOne(id);

			if (vehiculo == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/vehiculo/list/" + page;
			}

			model.addAttribute("vehiculo", vehiculo);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "vehiculo/view";
		}

		return "redirect:/vehiculo/list/" + page;
	}

	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {
		if (id > 0) {
			Vehiculo vehiculo = vehiculoService.findOne(id);

			if (vehiculo == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/vehiculo/list";
			}

			model.addAttribute("vehiculo", vehiculo);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption", true);
			model.addAttribute("imageField", imageField);

			return "vehiculo/viewimg";
		}

		return "redirect:/vehiculo/list";
	}

	private int lastPage() {
		Long nReg = vehiculoService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}

	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData", appData);
		model.addAttribute("optionCode", OPGEN);
		model.addAttribute("screen", screen);
	}
}
