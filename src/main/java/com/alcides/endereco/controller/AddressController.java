package com.alcides.endereco.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import com.alcides.config.error.ErrorMessage;
import com.alcides.endereco.model.Address;
import com.alcides.endereco.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value="/")
public class AddressController {

    @Autowired AddressService service;

    @Operation(summary = "Buscar endereço por código")
    @ApiResponses(value = {
        @ApiResponse(responseCode =  "200", description = "Created", content =  {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Address.class)
        )}),
        @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> loadById(@PathVariable final Long id){
        Optional<Address> address = service.loadById(id);

        if(address.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(address.get());
    }

    @Operation(summary = "Lista de endereços cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode =  "200", description = "All address", content =  {@Content(
            mediaType = "application/json",
            array =  @ArraySchema(schema = @Schema(implementation = Address.class))
        )})
    })
    @GetMapping("/listAll")
    public List<Address> listAll() {
        return service.listAll();
    }

    @Operation(summary = "Criar novo endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode =  "201", description = "Created", content =  {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Address.class)
        )}),
        @ApiResponse(responseCode = "400", description = "Bad Request", content =  {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @PostMapping()
    public ResponseEntity<?> createAddress(@Valid @RequestBody Address address){
        if (!Objects.isNull(address.getId()))
            return ResponseEntity.badRequest().body(new ErrorMessage("Para atualização utilizar operação Put!"));

        if(Objects.isNull(address.getLatitude()))
            address.setLatitude("");
        if(Objects.isNull(address.getLongitude()))
            address.setLatitude("");

        address = service.create(address);

        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @Operation(summary = "Atualizar endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode =  "200", description = "Updated", content =  {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Address.class)
        )}),
        @ApiResponse(responseCode = "400", description = "Bad Request", content =  {@Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @PutMapping()
    public ResponseEntity<?> updateAddress(@Valid @RequestBody Address address){
        if (Objects.isNull(address.getId()))
            return ResponseEntity.badRequest().body(new ErrorMessage("Id para atualização não informado!"));

        Optional<Address> oldAddress = service.loadById(address.getId());
        if(oldAddress.isEmpty())
            return ResponseEntity.badRequest().body(new ErrorMessage("Endereço para atualização não localizado!"));

        service.update(address);

        return ResponseEntity.ok(address);
    }

    @Operation(summary = "Deletar endereço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted"),
        @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable final Long id ){
        Optional<Address> address = service.loadById(id);

        if(address.isEmpty())
            return ResponseEntity.notFound().build();

        service.delete(id);

        return ResponseEntity.ok().build();
    }
}