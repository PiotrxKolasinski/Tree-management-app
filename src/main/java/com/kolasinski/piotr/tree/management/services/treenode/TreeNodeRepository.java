package com.kolasinski.piotr.tree.management.services.treenode;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TreeNodeRepository extends CrudRepository<TreeNode, Long> {
    Optional<TreeNode> findByParentOrderByParent(TreeNode parent);
}
