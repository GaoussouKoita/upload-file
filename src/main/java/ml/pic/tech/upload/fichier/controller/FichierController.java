package ml.pic.tech.upload.fichier.controller;


import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.repository.FichierRepository;
import ml.pic.tech.upload.fichier.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/", "/fichier"})
public class FichierController {
    @Autowired
    private FichierService service;

    @GetMapping("/new")
    public String fichierForm(){
        return "fichier/ajout";
    }

    @PostMapping("/ajout")
    public String ajout(@RequestParam("files") MultipartFile[] files){
        for (MultipartFile file: files){
            service.ajoutFichier(file);
        }
        return "redirect:";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) {
        Fichier file = service.getFichier(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType((file.getType())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getNom() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("fichiers", service.fichierList());
        return "fichier/liste";
    }


}
