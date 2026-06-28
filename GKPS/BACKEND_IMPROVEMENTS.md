# Backend Improvements Documentation

Dokumentasi lengkap tentang peningkatan backend GKPS untuk error handling, validation, dan CORS.

## 📋 Daftar Peningkatan

### 1. **Error Handling & Exception Management**

#### Custom Exception Classes
- `ApiException.java` - Base exception class untuk semua custom exceptions
- `ResourceNotFoundException.java` - Ketika resource tidak ditemukan (404)
- `DuplicateResourceException.java` - Ketika ada duplicate data (409 Conflict)
- `InvalidInputException.java` - Ketika input tidak valid (400)
- `UnauthorizedException.java` - Ketika akses tidak diizinkan (401)

#### Keuntungan:
✅ Konsisten error response format
✅ Error code dan message yang jelas
✅ Trace ID untuk tracking
✅ Error details untuk debugging

### 2. **Standardized API Response**

#### Response Classes
- `ApiResponse<T>.java` - Wrapper untuk semua response
- `ErrorResponse.java` - Detail error information

#### Response Format (Success):
```json
{
  "success": true,
  "message": "Request berhasil diproses",
  "data": { /* actual data */ },
  "timestamp": "2026-06-28T10:30:00",
  "path": "/api/keuangan/summary"
}
```

#### Response Format (Error):
```json
{
  "success": false,
  "message": "Input tidak valid. Periksa detail error.",
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Validasi input gagal",
    "details": {
      "startDate": "harus tidak kosong",
      "endDate": "harus format YYYY-MM-DD"
    },
    "traceId": "550e8400-e29b-41d4-a716-446655440000"
  },
  "timestamp": "2026-06-28T10:30:00",
  "path": "/api/keuangan/summary"
}
```

### 3. **Global Exception Handler**

#### EnhancedGlobalExceptionHandler.java
Menangani:
- ✅ Custom ApiException
- ✅ MethodArgumentNotValidException (validation errors)
- ✅ BadCredentialsException (auth failures)
- ✅ AuthenticationException (auth errors)
- ✅ MethodArgumentTypeMismatchException (type errors)
- ✅ NoHandlerFoundException (404 endpoints)
- ✅ RuntimeException
- ✅ General Exception

#### Fitur:
- Automatic trace ID generation
- Detailed logging untuk debugging
- Consistent error response format
- Request path dalam response

### 4. **Enhanced Security Configuration**

#### EnhancedSecurityConfig.java
Peningkatan:
- Improved CORS configuration dengan lebih fleksibel
- Better HTTP method authorization
- Cleaner endpoint authorization rules
- Support untuk multiple dev environments

#### Authorization Rules:
```
Public Endpoints:
- /api/auth/**          - Authentication endpoints
- /api/public/**        - Public information
- GET /api/ibadah/**    - Public worship info
- GET /api/renungan/**  - Public devotions

Protected Endpoints:
- /api/keuangan/**      - Finance (authenticated only)
- /api/admin/**         - Admin endpoints
- POST/PUT/DELETE /*    - All modifications need auth
```

### 5. **Input Validation Best Practices**

#### Untuk digunakan di DTO classes:
```java
import jakarta.validation.constraints.*;

public class TransactionRequest {
    @NotBlank(message = "Type tidak boleh kosong")
    private String type;
    
    @NotNull(message = "Amount tidak boleh kosong")
    @Positive(message = "Amount harus lebih dari 0")
    private Double amount;
    
    @NotBlank(message = "Kategori tidak boleh kosong")
    private String kategori;
    
    @Size(min = 5, max = 500, message = "Deskripsi harus 5-500 karakter")
    private String deskripsi;
}
```

#### Di Controller:
```java
@PostMapping
public ResponseEntity<ApiResponse<Transaction>> create(
        @Valid @RequestBody TransactionRequest request) {
    // Jika invalid, GlobalExceptionHandler akan handle
}
```

## 🚀 Cara Menggunakan

### 1. Update Dependencies (pom.xml)
Tidak perlu tambahan dependency, semua sudah ada di Spring Boot.

### 2. Migrasi dari Old Exception Handler ke New

**Before (Old):**
```java
try {
    // business logic
} catch (Exception e) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", HttpStatus.BAD_REQUEST.value());
    error.put("error", "Bad Request");
    error.put("message", e.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
```

