package ml.pic.tech.upload.fichier.service;

import ml.pic.tech.upload.fichier.domaine.Archive;
import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArchiveService {
    @Autowired
    private ArchiveRepository repository;
    @Autowired
    private FichierService fichierService;


    public Archive ajoutArchive(Archive archive, MultipartFile[] files) {
        for (MultipartFile file : files) {
            Fichier fichier = fichierService.ajoutFichier(file);
            archive.getFichiers().add(fichier);
        }

        return repository.save(archive);
    }

    public Archive getArchive(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Archive> ArchiveList() {
        return repository.findAll();
    }

    public Page<Archive> archivePage(int page, int nbParPage) {
        return repository.findAll(PageRequest.of(page, nbParPage, Sort.by("reference").ascending()));
    }

    public List<Archive> archivesParReference(String reference) {
        return repository.findByReferenceContaining(reference);
    }

    public List<Archive> all() {
        return repository.findAll();
    }

}
