package com.kolasinski.piotr.tree.management.services.mock;

import com.kolasinski.piotr.tree.management.services.treenode.TreeNode;
import com.kolasinski.piotr.tree.management.services.treenode.TreeNodeCreation;
import com.kolasinski.piotr.tree.management.services.treenode.TreeNodeUpdate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TreeNodeMock {

    public TreeNode createMockTreeNode(Long id, long value, TreeNode parent, List<TreeNode> children) {
        return TreeNode.builder()
                .id(id)
                .value(value)
                .parent(parent)
                .children(children)
                .build();
    }

    public TreeNodeCreation createMockTreeNodeCreation(long value, Long parentId, List<TreeNodeCreation> children) {
        return TreeNodeCreation.builder()
                .value(value)
                .parentId(parentId)
                .children(children)
                .build();
    }

    public TreeNodeUpdate createMockTreeNodeUpdate(long value) {
        return TreeNodeUpdate.builder()
                .value(value)
                .build();
    }
}
