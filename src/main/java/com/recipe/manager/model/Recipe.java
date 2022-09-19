package com.recipe.manager.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *  entity class of recipe with one to many relationship with ingredients
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@RequiredArgsConstructor
@Table(name = "recipes")
  public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Embedded
    @ElementCollection
    @CollectionTable(name="Ingredients", joinColumns=@JoinColumn    (name="recipe_id"))
    @AttributeOverrides({
        @AttributeOverride(name="ingredient_name", column=@Column(name="ingredient_name"))
    })
    private List<Ingredient> recipeIngredients;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "type")
    private String type;

    @Column(name = "numberOfServings")
    private int numberOfServings;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;


  public Recipe(Recipe recipe) {
  }
}

