package com.colptha.dom.entities;

import javax.persistence.*;

/**
 * Created by Colptha on 4/1/17.
 */
@MappedSuperclass
public class AbstractDomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
