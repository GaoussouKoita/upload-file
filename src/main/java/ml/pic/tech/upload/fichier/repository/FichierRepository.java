package ml.pic.tech.upload.fichier.repository;

import ml.pic.tech.upload.fichier.domaine.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {
}
