package com.kolasinski.piotr.tree.management.services.treenode;

import com.kolasinski.piotr.tree.management.services.exception.EntityNotFoundException;
import com.kolasinski.piotr.tree.management.services.mock.TreeNodeMock;
import com.kolasinski.piotr.tree.management.services.treenode.exception.RootTreeNodeExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TreeNodeServiceTest {

    @InjectMocks
    private TreeNodeService treeNodeService;

    @Mock
    private TreeNodeRepository treeNodeRepository;

    @InjectMocks
    private TreeNodeMock treeNodeMock;

    private TreeNode treeNode;

    @Before
    public void init() {
        treeNode = treeNodeMock.createMockTreeNode(null, 2, null, new ArrayList<>());
    }

    @Test
    public void saveShouldReturnTreeNodeWithNotNullId() {
        TreeNode treeNodeReturn = treeNodeMock.createMockTreeNode(1L, treeNode.getValue(), treeNode.getParent(), treeNode.getChildren());
        when(treeNodeRepository.save(treeNode)).thenReturn(treeNodeReturn);

        TreeNode result = treeNodeService.save(treeNode);
        assertThat(result).isEqualTo(treeNodeReturn);
        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void saveShouldReturnTreeNodeWithChildrenWithCorrectParentId() {
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(null, 3, null, new ArrayList<>()));
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(null, 4, null, new ArrayList<>()));
        TreeNode treeNodeReturn = treeNodeMock.createMockTreeNode(1L, treeNode.getValue(), treeNode.getParent(), treeNode.getChildren());
        when(treeNodeRepository.save(treeNode)).thenReturn(treeNodeReturn, treeNode.getChildren().get(0), treeNode.getChildren().get(1));

        TreeNode result = treeNodeService.save(treeNode);
        assertThat(result).isEqualTo(treeNodeReturn);
        assertThat(result.getChildren()).hasSize(treeNodeReturn.getChildren().size());

        assertThat(result.getChildren().get(0).getParent()).isNotNull();
        assertThat(result.getChildren().get(0).getParent().getId()).isEqualTo(treeNodeReturn.getId());

        assertThat(result.getChildren().get(1).getParent()).isNotNull();
        assertThat(result.getChildren().get(1).getParent().getId()).isEqualTo(treeNodeReturn.getId());
    }

    @Test(expected = RootTreeNodeExistsException.class)
    public void saveShouldThrowRootTreeNodeExistsExceptionWhenNullParentIdExistsInDatabase() {
        when(treeNodeRepository.findByParentOrderByParent(null)).thenReturn(Optional.of(treeNode));

        treeNodeService.save(treeNode);
    }

    @Test
    public void updateShouldReturnUpdatedTreeNodeWithOnlyNewValueFromInput() {
        TreeNode treeNodeReturn = treeNodeMock.createMockTreeNode(1L, treeNode.getValue(), new TreeNode(), new ArrayList<TreeNode>() {{
            new TreeNode();
        }});
        when(treeNodeRepository.findById(1L)).thenReturn(Optional.of(treeNodeReturn));
        when(treeNodeRepository.save(treeNode)).thenReturn(treeNodeReturn);

        TreeNode result = treeNodeService.update(1L, treeNode);
        assertThat(result.getValue()).isEqualTo(treeNode.getValue());
        assertThat(result.getParent()).isNotEqualTo(treeNode.getParent());
        assertThat(result.getId()).isNotEqualTo(treeNode.getId());
        assertThat(result.getChildren()).hasSize(treeNodeReturn.getChildren().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateShouldThrowEntityNotFoundExceptionWhenCannotFindTreeNodeWithInputId() {
        treeNode.setId(1L);
        when(treeNodeRepository.findById(treeNode.getId())).thenThrow(EntityNotFoundException.class);

        treeNodeService.update(treeNode.getId(), treeNode);
    }

}
