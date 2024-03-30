package kr.sujin.cafereview.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
@Where(clause = "deletedAt = null")
public class BaseEntity {
    @CreatedBy
    @Column(updatable = false)
    private String createdAt;

    @LastModifiedBy
    private String updatedAt;

    @Column()
    private String deletedAt;
}