package com.fiap.burguer.api;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
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
@RequestMapping("/orders")
public interface OrderApi {

    @PostMapping
    @Operation(summary = "Cria um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content)})
     ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest,
                                         @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @GetMapping
    @Operation(summary = "Consulta todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado",
                    content = @Content)})
    public ResponseEntity<List<OrderResponse>> getAllOrders(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @GetMapping("/status/{status}")
    @Operation(summary = "Consulta pedidos por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Status inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado com o status informado",
                    content = @Content)})
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Status do pedido", required = true)
            @PathVariable("status") StatusOrder status,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @GetMapping("/{id}")
    @Operation(summary = "Consulta pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Id de pedido inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID do pedido a ser consultado", required = true) @PathVariable("id") int id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content)})
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "ID do pedido a ser atualizado", required = true)
            @PathVariable("id") int id,
            @Parameter(description = "Novo status do pedido", required = true, schema = @Schema(allowableValues = {
                    "PREPARATION",
                    "READY",
                    "FINISHED",
                    "CANCELED"}))
            @RequestParam StatusOrder newStatus,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader);

}
