# Church Events Management Enhancement

## 📋 Dokumentasi Fitur Marriage, Baptism, dan Sidi

Dokumentasi lengkap untuk modul pelayanan sakramen dan dokumentasi gereja.

## 🏛️ Overview

Sistem manajemen dokumentasi acara gereja yang komprehensif untuk mencatat:
- **Marriage (Pernikahan)** - Pencatatan upacara pernikahan jemaat
- **Baptism (Baptis)** - Pencatatan upacara pembaptisan jemaat  
- **Sidi** - Pencatatan pengakuan sidi/komisi jemaat

## 📁 Struktur File

### Models
```
Model/Dokumentasi/
├── Marriage.java       ✅ (sudah ada)
├── Baptism.java        ✅ (sudah ada)
├── Sidi.java           ✅ (sudah ada)
└── DocumentReference.java
```

### Repositories
```
Repository/
├── MarriageRepository.java    (baru)
├── BaptismRepository.java     (baru)
└── SidiRepository.java        (baru)
```

### Controllers
```
Controller/
├── MarriageController.java    (baru)
├── BaptismController.java     (baru)
└── SidiController.java        (baru)
```

## 🔗 API Endpoints

### Marriage (Pernikahan)

**Create Marriage**
```http
POST /api/dokumentasi/marriage
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "husbandId": "husband-id",
  "wifeId": "wife-id",
  "marriageDate": "2026-06-15",
  "tempatAcaraNikah": "Gereja GKPS",
  "pendeta": "Pendeta Budi",
  "lokasiGereja": "Jl. Merdeka No. 10",
  "saksi1": "Saksi 1 Name",
  "saksi2": "Saksi 2 Name",
  "notes": "Catatan tambahan"
}
```

**Get All Marriages**
```http
GET /api/dokumentasi/marriage
Authorization: Bearer {JWT_TOKEN}
```

**Get Marriage by ID**
```http
GET /api/dokumentasi/marriage/{id}
Authorization: Bearer {JWT_TOKEN}
```

**Get Marriages by Status**
```http
GET /api/dokumentasi/marriage/status/{status}
Authorization: Bearer {JWT_TOKEN}

Status: PENDING, APPROVED, REJECTED
```

**Update Marriage**
```http
PUT /api/dokumentasi/marriage/{id}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "husbandId": "husband-id",
  "wifeId": "wife-id",
  "marriageDate": "2026-06-15",
  "tempatAcaraNikah": "Gereja GKPS",
  "pendeta": "Pendeta Budi",
  "lokasiGereja": "Jl. Merdeka No. 10",
  "notes": "Catatan tambahan"
}
```

**Delete Marriage**
```http
DELETE /api/dokumentasi/marriage/{id}
Authorization: Bearer {JWT_TOKEN}
```

---

### Baptism (Baptis)

**Create Baptism**
```http
POST /api/dokumentasi/baptism
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "personId": "person-id",
  "baptismDate": "2026-06-20",
  "pendeta": "Pendeta Budi",
  "tempat": "Sungai Juwana",
  "notes": "Catatan tambahan"
}
```

**Get All Baptisms**
```http
GET /api/dokumentasi/baptism
Authorization: Bearer {JWT_TOKEN}
```

**Get Baptism by ID**
```http
GET /api/dokumentasi/baptism/{id}
Authorization: Bearer {JWT_TOKEN}
```

**Get Baptism by Person ID**
```http
GET /api/dokumentasi/baptism/person/{personId}
Authorization: Bearer {JWT_TOKEN}
```

**Get Baptisms by Status**
```http
GET /api/dokumentasi/baptism/status/{status}
Authorization: Bearer {JWT_TOKEN}

Status: PENDING, APPROVED, REJECTED
```

**Update Baptism**
```http
PUT /api/dokumentasi/baptism/{id}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "personId": "person-id",
  "baptismDate": "2026-06-20",
  "pendeta": "Pendeta Budi",
  "tempat": "Sungai Juwana",
  "notes": "Catatan tambahan"
}
```

**Delete Baptism**
```http
DELETE /api/dokumentasi/baptism/{id}
Authorization: Bearer {JWT_TOKEN}
```

---

### Sidi (Pengakuan Sidi)

**Create Sidi**
```http
POST /api/dokumentasi/sidi
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "personId": "person-id",
  "sidiDate": "2026-07-10",
  "pendeta": "Pendeta Budi",
  "notes": "Catatan tambahan"
}
```

**Get All Sidi Records**
```http
GET /api/dokumentasi/sidi
Authorization: Bearer {JWT_TOKEN}
```

**Get Sidi by ID**
```http
GET /api/dokumentasi/sidi/{id}
Authorization: Bearer {JWT_TOKEN}
```

**Get Sidi by Person ID**
```http
GET /api/dokumentasi/sidi/person/{personId}
Authorization: Bearer {JWT_TOKEN}
```

