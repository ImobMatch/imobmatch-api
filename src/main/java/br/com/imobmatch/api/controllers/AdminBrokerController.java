package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.services.broker.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
    * Controlador para operações administrativas relacionadas a corretores.
    * Permite listar corretores pendentes, aprovar e rejeitar corretores.
    * Acesso restrito a usuários com papel ADMIN.
 */


@RestController
@RequestMapping("/admin/brokers")
@RequiredArgsConstructor
public class AdminBrokerController {

    private final BrokerService brokerService;

    @GetMapping("/pending")
    public ResponseEntity<List<BrokerResponseDTO>> getPendingBrokers() {
        return ResponseEntity.ok(brokerService.listPendingBrokers());
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveBroker(@PathVariable UUID id) {
        brokerService.approveBroker(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectBroker(@PathVariable UUID id) {
        brokerService.rejectBroker(id);
        return ResponseEntity.noContent().build();
    }

}