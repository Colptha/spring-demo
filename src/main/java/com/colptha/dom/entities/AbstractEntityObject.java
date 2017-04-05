package com.colptha.dom.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Colptha on 4/1/17.
 */
@MappedSuperclass
public abstract class AbstractEntityObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    private Date createdOn;
    private Date updatedOn;

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @PrePersist
    @PreUpdate
    public void updateTimeStamps() {

        if (createdOn == null) {
            createdOn = new Date();
        }

        updatedOn = new Date();
    }
}