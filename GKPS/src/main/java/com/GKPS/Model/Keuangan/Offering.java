package com.GKPS.Model.Keuangan;

import com.GKPS.Model.Enum.OfferingType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offering")
public class Offering extends Transaction {
    private OfferingType offeringType;
    private String personId; // ID dari anggota yang memberikan persembahan
}
