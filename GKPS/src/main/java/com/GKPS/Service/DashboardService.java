package com.GKPS.Service;

import com.GKPS.DTO.Response.DashboardResponse;
import com.GKPS.Repository.FamilyRepository;
import com.GKPS.Repository.OrganizationRepository;
import com.GKPS.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        response.setTotalPengurus(organizationRepository.count());
        return response;
    }
}
