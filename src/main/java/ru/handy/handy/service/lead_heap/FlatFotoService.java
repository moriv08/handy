package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.lead_heap.FlatFotoEntity;
import ru.handy.handy.repository.lead_heap.FlatFotoRepository;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlatFotoService {
    private final FlatFotoRepository flatFotoRepository;

    public FlatFotoService(FlatFotoRepository flatFotoRepository) {
        this.flatFotoRepository = flatFotoRepository;
    }

    @Transactional
    public void saveNewFlatFoto(FlatFotoEntity flatFotoEntity){
        flatFotoRepository.save(flatFotoEntity);
    }

    @Transactional
    public void deleteFlatFoto(Long id){
        flatFotoRepository.findById(id).ifPresent(flatFotoRepository::delete);
    }

    @Transactional
    @ReadOnlyProperty
    public Map<Long, String> findAllFlatFotosByLeadIdAndPutIntoMap(Long id){

        Map<Long, byte[]> fotos = new HashMap<>();
        Map<Long, String> allFlatFotos = new HashMap<>();

        flatFotoRepository.findAllByLeadId(id).stream()
                .forEach(foto -> fotos.put(foto.getId(), foto.getFoto()));

        for (Map.Entry<Long, byte[]> entry : fotos.entrySet())
            allFlatFotos.put(entry.getKey(), Base64.getEncoder().encodeToString(entry.getValue()));

        return allFlatFotos;
    }
}
