package ml.pic.tech.upload.fichier.resource;

import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fichier-rest")
public class FichierResource {

    @Autowired
    private FichierService service;

    @PostMapping
    public void uploadFileDB(@RequestParam MultipartFile[] files) {
        for (MultipartFile file:files){service.ajoutFichier(file);}
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileDB(@PathVariable long id) {
        Fichier file = service.getFichier(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType((file.getType())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement;filename=\"" + file.getNom() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }
@DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
