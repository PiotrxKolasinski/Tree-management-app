package com.kolasinski.piotr.tree.management.services.treenode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO creation request
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeNodeCreation {
    private long value;
    private Long parentId;
    private List<TreeNodeCreation> children;
}
