package com.GKPS.Service;

import com.GKPS.DTO.Request.PersonRequestDTO;
import com.GKPS.DTO.Response.PersonResponseDTO;
import com.GKPS.Model.Organisasi.Person;
import com.GKPS.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public PersonResponseDTO create(PersonRequestDTO personRequestDTO) {
        if (personRepository.existsByNik(personRequestDTO.getNik())) {
            throw new IllegalArgumentException("NIK " + personRequestDTO.getNik() + " sudah terdaftar");
        }

        Person person = new Person();

        person.setNik(personRequestDTO.getNik());
        person.setName(personRequestDTO.getName());
        person.setJenisKelamin(personRequestDTO.getJenisKelamin());
        person.setTanggalLahir(personRequestDTO.getTanggalLahir());
        person.setAlamat(personRequestDTO.getAlamat());
        person.setSektor(personRequestDTO.getSektor());
        person.setNoHp(personRequestDTO.getNoHp());
        person.setEmail(personRequestDTO.getEmail());
        person.setStatusPernikahan(personRequestDTO.getStatusPernikahan());
        person.setActive(true);

        return toResponse(personRepository.save(person));
    }

    public Page<PersonResponseDTO> findAll(Pageable pageable) {
        return personRepository.findAll(pageable).map(this::toResponse);
    }

    public PersonResponseDTO findById(String id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Person dengan ID " + id + " tidak ditemukan"));
        return toResponse(person);
    }

    public PersonResponseDTO update(String id, PersonRequestDTO personRequestDTO) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Person dengan ID " + id + " tidak ditemukan"));

        if (!person.getNik().equals(personRequestDTO.getNik()) && personRepository.existsByNik(personRequestDTO.getNik())) {
            throw new IllegalArgumentException("NIK " + personRequestDTO.getNik() + " sudah terdaftar");
        }

        person.setNik(personRequestDTO.getNik());
        person.setName(personRequestDTO.getName());
        person.setJenisKelamin(personRequestDTO.getJenisKelamin());
        person.setTanggalLahir(personRequestDTO.getTanggalLahir());
        person.setAlamat(personRequestDTO.getAlamat());
        person.setSektor(personRequestDTO.getSektor());
        person.setNoHp(personRequestDTO.getNoHp());
        person.setEmail(personRequestDTO.getEmail());
        person.setStatusPernikahan(personRequestDTO.getStatusPernikahan());

        return toResponse(personRepository.save(person));

    }

    public void deactive(String id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Person dengan ID " + id + " tidak ditemukan"));
        person.setActive(false);
        personRepository.save(person);
    }

    private PersonResponseDTO toResponse(Person person) {
        PersonResponseDTO response = new PersonResponseDTO();
        response.setId(person.getId());
        response.setNik(person.getNik());
        response.setName(person.getName());
        response.setJenisKelamin(person.getJenisKelamin());
        response.setTanggalLahir(person.getTanggalLahir());
        response.setAlamat(person.getAlamat());
        response.setSektor(person.getSektor());
        response.setNoHp(person.getNoHp());
        response.setEmail(person.getEmail());
        response.setStatusPernikahan(person.getStatusPernikahan());
        response.setActive(person.getActive());
        return response;
    }
}
