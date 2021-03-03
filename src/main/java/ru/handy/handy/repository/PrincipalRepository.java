package ru.handy.handy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.PrincipalEntity;

import java.util.List;

@Repository
public interface PrincipalRepository extends CrudRepository<PrincipalEntity, Long> {
    PrincipalEntity findPrincipalEntityByUsername(String username);
    PrincipalEntity findPrincipalEntityByName(String name);
    PrincipalEntity findPrincipalEntityById(Long id);
    List<PrincipalEntity> findAllByRole(String role);
}
