package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:initalscripts/dao/car/CarTableCreation.sql",
        "classpath:initalscripts/dao/car/CarDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/car/CarTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CarDAOTest {
    @Autowired
    private CarDAO carDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/car/CarDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<Car> expected = new ArrayList<>();
        List<Car> actual = carDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnAllRecords_WhenTableIsNotEmpty() {
        List<Car> expected = new ArrayList<>();

        Car car = new Car();
        car.setId(1);
        car.setModelId(7);
        car.setRegistrationPlate("AA1234BB");
        car.setRentCostPerMin(10);
        car.setColor("red");
        car.setPhoto("car_1.png");
        expected.add(car);

        car = new Car();
        car.setId(2);
        car.setModelId(32);
        car.setRegistrationPlate("QZ1WD403");
        car.setRentCostPerMin(20);
        car.setColor("white");
        car.setPhoto("car_2.png");
        expected.add(car);

        car = new Car();
        car.setId(3);
        car.setModelId(18);
        car.setRegistrationPlate("VS399EK");
        car.setRentCostPerMin(7);
        car.setColor("black");
        car.setPhoto("car_3.png");
        expected.add(car);

        List<Car> actual = carDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            carDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        Car expected = new Car();
        expected.setId(1);
        expected.setModelId(7);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(10);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        Car actual = carDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        Car expected = new Car();
        expected.setId(4);
        expected.setModelId(2);
        expected.setRegistrationPlate("LR3451CO");
        expected.setRentCostPerMin(16);
        expected.setColor("white");
        expected.setPhoto("car_4.png");

        Car added = new Car();
        added.setModelId(2);
        added.setRegistrationPlate("LR3451CO");
        added.setRentCostPerMin(16);
        added.setColor("white");
        added.setPhoto("car_4.png");

        Car actual = carDAO.add(added);
        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldThrownException_WhenUpdatedRecordNotExists() {
        Car updated = new Car();
        updated.setId(0);
        updated.setModelId(1);
        updated.setRegistrationPlate("QQ1111QQ");
        updated.setRentCostPerMin(1);
        updated.setColor("black");
        updated.setPhoto("car_0.png");

        assertThrows(RecordNotFoundException.class, () -> {
            carDAO.updateById(updated);
        });
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        Car expected = new Car();
        expected.setId(1);
        expected.setModelId(2);
        expected.setRegistrationPlate("LR3451CO");
        expected.setRentCostPerMin(16);
        expected.setColor("white");
        expected.setPhoto("car_4.png");

        Car updated = new Car();
        updated.setId(1);
        updated.setModelId(2);
        updated.setRegistrationPlate("LR3451CO");
        updated.setRentCostPerMin(16);
        updated.setColor("white");
        updated.setPhoto("car_4.png");

        Car actual = carDAO.updateById(updated);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = carDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = carDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}