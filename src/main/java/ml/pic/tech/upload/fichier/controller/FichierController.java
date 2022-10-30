package ml.pic.tech.upload.fichier.controller;


import ml.pic.tech.upload.fichier.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fichier")
public class FichierController {
    @Autowired
    private FichierService service;

    @GetMapping("/nouveau")
    public String fichierForm(){
        return "fichier/ajout";
    }

    @PostMapping("/ajout")
    public String ajout(@RequestParam("files") MultipartFile[] files){
        for (MultipartFile file : files) {
            service.ajoutFichier(file);
        }
        return "redirect:";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) {
        return service.download(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity lire(@PathVariable Long id) {
        return service.lire(id);
    }

    @GetMapping("/supprimer/{id}")
    public void supprimer(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("fichiers", service.fichierList());
        return "fichier/liste";
    }


}
