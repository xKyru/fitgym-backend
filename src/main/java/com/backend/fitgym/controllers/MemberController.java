package com.backend.fitgym.controllers;

import com.backend.fitgym.config.ElasticMemberDataMigrator;
import com.backend.fitgym.entities.Member;
import com.backend.fitgym.services.MemberSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.backend.fitgym.dto.MemberDTO;
import com.backend.fitgym.services.MemberService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberSearchService memberSearchService;

    @GetMapping
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        Optional<MemberDTO> member = memberService.getMemberById(id);
        return member.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberDTO> getMemberByEmail(@PathVariable String email) {
        Optional<MemberDTO> member = memberService.getMemberByEmail(email);
        return member.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder("Errores de validación: ");
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(", "));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        MemberDTO createdMember = memberService.createMember(memberDTO);

        try{
            memberSearchService.indexMember(createdMember);
        }catch (IOException e) {
            return ResponseEntity.status(500).body("Miembro guardado en DB, pero falló en Elasticsearch");
        }

        return ResponseEntity.ok(createdMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        MemberDTO updatedMember = memberService.updateMember(id, memberDTO);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        if (memberService.deleteMember(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Elasticseach
    @GetMapping("/search/")
    public List<MemberDTO> searchMembers(@RequestParam String q) throws IOException {
        return memberSearchService.search(q);
    }

    //ELastic Migrator
    @Autowired
    private ElasticMemberDataMigrator dataMigrationService;

    @PostMapping("/migrate-elasticsearch")
    public ResponseEntity<String> migrateMembersToElasticsearch() {
        try {
            String result = dataMigrationService.migrateMembersToElasticsearch();
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error en migración: " + e.getMessage());
        }
    }

}
