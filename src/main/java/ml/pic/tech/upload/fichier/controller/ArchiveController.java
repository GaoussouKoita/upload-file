package ml.pic.tech.upload.fichier.controller;

import ml.pic.tech.upload.fichier.domaine.Archive;
import ml.pic.tech.upload.fichier.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/", "/archive"})
public class ArchiveController {
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

    @GetMapping("/recherche")
    public String recherce(@RequestParam(defaultValue = "0") int page,
                           @RequestParam String reference, Model model) {
        Page<Archive> archiveRefPage = service.archivesParReference(page, reference);
        model.addAttribute("archives", archiveRefPage.getContent());

        model.addAttribute("totalElement", archiveRefPage.getTotalElements());
        model.addAttribute("totalPage", new int[archiveRefPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", archiveRefPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("reference", reference);
        return "archive/listeRefs";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:";
    }

    @GetMapping
    public String allPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Archive> archivePage = service.archivePage(page);
        model.addAttribute("archives", archivePage.getContent());

        model.addAttribute("totalElement", archivePage.getTotalElements());
        model.addAttribute("totalPage", new int[archivePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", archivePage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "archive/liste";
    }

    @GetMapping("/fichier-show/{idFichier}")
    public ResponseEntity afficher(@PathVariable Long idFichier) {

        return service.lire(idFichier);
    }

    @GetMapping("/fichier-supprimer")
    public String deleteFiler(@RequestParam(name = "idArchive") Long idArchive,
                              @RequestParam(name = "idFichier") Long idFichier) {
        service.deleteFichier(idArchive, idFichier);
        return "redirect:";
    }

    @GetMapping("/fichier-download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) {
        return service.download(id);
    }


}
