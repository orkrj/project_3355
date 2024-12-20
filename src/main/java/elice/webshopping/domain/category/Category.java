package elice.webshopping.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import elice.webshopping.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Category> children = new ArrayList<>();


    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    //    @Embedded
//    private BaseEntity baseEntity;
    private Category(String name) {
        this.name = name;
    }


    private Category(String name, Long parentId) {
        this.name = name;
        this.id = parentId;
    }

    public static Category of(String name, Long parentId) {
        return new Category(name,parentId);
    }

    public static Category from(String name){
        return new Category(name);
    }



    public void addChild(Category child){
        child.setParent(this);
        this.children.add(child);
    }

    private void setParent(Category parent){
        this.parent = parent;
    }

    public void update(String name) {
        this.name = name;
    }

    public boolean isNotRootCategory() {
        return parent != null;
    }

    public CategoryDto toDto() {

        return CategoryDto.builder()
                .id(this.id)
                .parentId(this.parent != null ? this.parent.getId() : null)
                .name(this.name)
                .build();
    }
}
