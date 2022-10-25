package ml.pic.tech.upload.fichier.controller;

import ml.pic.tech.upload.fichier.domaine.Archive;
import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.service.ArchiveService;
import ml.pic.tech.upload.fichier.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/archive")
public class ArchiveController {
    @Autowired
    private FichierService fichierService;

    @Autowired
    private ArchiveService service;

    @GetMapping("/nouveau")
    public String form(Model model) {
        model.addAttribute("archive", new Archive());
        return "archive/ajout";
    }

    @PostMapping("/ajout")
    public String ajout(@ModelAttribute Archive archive,
                        @RequestParam MultipartFile[] files, Model model) {
        service.ajoutArchive(archive, files);
        return "redirect:";
    }
    @PostMapping("/recherche")
    public String recherce(@RequestParam String reference, Model model) {
       model.addAttribute("archives",
               service.archivesParReference(reference));
        return "archive/liste";
    }

    @GetMapping("/{id}")
    public String  delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:";
    }
    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id) {
        List<Fichier> fichiers = service.getArchive(id).getFichiers();
        for (Fichier fichier : fichiers) {
            fichierService.download(fichier.getId());
        }
    }

    @GetMapping
    public String allPage(Model model) {
        model.addAttribute("archives", service.all());
        return "archive/liste";
    }


}
