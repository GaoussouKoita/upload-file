package ml.pic.tech.upload.fichier.repository;

import ml.pic.tech.upload.fichier.domaine.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findByReferenceContaining(String ref);
}
