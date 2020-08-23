package com.kolasinski.piotr.tree.management.services.treenode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class TreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private long value;

    @ManyToOne(fetch = FetchType.LAZY)
    private TreeNode parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TreeNode> children;

    @JsonIgnore
    public List<TreeNode> getChildren() {
        return children;
    }
}
