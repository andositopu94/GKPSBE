package com.GKPS.Controller;

import com.GKPS.DTO.Response.PageResponse;
import com.GKPS.Model.Organisasi.Organization;
import com.GKPS.Service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/organizations", "/api/admin/jemaat/organizations"})
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<PageResponse<Organization>> getAll(@RequestParam(required = false) Boolean active, @PageableDefault(page = 0, size=20, sort = "name", direction = Sort.Direction.ASC)Pageable pageable) {
        if (Boolean.TRUE.equals(active)) {
             ResponseEntity.ok(organizationService.getActiveOrganizations());
        }
        Page<Organization> organizationPage = organizationService.getAllOrganizations(pageable);
        return ResponseEntity.ok(PageResponse.fromPage(organizationPage));
    }

    @GetMapping("{id}")
    public ResponseEntity<Organization> getById(@RequestParam String id) {
        return organizationService.getOrganizationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Organization> create(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.createOrganization(organization));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Organization> update(@PathVariable String id, @RequestBody Organization organization) {
        if (!organizationService.getOrganizationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organizationService.updateOrganization(id, organization));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!organizationService.getOrganizationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }

}
