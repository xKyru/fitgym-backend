package com.backend.fitgym.controllers;

import com.backend.fitgym.services.MemberSearchService;
import com.backend.fitgym.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members/search")
public class MemberSearchController {

    @Autowired
    private MemberSearchService memberSearchService;

    // Buscar miembros por query
    @GetMapping("/")
    public List<MemberDTO> searchMembers(@RequestParam String q) throws IOException {
        return memberSearchService.search(q);
    }

    // Indexar o actualizar miembro
    @PostMapping("/index")
    public ResponseEntity<?> indexMember(@RequestBody MemberDTO member) {
        try {
            memberSearchService.indexMember(member);
            return ResponseEntity.ok("Miembro indexado correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al indexar en Elasticsearch");
        }
    }

    // Actualizar miembro (puede ser igual que indexar, Elasticsearch reemplaza documento con mismo id)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody MemberDTO member) {
        try {
            member.setId(id); // asegurar que el id esté presente
            memberSearchService.indexMember(member);
            return ResponseEntity.ok("Miembro actualizado correctamente en Elasticsearch");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al actualizar en Elasticsearch");
        }
    }

    // Eliminar miembro de Elasticsearch
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        try {
            memberSearchService.deleteMemberById(id);
            return ResponseEntity.ok("Miembro eliminado de Elasticsearch");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al eliminar de Elasticsearch");
        }
    }

    // Obtener todos los miembros indexados (opcional)
    @GetMapping("/all")
    public List<MemberDTO> getAllMembers() throws IOException {
        return memberSearchService.getAllMembers();
    }

    @GetMapping("/facets")
    public ResponseEntity<Map<String, Long>> getMemberFacets() {
        try {
            Map<String, Long> facets = memberSearchService.getMemberFacets();
            return ResponseEntity.ok(facets);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }



}
