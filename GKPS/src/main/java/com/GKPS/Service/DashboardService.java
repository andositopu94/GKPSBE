package com.GKPS.Service;

import com.GKPS.DTO.Response.DashboardResponse;
import com.GKPS.Model.Organisasi.Organization;
import com.GKPS.Model.Organisasi.Person;
import com.GKPS.Repository.FamilyRepository;
import com.GKPS.Repository.OrganizationRepository;
import com.GKPS.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final PersonRepository personRepository;
    private final FamilyRepository familyRepository;
    private final OrganizationRepository organizationRepository;

    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalJemaat(personRepository.count());
        response.setTotalKeluarga(familyRepository.count());
        response.setTotalPengurus(organizationRepository.countByIsActiveTrue());

        //statistik per sektor
        List<Person> allPersons = personRepository.findAll();
        Map<String, Long> statistikPerSektor = allPersons.stream()
                .collect(Collectors.groupingBy(Person::getSektor, Collectors.counting()));
        response.setStatistikPerSektor(statistikPerSektor);

        //statistik per organization
        List<Organization> allOrganizations = organizationRepository.findByIsActiveTrue();
        Map<String, Long> statistikPerOrganization = new HashMap<>();
        for (Organization org : allOrganizations) {
            String nameOrg = org.getName();
            Long jumlahAnggota = org.getAnggotaIds() != null ? (long) org.getAnggotaIds().size() : 0L;
            statistikPerOrganization.put(nameOrg, jumlahAnggota);
        }
        response.setStatistikPerOrganisasi(statistikPerOrganization);

        //total aktivitas per bulan
        int totalKegiatanPerBulan = (int) organizationRepository.findByEndDateAfter(LocalDate.now()).size();
        response.setTotalKegiatanBulanIni(totalKegiatanPerBulan);

        BigDecimal pemasukan = getTotalKeuanganByJenis("PEMASUKAN");
        BigDecimal pengeluaran = getTotalKeuanganByJenis("PENGELUARAN");
        response.setTotalPemasukan(pemasukan);
        response.setTotalPengeluaran(pengeluaran);
        response.setSaldoKas(pemasukan.subtract(pengeluaran));

        response.setPemasukanPerKategori(getPemasukanPerKategori());
        response.setTrendBulanan(getTrendKeuanganBulanan());

        response.setLastUpdate(LocalDate.now().toString());

        return response;
    }

    private BigDecimal getTotalKeuanganByJenis(String jenis) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("jenisTransaksi").is(jenis)),
                group().sum("jumlah").as("total")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "keuangan", Map.class);
        if (results.getMappedResults().isEmpty()) return BigDecimal.ZERO;
            Object total = results.getMappedResults().get(0).get("total");
            return total != null ? new BigDecimal(total.toString()) : BigDecimal.ZERO;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getPemasukanPerKategori() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("jenisTransaksi").is("PEMASUKAN")),
                group("kategori").sum("jumlah").as("total"),
                sort(Sort.Direction.DESC, "total")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "keuangan", Map.class);
        return (List<Map<String, Object>>) (List<?>) results.getMappedResults();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getTrendKeuanganBulanan() {
        YearMonth now = YearMonth.now();
        List<String> lastSixMonths = List.of(
                now.minusMonths(5).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                now.minusMonths(4).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                now.minusMonths(3).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                now.minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                now.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        );

        Aggregation aggregation = newAggregation(
                project("jumlah", "jenisTransaksi")
                        .andExpression("{ $dateToString: { format: '%Y-%m', date: '$tanggal' } }").as("bulan"),
                match(Criteria.where("bulan").in(lastSixMonths)),
                group("bulan", "jenisTransaksi").sum("jumlah").as("total"),
                sort(Sort.Direction.ASC,  "_id.bulan")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "keuangan", Map.class);
        return (List<Map<String, Object>>) (List<?>) results.getMappedResults();
    }
}
