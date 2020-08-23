package com.kolasinski.piotr.tree.management.services.treenode;

import com.kolasinski.piotr.tree.management.services.mock.TreeNodeMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TreeNodeConverterTest {

    @InjectMocks
    private TreeNodeConverter treeNodeConverter;

    @InjectMocks
    private TreeNodeMock treeNodeMock;

    @Test
    public void shouldConvertEntityTreeNodeToTreeNodePayloadWithChildren() {
        TreeNode treeNode = treeNodeMock.createMockTreeNode(1L, 5, null, new ArrayList<>());
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(2L, 10, treeNode, new ArrayList<>()));
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(3L, 15, treeNode, new ArrayList<>()));
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(4L, 20, treeNode, new ArrayList<>()));

        TreeNodeChildrenPayload result = treeNodeConverter.convertEntityToResponseWithChildren(treeNode);
        assertThat(result.getValue()).isEqualTo(treeNode.getValue());
        assertThat(result.getId()).isEqualTo(treeNode.getId());
        assertThat(result.getParent()).isNull();
        assertThat(result.getChildren()).hasSize(treeNode.getChildren().size());
    }

    @Test
    public void shouldConvertEntityTreeNodeToTreeNodePayload() {
        TreeNode treeNode = treeNodeMock.createMockTreeNode(1L, 5, null, new ArrayList<>());
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(2L, 10, treeNode, new ArrayList<>()));
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(3L, 15, treeNode, new ArrayList<>()));
        treeNode.getChildren().add(treeNodeMock.createMockTreeNode(4L, 20, treeNode, new ArrayList<>()));

        TreeNodePayload result = treeNodeConverter.convertEntityToResponse(treeNode);
        assertThat(result.getValue()).isEqualTo(treeNode.getValue());
        assertThat(result.getId()).isEqualTo(treeNode.getId());
        assertThat(result.getParent()).isNull();
    }


    @Test
    public void shouldConvertTreeNodeCreationToEntityTreeNode() {
        TreeNodeCreation treeNodeCreation = treeNodeMock.createMockTreeNodeCreation(5, null, new ArrayList<>());
        treeNodeCreation.getChildren().add(treeNodeMock.createMockTreeNodeCreation(10, treeNodeCreation.getParentId(), new ArrayList<>()));
        treeNodeCreation.getChildren().add(treeNodeMock.createMockTreeNodeCreation(15, treeNodeCreation.getParentId(), new ArrayList<>()));
        treeNodeCreation.getChildren().add(treeNodeMock.createMockTreeNodeCreation(20, treeNodeCreation.getParentId(), new ArrayList<>()));

        TreeNode result = treeNodeConverter.convertCreationRequestToEntity(treeNodeCreation);
        assertThat(result.getValue()).isEqualTo(treeNodeCreation.getValue());
        assertThat(result.getId()).isNull();
        assertThat(result.getParent()).isNull();
        assertThat(result.getChildren()).hasSize(treeNodeCreation.getChildren().size());
    }

    @Test
    public void shouldConvertTreeNodeUpdateToEntityTreeNode() {
        TreeNodeUpdate treeNodeUpdate = treeNodeMock.createMockTreeNodeUpdate(5);

        TreeNode result = treeNodeConverter.convertUpdateRequestToEntity(treeNodeUpdate);
        assertThat(result.getValue()).isEqualTo(treeNodeUpdate.getValue());
        assertThat(result.getId()).isNull();
        assertThat(result.getParent()).isNull();
        assertThat(result.getChildren()).hasSize(0);
    }
}
