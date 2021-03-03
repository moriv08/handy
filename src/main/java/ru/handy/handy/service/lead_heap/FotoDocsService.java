package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.lead_heap.FotoDocsEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.repository.lead_heap.FotoDocsRepository;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FotoDocsService {
    private final FotoDocsRepository fotoDocsRepository;

    public FotoDocsService(FotoDocsRepository fotoDocsRepository) {
        this.fotoDocsRepository = fotoDocsRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public List<FotoDocsEntity> findAllFotoDocsByJuristId(Long id){
        return fotoDocsRepository.findAllByJuristInfoId(id);
    }

    @Transactional
    public void deleteFotoDocById(Long id){
        fotoDocsRepository.deleteById(id);
    }

    @Transactional
    public void saveFotoDoc(FotoDocsEntity fotoDoc){
        fotoDocsRepository.save(fotoDoc);
    }
    @Transactional
    public void setJuristInfoToDocFotoById(Long docsId, JuristInfoEntity juristInfoEntity){
        fotoDocsRepository.setJuristInfoToDocFotoById(docsId, juristInfoEntity);
    }

    @Transactional
    @ReadOnlyProperty
    public Map<Long, String> findAllFotoDocsByJuristIdAndPutIntoMap(Long id){

        Map<Long, byte[]> fotoBytes = new HashMap<>();
        Map<Long, String> allFotoDocs = new HashMap<>();

        findAllFotoDocsByJuristId(id).stream()
                .forEach(foto -> fotoBytes.put(foto.getId(), foto.getFotoDoc()));

        for (Map.Entry<Long, byte[]> entry : fotoBytes.entrySet())
            allFotoDocs.put(entry.getKey(), Base64.getEncoder().encodeToString(entry.getValue()));

        return allFotoDocs;
    }

}
