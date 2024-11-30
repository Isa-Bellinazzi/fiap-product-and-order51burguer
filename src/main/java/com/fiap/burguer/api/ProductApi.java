package com.fiap.burguer.api;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.ProductCreate;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public interface ProductApi {

    @GetMapping("/{id}")
    @Operation(summary = "Consulta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou produto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<Product> getProductById(
            @Parameter(description = "ID do produto a ser consultado", required = true) @PathVariable("id") int id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @GetMapping("/category/{category}")
    @Operation(summary = "Consulta produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou produtos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Categoria de produto inválida",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados para a categoria",
                    content = @Content) ,
            @ApiResponse(responseCode = "406", description = "Tipo de mídia não aceitável",
                    content = @Content)})

    public ResponseEntity<List<Product>> getProductsByCategory(
            @Parameter(description = "Categoria dos produtos a serem consultados", required = true) @PathVariable("category") CategoryProduct category,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody Product postProduct(@RequestBody @Valid ProductCreate productCreate,
                                             @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @PutMapping(name = "/update", produces = "application/json")
    @Operation(summary = "Atualiza produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody Product putProduct(@Valid Product product,
                                            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletou produto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto a ser deletado", required = true) @PathVariable("id") int id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);
}

