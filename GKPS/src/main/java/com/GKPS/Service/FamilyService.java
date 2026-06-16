package com.GKPS.Service;

import com.GKPS.DTO.FamilyRequestDto;
import com.GKPS.DTO.FamilyResponseDto;
import com.GKPS.Model.Organisasi.Family;
import com.GKPS.Model.Organisasi.Person;
import com.GKPS.Repository.FamilyRepository;
import com.GKPS.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final PersonRepository personRepository;

    public Family save(FamilyRequestDto familyRequestDto) {
        if (!personRepository.existsById(familyRequestDto.getKepalaKeluargaId())) {
            throw new IllegalArgumentException("Kepala keluarga dengan ID " + familyRequestDto.getKepalaKeluargaId() + " tidak ditemukan");
    }
        Family family = new Family();
        family.setNomorKeluarga(familyRequestDto.getNomorKeluarga());
        family.setKepalaKeluargaId(familyRequestDto.getKepalaKeluargaId());
        family.setSektor(familyRequestDto.getSektor());
        family.setAnggotaKeluarga(familyRequestDto.getAnggotaKeluarga());
        family.setActive(true);
        return familyRepository.save(family);
    }

    public List<FamilyResponseDto> findAll(){
        return familyRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public FamilyResponseDto findById(String id){
        Family family = familyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Family dengan ID " + id + " tidak ditemukan"));
        return toResponse(family);
    }

    public void delete(String id) {
        familyRepository.deleteById(id);
    }

    private FamilyResponseDto toResponse(Family family) {
        FamilyResponseDto response = new FamilyResponseDto();
        response.setId(family.getId());
        response.setNomorKeluarga(family.getNomorKeluarga());
        response.setKepalaKeluargaId(family.getKepalaKeluargaId());
        Person kepalaKeluarga = personRepository.findById(family.getKepalaKeluargaId()).orElse(null);
        response.setNamaKepalaKeluarga(kepalaKeluarga != null ? kepalaKeluarga.getName() : "N/A");
        response.setSektor(family.getSektor());
        response.setJumlahAnggota(family.getAnggotaKeluarga() != null ? family.getAnggotaKeluarga().size() : 0);
        return response;
    }
}
