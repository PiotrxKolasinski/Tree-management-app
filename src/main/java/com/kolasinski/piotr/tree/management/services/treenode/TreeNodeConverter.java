package com.kolasinski.piotr.tree.management.services.treenode;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter for TreeNode between request/response from api and entity.
 */
@Component
public class TreeNodeConverter {

    /**
     * Convert creation request to database entity.
     * @param node input
     * @return tree node database entity
     */
    public TreeNode convertCreationRequestToEntity(TreeNodeCreation node) {
        return TreeNode.builder()
                .value(node.getValue())
                .parent(createParentByCreationRequestParentId(node))
                .children(convertCreationRequestToEntityChildren(node.getChildren()))
                .build();
    }

    /**
     * Convert update request to database entity.
     * @param node input
     * @return tree node database entity
     */
    public TreeNode convertUpdateRequestToEntity(TreeNodeUpdate node) {
        return TreeNode.builder()
                .value(node.getValue())
                .children(new ArrayList<>())
                .build();
    }

    /**
     * Create node.
     * @param node input
     * @return created empty tree node with id from input node
     */
    private TreeNode createParentByCreationRequestParentId(TreeNodeCreation node) {
        return node.getParentId() == null ? null : TreeNode.builder().id(node.getParentId()).build();
    }

    /**
     * Convert database entity to response tree node with children.
     * @param node input
     * @return response tree node with children
     */
    public TreeNodeChildrenPayload convertEntityToResponseWithChildren(TreeNode node) {
        return TreeNodeChildrenPayload.builder()
                .id(node.getId())
                .value(node.getValue())
                .parent(convertParentToResponse(node.getParent()))
                .children(convertChildrenToResponse(node.getChildren()))
                .build();
    }

    /**
     * Convert database entity to response tree node.
     * @param node input
     * @return response tree node
     */
    public TreeNodePayload convertEntityToResponse(TreeNode node) {
        return TreeNodePayload.builder()
                .id(node.getId())
                .value(node.getValue())
                .parent(convertParentToResponse(node.getParent()))
                .build();
    }

    /**
     * Convert tree node parent to response tree node.
     * @param parent input
     * @return response tree node
     */
    private TreeNodePayload convertParentToResponse(TreeNode parent) {
        return parent == null ? null : convertEntityToResponse(parent);
    }

    /**
     * Convert tree node children to response tree node with children.
     * @param children input
     * @return response tree node witch children
     */
    private List<TreeNodeChildrenPayload> convertChildrenToResponse(List<TreeNode> children) {
        return children == null ? new ArrayList<>() :
                children.stream().map(this::convertEntityToResponseWithChildren).collect(Collectors.toList());
    }

    /**
     * Convert tree node children to database entity.
     * @param children input
     * @return tree node database entity
     */
    private List<TreeNode> convertCreationRequestToEntityChildren(List<TreeNodeCreation> children) {
        return children == null ? new ArrayList<>() :
                children.stream().map(this::convertCreationRequestToEntity).collect(Collectors.toList());
    }
}
