package io.javabackend.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String url;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )

    private Collection<Group> groups;

    public Role() {
    }

    public Role(int id, String url, String description, Collection<Group> groups) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(url, role.url) && Objects.equals(description, role.description) && Objects.equals(groups, role.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, description, groups);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", groups=" + groups +
                '}';
    }
}
