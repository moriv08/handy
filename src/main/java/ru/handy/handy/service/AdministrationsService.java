package ru.handy.handy.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.AdministrationEntity;
import ru.handy.handy.repository.AdministrationsRepository;

import javax.transaction.Transactional;

@Service
public class AdministrationsService {
    private final AdministrationsRepository administrations;

    public AdministrationsService(AdministrationsRepository administrations) {
        this.administrations = administrations;
    }

    @Transactional
    @ReadOnlyProperty
    public AdministrationEntity findAdministrationById(Long id){
        return administrations.findAdministrationEntityById(id);
    }

    @Transactional
    public void setNewAnnouncement(Long id, String announcement){
        administrations.setNewAnnouncement(id, announcement);
    }
}
