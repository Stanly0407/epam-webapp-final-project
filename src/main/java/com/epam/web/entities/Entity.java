package com.epam.web.entities;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {
    private Long id;

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }

    private Long getId() {
        return id;
    }
}
