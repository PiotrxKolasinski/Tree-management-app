package com.kolasinski.piotr.tree.management.services.treenode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO for response contains tree node with children
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNodeChildrenPayload {
    private Long id;
    private long value;
    private TreeNodePayload parent;
    private List<TreeNodeChildrenPayload> children;
}
