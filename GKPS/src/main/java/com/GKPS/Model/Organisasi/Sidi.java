package com.GKPS.Model.Organisasi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "sidi")
public class Sidi {
    @Id
    private String id;
    private String personId; // ID of the Person who received Sidi
    private LocalDate sidiDate; // Date of Sidi
    private String pendeta; // Name of the pastor who performed Sidi

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public LocalDate getSidiDate() {
        return sidiDate;
    }

    public void setSidiDate(LocalDate sidiDate) {
        this.sidiDate = sidiDate;
    }

    public String getPendeta() {
        return pendeta;
    }

    public void setPendeta(String pendeta) {
        this.pendeta = pendeta;
    }
}
