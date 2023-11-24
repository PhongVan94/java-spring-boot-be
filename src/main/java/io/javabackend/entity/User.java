package io.javabackend.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String username;
    private String address;
    private String phone;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonManagedReference
    private GroupMember groupMember;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )

    private Collection<Project> projects;

    public User() {
    }

    public User(int id, String email, String password, String username, String address, String phone, String gender, GroupMember groupMember, Collection<Project> projects) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.groupMember = groupMember;
        this.projects = projects;
    }

    public GroupMember getGroup() {
        return groupMember;
    }

    public void setGroup(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", groupMember=" + groupMember +
                ", projects=" + projects +
                '}';
    }
}
