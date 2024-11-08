package com.example.proyectofinalbackend.Controllers;

import com.example.proyectofinalbackend.dto.RegistroDTO;
import com.example.proyectofinalbackend.model.Cliente;
import com.example.proyectofinalbackend.model.Reserva;
import com.example.proyectofinalbackend.model.Usuario;
import com.example.proyectofinalbackend.model.Vehiculo;
import com.example.proyectofinalbackend.services.ReservaService;
import com.example.proyectofinalbackend.services.VehiculoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.internal.icu.impl.Punycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jdk.internal.icu.impl.Punycode.encode;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private VehiculoService vehiculoService;
    private ClienteController UsuarioService;
    private Punycode passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarCliente(@RequestBody RegistroDTO registro) {
        Cliente cliente = new Cliente();
        cliente.setNombre(registro.getNombre());
        cliente.setEmail(registro.getEmail());
        Usuario usuario = new Usuario();
        usuario.setUsername(registro.getUsername());
        usuario.setPassword(String.valueOf(encode(registro.getPassword())));
        return ResponseEntity.ok(UsuarioService.registrarCliente(usuario, cliente));
    }

    private char[] encode(@NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        return new char[0];
    }

    private Object registrarCliente(Usuario usuario, Cliente cliente) {
        return null;
    }

    @GetMapping("/vehiculos/buscar")
    public List<Vehiculo> buscarVehiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo) {
        return vehiculoService.buscarVehiculos(marca, modelo);
    }

    @PostMapping("/reservas")
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @PutMapping("/reservas/{id}")
    public Reserva modificarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.modificarReserva(id, reserva);
    }

    @DeleteMapping("/reservas/{id}")
    public void cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
    }

    @GetMapping("/mis-reservas")
    public List<Reserva> misReservas(@AuthenticationPrincipal Usuario usuario) {
        return reservaService.obtenerReservasCliente(usuario.getCliente().getId());
    }
}