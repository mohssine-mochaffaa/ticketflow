package com.ticketflow.customerservice.controllers;

import com.ticketflow.customerservice.dto.CustomerRequest;
import com.ticketflow.customerservice.dto.CustomerResponse;
import com.ticketflow.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private final ApplicationContext applicationContext;


    public CustomerController(CustomerService service, ApplicationContext applicationContext) {
        this.service = service;
        this.applicationContext = applicationContext;
    }

    @PostMapping
    ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        System.out.println("ana hna");
        CustomerResponse created = CustomerResponse.from(service.create(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/instance/test")
    public String getInstance() {
        return "Handled by port: " + ((ServletWebServerApplicationContext)applicationContext).getWebServer().getPort();
    }

    @GetMapping("/{id}")
    CustomerResponse get(@PathVariable Long id) {
        return CustomerResponse.from(service.get(id));
    }

    @GetMapping
    List<CustomerResponse> list() {
        return service.list().stream().map(CustomerResponse::from).toList();
    }

    @PutMapping("/{id}")
    CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return CustomerResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