**After (New):**
```java
if (transaction == null) {
    throw new ResourceNotFoundException("Transaction", "id", transactionId);
}
// Response automatically handled by GlobalExceptionHandler
```

### 3. Update Controllers untuk menggunakan ApiResponse

**Before:**
```java
@GetMapping
public ResponseEntity<List<Transaction>> getAll() {
    return ResponseEntity.ok(transactionService.getAll());
}
```

**After:**
```java
@GetMapping
public ResponseEntity<ApiResponse<List<Transaction>>> getAll() {
    List<Transaction> data = transactionService.getAll();
    return ResponseEntity.ok(ApiResponse.success("Transaksi berhasil diambil", data));
}
```

### 4. Update DTOs dengan Validation

```java
import jakarta.validation.constraints.*;

public class TransactionRequest {
    @NotBlank(message = "Type tidak boleh kosong")
    private String type;
    
    @NotNull(message = "Amount tidak boleh kosong")
    @Positive(message = "Amount harus positif")
    private Double amount;
    
    // getters setters
}
```

## 📊 HTTP Status Codes

| Status | Meaning | Contoh |
|--------|---------|--------|
| 200 | OK | Request berhasil |
| 201 | Created | Resource berhasil dibuat |
| 400 | Bad Request | Input tidak valid |
| 401 | Unauthorized | Token tidak valid/expired |
| 404 | Not Found | Resource tidak ditemukan |
| 409 | Conflict | Duplicate resource |
| 500 | Internal Server Error | Server error |

## 🔍 Error Codes

| Code | Meaning |
|------|----------|
| VALIDATION_ERROR | Input validation gagal |
| RESOURCE_NOT_FOUND | Resource tidak ditemukan |
| DUPLICATE_RESOURCE | Resource sudah ada |
| INVALID_CREDENTIALS | Username/password salah |
| UNAUTHORIZED | Tidak memiliki akses |
| INVALID_INPUT | Input tidak sesuai format |
| INVALID_PARAMETER_TYPE | Type parameter salah |
| ENDPOINT_NOT_FOUND | Endpoint tidak ada |
| INTERNAL_ERROR | Error internal |

## 🧪 Testing Examples

### Test 1: Valid Request
```bash
curl -X GET "http://localhost:8080/api/keuangan/uang-masuk?startDate=2026-06-01&endDate=2026-06-30" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Request berhasil diproses",
  "data": [ /* transaksi data */ ],
  "timestamp": "2026-06-28T10:30:00",
  "path": "/api/keuangan/uang-masuk"
}
```

### Test 2: Invalid Date Format
```bash
curl -X GET "http://localhost:8080/api/keuangan/uang-masuk?startDate=2026/06/01&endDate=30-06-2026" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response:**
```json
{
  "success": false,
  "message": "Parameter tidak valid",
  "error": {
    "code": "INVALID_PARAMETER_TYPE",
    "message": "Parameter 'startDate' harus bertipe LocalDate, diterima: 2026/06/01",
    "traceId": "550e8400-e29b-41d4-a716-446655440000"
  },
  "timestamp": "2026-06-28T10:30:00"
}
```

### Test 3: Unauthorized
```bash
curl -X GET "http://localhost:8080/api/keuangan/uang-masuk?startDate=2026-06-01&endDate=2026-06-30"
```

**Response:**
```json
{
  "success": false,
  "message": "Akses ditolak",
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Token tidak valid atau telah expired",
    "traceId": "550e8400-e29b-41d4-a716-446655440000"
  },
  "timestamp": "2026-06-28T10:30:00"
}
```

## 📝 Next Steps

1. ✅ Apply perubahan ke Controllers
2. ✅ Update DTOs dengan @Valid annotations
3. ✅ Test semua endpoints dengan error scenarios
4. ✅ Update API documentation
5. ✅ Integrate dengan React frontend

## 📚 Resources

- [Spring Boot Validation](https://spring.io/guides/gs/validating-form-input/)
- [Spring Security Best Practices](https://spring.io/guides/topical/spring-security-architecture/)
- [REST API Best Practices](https://restfulapi.net/)
- [HTTP Status Codes](https://httpwg.org/specs/rfc7231.html#status.codes)
