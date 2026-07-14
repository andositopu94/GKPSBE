package com.GKPS.Controller;

import com.GKPS.DTO.Request.PersonRequestDTO;
import com.GKPS.DTO.Response.PageResponse;
import com.GKPS.DTO.Response.PersonResponseDTO;
import com.GKPS.Service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public PersonResponseDTO createPerson(@RequestBody PersonRequestDTO personRequestDTO) {
        return personService.create(personRequestDTO);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PersonResponseDTO>> findAll(@PageableDefault(size =20, page = 0, sort = "name", direction = Sort.Direction.ASC)Pageable pageable) {
        Page<PersonResponseDTO> personPage = personService.findAll(pageable);
        return ResponseEntity.ok(PageResponse.fromPage(personPage));
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
