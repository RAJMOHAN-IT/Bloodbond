package com.randy.bloodbond.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class Donor {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String bloodGroup;
private int age;
private String city;
private String email;
private String phoneNumber;
@ManyToOne
@JoinColumn(name = "hospital_id")
@JsonBackReference
private Hospital hospital;
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getBloodGroup() { return bloodGroup; }
public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
public int getAge() { return age; }
public void setAge(int age) { this.age = age; }
public String getCity() { return city; }
public void setCity(String city) { this.city = city; }
public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }
public String getPhoneNumber() { return phoneNumber; }
public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
public Hospital getHospital() { return hospital; }
public void setHospital(Hospital hospital) { this.hospital = hospital; }
}