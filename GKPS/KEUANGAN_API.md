--- GKPS/KEUANGAN_API.md (原始)


+++ GKPS/KEUANGAN_API.md (修改后)
# API Documentation - GKPS Financial Module

## Base URL
```
/api/keuangan
```

## Endpoints

### 1. Get Uang Masuk (Income) Summary
Returns income transactions grouped by date with predefined categories.

**Endpoint:** `GET /api/keuangan/uang-masuk`

**Parameters:**
- `startDate` (required): Start date in ISO format (YYYY-MM-DD)
- `endDate` (required): End date in ISO format (YYYY-MM-DD)

**Example Request:**
```bash
GET /api/keuangan/uang-masuk?startDate=2026-05-01&endDate=2026-05-31
```

**Response Format:**
```json
[
  {
    "tanggal": "2026-05-20",
    "items": [
      {
        "jenis": "Persembahan Ibadah Utama",
        "amount": 1000000,
        "deskripsi": ""
      },
      {
        "jenis": "Persembahan Ibadah Sektor A",
        "amount": 500000,
        "deskripsi": ""
      },
      {
        "jenis": "Perpuluhan",
        "amount": 2000000,
        "deskripsi": ""
      }
      // ... other categories with 0 amount if no transaction
    ],
    "total": 3500000
  }
]
```

**Categories for Uang Masuk:**
- Persembahan Ibadah Utama
- Persembahan Ibadah Sektor A
- Persembahan Sektor B
- Persembahan Ibadah Seksi Bapa
- Persembahan Ibadah Seksi Wanita
- Persembahan Ibadah Seksi Pemuda
- Persembahan Ibadah Seksi Sekolah Minggu
- Persembahan Ibadah Lainnya
- Perpuluhan
- Donasi
- Dana Usaha
- Lainnya

---

### 2. Get Uang Keluar (Expense) Summary
Returns expense transactions grouped by date with predefined categories.

**Endpoint:** `GET /api/keuangan/uang-keluar`

**Parameters:**
- `startDate` (required): Start date in ISO format (YYYY-MM-DD)
- `endDate` (required): End date in ISO format (YYYY-MM-DD)

**Example Request:**
```bash
GET /api/keuangan/uang-keluar?startDate=2026-05-01&endDate=2026-05-31
```

**Response Format:**
```json
[
  {
    "tanggal": "2026-05-20",
    "items": [
      {
        "jenis": "Pembangunan",
        "amount": 5000000,
        "deskripsi": "Renovasi gereja"
      },
      {
        "jenis": "Operasional",
        "amount": 1000000,
        "deskripsi": "Listrik dan air"
      }
      // ... other categories with 0 amount if no transaction
    ],
    "total": 6000000
  }
]
```

**Categories for Uang Keluar:**
- Pembangunan
- Operasional
- Pemeliharaan
- Acara
- Modal Usaha
- Sumbangan Kematian
- Sumbangan Pernikahan
- Sumbangan Baptis
- Sumbangan Angkat Sidi
- Gaji Majelis
- Donasi ke Pendeta
- Lainnya

---

### 3. Get Combined Keuangan Summary
Returns both income and expense summaries with grand totals.

**Endpoint:** `GET /api/keuangan/summary`

**Parameters:**
- `startDate` (required): Start date in ISO format (YYYY-MM-DD)
- `endDate` (required): End date in ISO format (YYYY-MM-DD)

**Example Request:**
```bash
GET /api/keuangan/summary?startDate=2026-05-01&endDate=2026-05-31
```

**Response Format:**
```json
{
  "uangMasuk": [
    {
      "tanggal": "2026-05-20",
      "items": [...],
      "total": 3500000
    }
  ],
  "uangKeluar": [
    {
      "tanggal": "2026-05-20",
      "items": [...],
      "total": 6000000
    }
  ],
  "totalMasuk": 3500000,
  "totalKeluar": 6000000,
  "saldo": -2500000
}
```

---

## Transaction Model

When creating transactions, use the following structure:

```json
{
  "type": "Masuk", // or "Keluar"
  "kategori": "PERSEMBAHAN", // from TransactionCategory enum
  "amount": 1000000,
  "deskripsi": "Persembahan ibadah minggu",
  "accountId": "account-id-here"
}
```

## Notes

1. All amounts are in Indonesian Rupiah (IDR)
2. Dates are in ISO 8601 format (YYYY-MM-DD)
3. Categories are automatically matched based on transaction category
4. Empty categories will show with 0 amount
5. Totals are calculated automatically
6. Response is sorted by date (descending - newest first)