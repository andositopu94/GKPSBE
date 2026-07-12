package com.GKPS.Controller;

import com.GKPS.DTO.Request.PersonRequestDTO;
import com.GKPS.DTO.Response.PersonResponseDTO;
import com.GKPS.Service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public PersonResponseDTO createPerson(@RequestBody PersonRequestDTO personRequestDTO) {
        return personService.create(personRequestDTO);
    }

    @GetMapping
    public Page<PersonResponseDTO> findAll(@PageableDefault(size =20, page = 0)Pageable pageable) {
        return personService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PersonResponseDTO findById(@PathVariable String id) {
        return personService.findById(id);
    }

    @PutMapping("/{id}")
    public PersonResponseDTO updatePerson(@PathVariable String id, @RequestBody PersonRequestDTO personRequestDTO) {
        return personService.update(id, personRequestDTO);
    }

    @PatchMapping("/{id}/deactive")
    public void deactivePerson(@PathVariable String id) {
        personService.deactive(id);
    }
}
