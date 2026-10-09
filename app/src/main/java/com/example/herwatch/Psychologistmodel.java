package com.example.herwatch;

public class Psychologistmodel {
    String id,name,email,phone,work,specialization,experience,password;

    public Psychologistmodel(String id, String name, String email, String phone, String work, String specialization, String experience, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.work = work;
        this.specialization = specialization;
        this.experience = experience;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWork() {
        return work;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getExperience() {
        return experience;
    }

    public String getPassword() {
        return password;
    }
}
