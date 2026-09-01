package com.mypack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user_contexts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "preferences")
    @Lob
    private String preferences;

    @Column(name = "memory")
    @Lob
    private String memory;

    @ElementCollection
    @CollectionTable(name = "user_context_properties")
    @MapKeyColumn(name = "property_key")
    @Column(name = "property_value")
    private Map<String, String> properties = new HashMap<>();
}
