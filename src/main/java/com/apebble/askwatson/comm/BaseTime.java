package com.apebble.askwatson.comm;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  //생성시간

    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt; //수정시간
}

