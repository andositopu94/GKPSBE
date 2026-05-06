package com.GKPS.Model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jadwalIbadah")
public class jadwalIbadah {
        private String nama;
        private String hari;
        private String jam;
        private String alamat;

        public jadwalIbadah(String nama, String hari, String jam, String alamat) {
            this.nama = nama;
            this.hari = hari;
            this.jam = jam;
            this.alamat = alamat;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getHari() {
            return hari;
        }

        public void setHari(String hari) {
            this.hari = hari;
        }

        public String getJam() {
            return jam;
        }

        public void setJam(String jam) {
            this.jam = jam;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }
}
