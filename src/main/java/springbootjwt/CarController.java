package springbootjwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepo carRepo;

    // save a new car
    @PostMapping
    @PreAuthorize("hasAuthority('VENDOR')")
    Car save(@RequestBody Car car) {
        return carRepo.save(car);
    }

    // find all cars
    @GetMapping // localhost:8080/api/cars GET
    @PreAuthorize("isAuthenticated()")
    List<Car> findAll() {
        return carRepo.findAll();
    }

    // delete car
    @DeleteMapping("/{carId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    void delete(@PathVariable Long carId) {
        carRepo.deleteById(carId);
    }

    // find by id
    @GetMapping("/{carId}")
    @PreAuthorize("isAuthenticated()")
    Car findById(@PathVariable Long carId) {
        return carRepo.findById(carId).orElse(null);
    }
}
