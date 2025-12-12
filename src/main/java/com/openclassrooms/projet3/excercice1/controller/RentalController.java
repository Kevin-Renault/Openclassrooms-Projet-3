package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.dto.RentalDto;
import com.openclassrooms.projet3.excercice1.dto.RentalResponse;
import com.openclassrooms.projet3.excercice1.service.RentalService;
import com.openclassrooms.projet3.excercice1.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final AuthService authService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalDto> create(
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") Integer price,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            @RequestParam(value = "description", required = false) String description,
            Authentication authentication) {

        // Récupérer l'utilisateur connecté depuis le token JWT
        Long ownerId = authService.getCurrentUser(authentication).getId();

        RentalDto rentalDto = new RentalDto();
        rentalDto.setName(name);
        rentalDto.setSurface(surface);
        rentalDto.setPrice(price);
        rentalDto.setDescription(description);
        rentalDto.setOwnerId(ownerId);

        RentalDto createdRental = rentalService.create(rentalDto, picture);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        RentalDto rental = rentalService.findById(id);
        return ResponseEntity.ok(rental);
    }

    @GetMapping
    public ResponseEntity<RentalResponse> findAll() {
        List<RentalDto> rentals = rentalService.findAll();
        return ResponseEntity.ok(new RentalResponse(rentals));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RentalDto>> findByOwnerId(@PathVariable Long ownerId) {
        List<RentalDto> rentals = rentalService.findByOwnerId(ownerId);
        return ResponseEntity.ok(rentals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDto> update(@PathVariable Long id, @Valid @RequestBody RentalDto rentalDto) {
        RentalDto updatedRental = rentalService.update(id, rentalDto);
        return ResponseEntity.ok(updatedRental);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
