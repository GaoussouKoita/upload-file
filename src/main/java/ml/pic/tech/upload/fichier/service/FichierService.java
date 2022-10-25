package ml.pic.tech.upload.fichier.service;

import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class FichierService {
    @Autowired
    private FichierRepository repository;

    public Fichier ajoutFichier(MultipartFile file) {
        Fichier fichier = new Fichier();
        try {
            fichier.setNom(file.getOriginalFilename());
            fichier.setData(file.getBytes());
            fichier.setType(file.getContentType());
            fichier.setTailleMo((float) file.getSize()/1024/1024);
            repository.save(fichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fichier;
    }

    public Fichier getFichier(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Fichier> fichierList() {
        return repository.findAll();
    }

    public List<Fichier> ajoutList(List<Fichier> fichiers){
        return repository.saveAll(fichiers);
    }

    public ResponseEntity<ByteArrayResource> download(Long id){
        Fichier fichier = getFichier(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType((fichier.getType())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + fichier.getNom() + "\"")
                .body(new ByteArrayResource(fichier.getData()));
    }

}
