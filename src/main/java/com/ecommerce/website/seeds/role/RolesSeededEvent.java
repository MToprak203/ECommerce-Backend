package com.ecommerce.website.seeds.role;

import org.springframework.context.ApplicationEvent;

public class RolesSeededEvent extends ApplicationEvent {
    public RolesSeededEvent(Object source) {
        super(source);
    }
}
