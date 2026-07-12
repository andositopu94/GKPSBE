package com.GKPS.Service;

import com.GKPS.DTO.Response.DashboardResponse;
import com.GKPS.Model.Organisasi.Organization;
import com.GKPS.Model.Organisasi.Person;
import com.GKPS.Repository.FamilyRepository;
import com.GKPS.Repository.OrganizationRepository;
import com.GKPS.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
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

        return response;
    }
}
