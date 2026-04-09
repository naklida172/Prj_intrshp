package com.adilkan.demo.bootstrap;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeferredRelationship {
    private Long mainId;
    private List<Long> relationId;
    private String mainEntityType;
    private String relationEntityType;
    private String relationField;
}
