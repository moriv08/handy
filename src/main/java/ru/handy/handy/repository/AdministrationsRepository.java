package ru.handy.handy.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.handy.handy.models.AdministrationEntity;

public interface AdministrationsRepository extends CrudRepository<AdministrationEntity, Long> {

    AdministrationEntity findAdministrationEntityById(Long id);

    @Modifying
    @Query("update AdministrationEntity administration set administration.announcement = ?2 where administration.id = ?1")
    void setNewAnnouncement(Long id, String announcement);
}
