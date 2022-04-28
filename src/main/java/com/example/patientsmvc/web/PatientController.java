package com.example.patientsmvc.web;

import com.example.patientsmvc.entities.patient;
import com.example.patientsmvc.repositories.patientRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private patientRepo patientRepository;
@GetMapping(path = "/user/index")
    public  String patients(Model model,
                            @RequestParam(name="page",defaultValue = "0") int page ,
                            @RequestParam(name="size",defaultValue = "5") int size,
                            @RequestParam(name="keyword",defaultValue = "") String keyword){
    Page<patient> pagePatients=patientRepository.findByNomContains(keyword, PageRequest.of(page,size));
    model.addAttribute("listPatients",pagePatients.getContent());
    model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
    model.addAttribute("curentPage",page);
    model.addAttribute("keyword",keyword);
return "patients";
    }
    @GetMapping("/admin/delete")
    public String delete(Long id,String keyword, int page){
    patientRepository.deleteById(id);
    return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/admin/PatientForm")
    public String PatientForm(Model model)
    {
        model.addAttribute("patient", new patient());
        return "PatientForm";
    }

    @PostMapping(path = "/admin/save")
    public String Save(Model model, @Valid patient patient, BindingResult bindingResult, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String key)
    {
        if (bindingResult.hasErrors()) return "PatientForm";
    patientRepository.save(patient);
    return("redirect:/user/index?page="+page+"&key="+key);

    }
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, Long id, int page, String keyword)
    {
        patient p = patientRepository.findById(id).orElse(null);
        if (p == null) return "400";

        model.addAttribute("patient", p);
        model.addAttribute("page", page);
        model.addAttribute("key", keyword);


        return "editPatient";
    }




}
