package com.kolasinski.piotr.tree.management.services.treenode;

import com.kolasinski.piotr.tree.management.services.exception.EntityNotFoundException;
import com.kolasinski.piotr.tree.management.services.treenode.exception.RootTreeNodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * Service to manage tree node.
 */
@Service
public class TreeNodeService {
    private static final Logger logger = LoggerFactory.getLogger(TreeNodeService.class);

    private final TreeNodeRepository treeNodeRepository;

    public TreeNodeService(TreeNodeRepository treeNodeRepository) {
        this.treeNodeRepository = treeNodeRepository;
    }

    /**
     * Find root tree node.
     * @return root tree node if exist otherwise throw an exception
     */
    public TreeNode findRoot() {
        return treeNodeRepository.findByParentOrderByParent(null)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find root tree node"));
    }

    /**
     * Check the request, then save node with children and refresh leafs.
     * @param node converted request body
     * @return saved node with children
     */
    @Transactional
    public TreeNode save(TreeNode node) {
        validRootTreeNode(node);
        TreeNode saved = saveWithChildren(node);
        refreshLeaves();
        return saved;
    }

    /**
     * Method of saving a node with all its children.
     * @param node converted request body
     * @return saved node with children
     */
    private TreeNode saveWithChildren(TreeNode node) {
        TreeNode saved = treeNodeRepository.save(prepareToSave(node));
        if (hasChildren(node)) {
            saved.setChildren(node.getChildren().stream().peek(n -> n.setParent(saved)).map(this::saveWithChildren).collect(Collectors.toList()));
        }
        logger.info("Save new TreeNode with id: " + saved.getId());
        return saved;
    }

    /**
     * Valid root tree node. Tree should only have one root per tree.
     * @param node to valid
     */
    private void validRootTreeNode(TreeNode node) {
        if (isParentNull(node) && treeNodeRepository.findByParentOrderByParent(null).isPresent())
            throw new RootTreeNodeExistsException("Root tree node exists");
    }

    /**
     * Refresh every leaf in tree node in database.
     */
    private void refreshLeaves() {
        treeNodeRepository.findByParentOrderByParent(null).ifPresent(treeNode -> updateLeaves(treeNode, 0));
    }

    /**
     * Set every leaf in tree node as sum of all values to root node.
     * @param node current node
     * @param value current value from previous state
     */
    private void updateLeaves(TreeNode node, long value) {
        if (hasChildren(node)) {
            node.getChildren().forEach(child -> updateLeaves(child, node.getValue() + value));
        } else {
            node.setValue(value);
            treeNodeRepository.save(node);
        }
    }

    /**
     * Prepare request node to save to database.
     * @param node converted request body
     * @return preapred entity to save to database
     */
    private TreeNode prepareToSave(TreeNode node) {
        return TreeNode.builder()
                .value(node.getValue())
                .parent(isParentNull(node) ? null : TreeNode.builder().id(node.getParent().getId()).build())
                .build();
    }

    /**
     * Update tree node by id, then refresh every leaf in tree node.
     * @param id tree node to update
     * @param node converted request body
     * @return updated tree node
     */
    @Transactional
    public TreeNode update(Long id, TreeNode node) {
        TreeNode existingTreeNode = treeNodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find tree node with id: " + id));
        existingTreeNode.setValue(node.getValue());
        treeNodeRepository.save(existingTreeNode);
        refreshLeavesAfterUpdate(existingTreeNode);
        logger.info("Updated TreeNode with id: " + id);
        return existingTreeNode;
    }

    /**
     * Refresh leaves depend on node children.
     * @param node updated tree node
     */
    private void refreshLeavesAfterUpdate(TreeNode node) {
        if (isParentNull(node)) {
            if (hasChildren(node)) refreshLeaves();
        } else {
            if (!hasChildren(node)) {
                treeNodeRepository.save((buildLeaf(node)));
            } else {
                refreshLeaves();
            }
        }
    }

    /**
     * Build leaf by node.
     * @param parent input
     * @return prepared leaf to save
     */
    private TreeNode buildLeaf(TreeNode parent) {
        return TreeNode.builder().value(countSumOfAllValuesToRoot(parent)).parent(parent).build();
    }

    /**
     * Count sum of all values to root.
     * @param node input
     * @return result
     */
    private long countSumOfAllValuesToRoot(TreeNode node)  {
        return isParentNull(node) ? node.getValue() : node.getValue() + countSumOfAllValuesToRoot(node.getParent());
    }

    /**
     * Remove tree node by id, then refresh every leaf in tree node.
     * @param id tree node to remove
     */
    @Transactional
    public void remove(Long id) {
        treeNodeRepository.deleteById(id);
        logger.info("Deleted TreeNode with id: " + id);
        refreshLeaves();
    }

    /**
     * Check parent is null.
     * @param node object to verify
     * @return result of conditional statement
     */
    private boolean isParentNull(TreeNode node) {
        return node.getParent() == null;
    }

    /**
     * Check children is null or empty.
     * @param node object to verify
     * @return result of conditional statement
     */
    private boolean hasChildren(TreeNode node) {
        return node.getChildren() != null && node.getChildren().size() > 0;
    }
}
