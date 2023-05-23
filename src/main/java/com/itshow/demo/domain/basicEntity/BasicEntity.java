package com.itshow.demo.domain.basicEntity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BasicEntity extends BasicTime {

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long lastUpdateBy;
}
