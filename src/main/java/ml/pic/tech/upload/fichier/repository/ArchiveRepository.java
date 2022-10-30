package ml.pic.tech.upload.fichier.repository;

import ml.pic.tech.upload.fichier.domaine.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    public List<Archive> findByReferenceContaining(String ref);

    public Page<Archive> findByReferenceContaining(String ref, Pageable pageable);
}
