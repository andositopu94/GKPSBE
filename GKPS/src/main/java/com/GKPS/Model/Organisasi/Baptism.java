package com.GKPS.Model.Organisasi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "baptism")
public class Baptism {
    @Id
    private String id;
    private String personId; // ID of the person who got baptized
    private LocalDate baptismDate;
    private String pendeta; // Name of the pastor who performed the baptism
    private String tempat; // Place of baptism

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

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public void setBaptismDate(LocalDate baptismDate) {
        this.baptismDate = baptismDate;
    }

    public String getPendeta() {
        return pendeta;
    }

    public void setPendeta(String pendeta) {
        this.pendeta = pendeta;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }
}
