package com.timeyang.catalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author chaokunyang
 */
@Entity
public class CatalogInfo {
    @Id
    private String id;
    private Long catalogId;
    private Boolean active;

    public CatalogInfo() {
        id = UUID.randomUUID().toString();
        active = false;
    }

    public CatalogInfo(Long catalogId) {
        this();
        this.catalogId = catalogId;
    }

    public CatalogInfo(Long catalogId, Boolean active) {
        this(catalogId);
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CatalogInfo{" +
                "id='" + id + '\'' +
                ", catalogId=" + catalogId +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        CatalogInfo that = (CatalogInfo) obj;
        if(id != null ? !id.equals(that.id) : that.id != null) return false;
        if(catalogId != null ? !catalogId.equals(that.catalogId) : that.catalogId != null) return false;
        return active != null ? active.equals(that.active) : that.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}