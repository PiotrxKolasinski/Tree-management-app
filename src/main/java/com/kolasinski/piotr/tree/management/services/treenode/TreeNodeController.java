package com.kolasinski.piotr.tree.management.services.treenode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Api to management tree node.
 */
@RestController
@RequestMapping("tree-nodes")
public class TreeNodeController {
    private final TreeNodeService treeNodeService;
    private final TreeNodeConverter treeNodeConverter;

    public TreeNodeController(TreeNodeService treeNodeService, TreeNodeConverter treeNodeConverter) {
        this.treeNodeService = treeNodeService;
        this.treeNodeConverter = treeNodeConverter;
    }

    /**
     * Get root tree node.
     * @return root tree node and all its children
     */
    @GetMapping
    public TreeNodeChildrenPayload getRoot() {
        return treeNodeConverter.convertEntityToResponseWithChildren(treeNodeService.findRoot());
    }

    /**
     * Create new child and its children node by parent Id.
     * @param request dto create input
     * @return saved tree node and all its children
     */
    @PostMapping
    public TreeNodeChildrenPayload save(@RequestBody TreeNodeCreation request) {
        TreeNode treeNode = treeNodeConverter.convertCreationRequestToEntity(request);
        return treeNodeConverter.convertEntityToResponseWithChildren(treeNodeService.save(treeNode));
    }

    /**
     * Update node by id.
     * @param id node to update
     * @param request dto update input
     * @return updated tree node and all its children
     */
    @PutMapping("/{id}")
    public TreeNodeChildrenPayload update(@PathVariable Long id, @RequestBody TreeNodeUpdate request) {
        TreeNode treeNode = treeNodeConverter.convertUpdateRequestToEntity(request);
        return treeNodeConverter.convertEntityToResponseWithChildren(treeNodeService.update(id, treeNode));
    }

    /**
     * Remove node and all its children by id.
     * @param id node to remove
     * @return 204 no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        treeNodeService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
