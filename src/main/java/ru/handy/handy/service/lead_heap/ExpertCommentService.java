package ru.handy.handy.service.lead_heap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.handy.handy.models.lead_heap.ExpertCommentEntity;
import ru.handy.handy.repository.lead_heap.ExpertCommentRepository;

@Service
public class ExpertCommentService {
    private final ExpertCommentRepository expertCommentRepository;

    public ExpertCommentService(ExpertCommentRepository expertCommentRepository) {
        this.expertCommentRepository = expertCommentRepository;
    }

    @Transactional
    public void saveComment(ExpertCommentEntity comment){
        expertCommentRepository.save(comment);
    }
}