**Get Sidi by Status**
```http
GET /api/dokumentasi/sidi/status/{status}
Authorization: Bearer {JWT_TOKEN}

Status: PENDING, APPROVED, REJECTED
```

**Update Sidi**
```http
PUT /api/dokumentasi/sidi/{id}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "personId": "person-id",
  "sidiDate": "2026-07-10",
  "pendeta": "Pendeta Budi",
  "notes": "Catatan tambahan"
}
```

**Delete Sidi**
```http
DELETE /api/dokumentasi/sidi/{id}
Authorization: Bearer {JWT_TOKEN}
```

---

## 📊 Response Format

**Success Response (201 Created):**
```json
{
  "success": true,
  "message": "Marriage record created successfully",
  "data": {
    "id": "507f1f77bcf86cd799439011",
    "husbandId": "husband-id",
    "wifeId": "wife-id",
    "marriageDate": "2026-06-15",
    "tempatAcaraNikah": "Gereja GKPS",
    "pendeta": "Pendeta Budi",
    "approvalStatus": "PENDING"
  },
  "timestamp": "2026-06-28T10:30:00"
}
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Marriage records retrieved successfully",
  "data": [
    {
      "id": "507f1f77bcf86cd799439011",
      "husbandId": "husband-id",
      "wifeId": "wife-id",
      "marriageDate": "2026-06-15",
      "tempatAcaraNikah": "Gereja GKPS",
      "pendeta": "Pendeta Budi",
      "approvalStatus": "PENDING"
    }
  ],
  "timestamp": "2026-06-28T10:30:00"
}
```

**Error Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Person ID dan Baptism Date harus diisi",
  "error": {
    "code": "INVALID_INPUT",
    "message": "Person ID dan Baptism Date harus diisi",
    "traceId": "550e8400-e29b-41d4-a716-446655440000"
  },
  "timestamp": "2026-06-28T10:30:00"
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Marriage tidak ditemukan dengan id '507f1f77bcf86cd799439012'",
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Marriage tidak ditemukan dengan id '507f1f77bcf86cd799439012'",
    "traceId": "550e8400-e29b-41d4-a716-446655440000"
  },
  "timestamp": "2026-06-28T10:30:00"
}
```

## 🔐 Authorization

Semua endpoint memerlukan:
- **JWT Token** di header `Authorization: Bearer {token}`
- **User harus authenticated**
- **Optional: Role-based access control** untuk certain operations

## 📝 Approval Status

Setiap record dapat memiliki status approval:
- **PENDING** - Menunggu persetujuan
- **APPROVED** - Sudah disetujui
- **REJECTED** - Ditolak dengan alasan

## 🧪 Testing Examples

### Create Marriage
```bash
curl -X POST "http://localhost:8080/api/dokumentasi/marriage" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "husbandId": "person-001",
    "wifeId": "person-002",
    "marriageDate": "2026-06-15",
    "tempatAcaraNikah": "Gereja GKPS",
    "pendeta": "Pendeta Budi",
    "lokasiGereja": "Jl. Merdeka No. 10",
    "saksi1": "Bambang",
    "saksi2": "Ahmad"
  }'
```

### Get All Marriages
```bash
curl -X GET "http://localhost:8080/api/dokumentasi/marriage" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Get Marriage by Status
```bash
curl -X GET "http://localhost:8080/api/dokumentasi/marriage/status/PENDING" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create Baptism
```bash
curl -X POST "http://localhost:8080/api/dokumentasi/baptism" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "personId": "person-003",
    "baptismDate": "2026-06-20",
    "pendeta": "Pendeta Budi",
    "tempat": "Sungai Juwana"
  }'
```

### Get Baptism by Person
```bash
curl -X GET "http://localhost:8080/api/dokumentasi/baptism/person/person-003" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create Sidi
```bash
curl -X POST "http://localhost:8080/api/dokumentasi/sidi" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "personId": "person-004",
    "sidiDate": "2026-07-10",
    "pendeta": "Pendeta Budi"
  }'
```

## 📋 Entity Relationships

```
Person (Organisasi)
  ├── Marriage (references husbandId, wifeId)
  ├── Baptism (references personId)
  └── Sidi (references personId)

DocumentReference
  ├── linked to Marriage (documentReferenceIds)
  ├── linked to Baptism (documentReferenceIds)
  └── linked to Sidi (documentReferenceIds)
```

## ✅ Next Steps

1. ✅ Models sudah ada (Marriage, Baptism, Sidi)
2. ✅ Repositories dibuat
3. ✅ Controllers dibuat dengan full CRUD
4. ⏳ Update existing controllers dengan ApiResponse
5. ⏳ Add Service layer untuk business logic
6. ⏳ Add validation annotations ke models
7. ⏳ Integrate dengan Document/DocumentReference
8. ⏳ Add approval workflow logic
