package com.kolasinski.piotr.tree.management.services.treenode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for response contains tree node
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNodePayload {
    private Long id;
    private long value;
    private TreeNodePayload parent;
}
