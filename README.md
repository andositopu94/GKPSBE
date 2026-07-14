# GKPSBE
# GKPS - Sistem Informasi Gereja GKPS

Backend API untuk sistem informasi Gereja GKPS, dibangun dengan **Spring Boot 3.4.3** dan **MongoDB**. Mendukung manajemen jemaat, keluarga, organisasi, keuangan, serta dashboard statistik.

## Tech Stack
- **Java 21**
- **Spring Boot 3.4.3**
- **MongoDB** (Database)
- **Redis** (Caching & Session)
- **Spring Security** + **JWT** (Autentikasi)
- **Maven** (Build Tool)

## Fitur Utama
- ✅ Manajemen Jemaat & Keluarga (CRUD dengan Pagination)
- ✅ Manajemen Organisasi & Pengurus
- ✅ Manajemen Keuangan (Pemasukan/Pengeluaran)
- ✅ Dashboard Statistik Real-time (Jemaat, Keuangan, Kegiatan)
- ✅ Role-Based Access Control (RBAC) dengan hierarki (PENDETA > MAJELIS > dll)
- ✅ API Versioning (`/api/v1/...`)

## Cara Menjalankan

### 1. Prasyarat
- Java 21
- Maven 3.8+
- MongoDB (local atau cloud)
- Redis (opsional, untuk session)

### 2. Clone Repository
```bash
git clone https://github.com/andositopu94/GKPSBE.git
cd GKPSBE/GKPS