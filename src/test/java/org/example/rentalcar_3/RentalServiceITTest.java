package org.example.rentalcar_3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class RentalServiceITTest {

    @Autowired
    private RentalService rentalService;
    @MockBean
    private CarStorage carStorage;
    @MockBean
    private RentalStorage rentalStorage;

    @Test
    void shouldCarExistInStorage() {
        //Given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);

        //When
        Optional<Car> carOptional = rentalService.carExist(car.getVinNumber());

        //Then
        assertThat(carOptional).isPresent();
    }

    @Test
    void shouldCarNotExistInStorage() {
        //When
        Optional<Car> carOptional = rentalService.carExist("vinNumber");

        //Then
        assertThat(carOptional).isEmpty();
    }

    @Test
    void estimatePrice_withoutParameters_shouldReturnEquals() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,25);
        LocalDate dateEnd = LocalDate.of(2023,12,26);

        //when
        double estimatePrice = rentalService.estimatePrice(car.getVinNumber(), dateStart, dateEnd);

        //then
        assertEquals(750,estimatePrice);
    }

    @Test
    void estimatePrice_withoutParameters_shouldReturnNull() {
        //given
        LocalDate dateStart = LocalDate.of(2023,12,5);
        LocalDate dateEnd = LocalDate.of(2023,12,6);

        //when

        //then
        assertThrows(NoSuchElementException.class, () -> rentalService.estimatePrice("12345", dateStart, dateEnd));
    }

    @Test
    void isAvailable_withoutParameters_shouldReturnsTrue() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,5);
        LocalDate dateEnd = LocalDate.of(2023,12,12);

        //when
        boolean available = rentalService.isAvailable(car.getVinNumber(), dateStart, dateEnd);

        //then
        assertEquals(Boolean.TRUE, available);
    }

    @Test
    void isAvailable_withoutParameters_shouldReturnsFalse() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,25);
        LocalDate dateEnd = LocalDate.of(2023,12,29);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 500);
        rentalStorage.addRental(rental);
        //when
        boolean available = rentalService.isAvailable(car.getVinNumber(), dateStart, dateEnd);

        //then
        assertEquals(Boolean.FALSE, available);
    }

    @ParameterizedTest()
    @MethodSource("testToOverlappingFalse")
    void isAvailable_withParameters_shouldReturnsFalseBecauseOfOverlappingFalse(LocalDate dateStartFromParameter, LocalDate dateEndFromParameter) {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,3);
        LocalDate dateEnd = LocalDate.of(2023,12,15);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 500);
        rentalStorage.addRental(rental);
        //when
        boolean available = rentalService.isAvailable(car.getVinNumber(), dateStartFromParameter, dateEndFromParameter);

        //then
        assertEquals(Boolean.FALSE, available);
    }

    public static Stream<Arguments> testToOverlappingFalse() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 5), LocalDate.of(2023,12,10)),
                Arguments.of(LocalDate.of(2023, 12, 5), LocalDate.of(2023,12,5)),
                Arguments.of(LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,20)),
                Arguments.of(LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,10)),
                Arguments.of(LocalDate.of(2023, 12, 10), LocalDate.of(2023,12,20))
        );
    }
    @ParameterizedTest()
    @MethodSource("testToOverlappingTrue")
    void isAvailable_withParameters_shouldReturnsFalseBecauseOfOverlappingTrue(LocalDate dateStartFromParameter, LocalDate dateEndFromParameter) {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,3);
        LocalDate dateEnd = LocalDate.of(2023,12,15);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 500);
        rentalStorage.addRental(rental);
        //when
        boolean available = rentalService.isAvailable(car.getVinNumber(), dateStartFromParameter, dateEndFromParameter);

        //then
        assertEquals(Boolean.TRUE, available);
    }

    public static Stream<Arguments> testToOverlappingTrue() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 1), LocalDate.of(2023,12,2)),
                Arguments.of(LocalDate.of(2023, 12, 16), LocalDate.of(2023,12,16)),
                Arguments.of(LocalDate.of(2023, 12, 17), LocalDate.of(2023,12,24)),
                Arguments.of(LocalDate.of(2023, 12, 25), LocalDate.of(2023,12,26)),
                Arguments.of(LocalDate.of(2023, 12, 29), LocalDate.of(2023,12,31))
        );
    }
    @Test
    void rent_withoutParameters_shouldReturnsEquals() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,5);
        LocalDate dateEnd = LocalDate.of(2023,12,6);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 750);
        //when
        Rental rent = rentalService.rent(1, "vinNumber", dateStart, dateEnd);
        //then
        assertEquals(rent.getCar().getVinNumber(), rental.getCar().getVinNumber());
    }

    @Test
    void rent_withoutParameters_shouldReturnsNull() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,5);
        LocalDate dateEnd = LocalDate.of(2023,12,6);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 750);
        rentalStorage.addRental(rental);
        //when

        //then
        assertNull(rentalService.rent(1, "vinNumber", LocalDate.of(2023, 12, 3), LocalDate.of(2023,12,10)));
    }
    @ParameterizedTest
    @MethodSource("testToOverlappingFalse")
    void rent_withParameters_shouldReturnsNull() {
        //given
        Car car = new Car("marka","model", "vinNumber", Type.STANDARD);
        carStorage.addCar(car);
        LocalDate dateStart = LocalDate.of(2023,12,3);
        LocalDate dateEnd = LocalDate.of(2023,12,15);

        Rental rental = new Rental(new User(1), car, dateStart, dateEnd, 750);
        rentalStorage.addRental(rental);
        //when

        //then
        assertNull(rentalService.rent(1, "vinNumber", LocalDate.of(2023, 12, 3), LocalDate.of(2023,12,10)));
    }



}