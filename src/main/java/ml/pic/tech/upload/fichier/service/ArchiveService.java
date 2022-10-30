package ml.pic.tech.upload.fichier.service;

import ml.pic.tech.upload.fichier.domaine.Archive;
import ml.pic.tech.upload.fichier.domaine.Fichier;
import ml.pic.tech.upload.fichier.repository.ArchiveRepository;
import ml.pic.tech.upload.fichier.utilis.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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

    public void deleteFichier(Long idArchive, long idFichier) {
        Fichier fichier=fichierService.getFichier(idFichier);

        Archive archive = getArchive(idArchive);
        archive.getFichiers().remove(fichier);
        repository.save(archive);
    }


    public Page<Archive> archivePage(int page) {
        return repository.findAll(PageRequest.of(page, Utils.NBRE_PAR_PAGE,
                Sort.by("date").descending()
                        .and(Sort.by("reference").ascending())));
    }

    public List<Archive> archivesParReference(String reference) {
        return repository.findByReferenceContaining(reference);
    }
    public Page<Archive> archivesParReference(int page, String reference) {
        return repository.findByReferenceContaining(reference,
                PageRequest.of(page, Utils.NBRE_PAR_PAGE,
                        Sort.by("date").descending()
                                .and(Sort.by("reference").ascending())));
    }


    public ResponseEntity lire(Long idFichier) {
        return fichierService.lire(idFichier);
    }

    public ResponseEntity<ByteArrayResource> download(Long id) {
        return fichierService.download(id);
    }
}
