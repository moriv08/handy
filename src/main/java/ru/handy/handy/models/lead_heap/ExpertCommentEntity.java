package ru.handy.handy.models.lead_heap;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_expert_comments")
public class ExpertCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expertComment;
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private LeadEntity lead;

    public ExpertCommentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpertComment() {
        return expertComment;
    }

    public void setExpertComment(String expertComment) {
        this.expertComment = expertComment;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

    public LeadEntity getLead() {
        return lead;
    }

    public void setLead(LeadEntity lead) {
        this.lead = lead;
    }
}
