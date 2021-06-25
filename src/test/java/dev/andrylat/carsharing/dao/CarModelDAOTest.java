package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.CarModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql({"classpath:initalscripts/dao/carmodel/CarModelTableCreation.sql",
        "classpath:initalscripts/dao/carmodel/CarModelDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/carmodel/CarModelTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CarModelDAOTest {
    @Autowired
    private CarModelDAO CarModelDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/carmodel/CarModelDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<CarModel> expected = new ArrayList<>();
        List<CarModel> actual = CarModelDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnAllRecords_WhenTableIsNotEmpty() {
        List<CarModel> expected = new ArrayList<>();

        CarModel carModel = new CarModel();
        carModel.setId(1);
        carModel.setBodyId(1);
        carModel.setBrandId(5);
        carModel.setName("Type-345");
        carModel.setEngineDisplacement(1.8);
        carModel.setFuelId(2);
        carModel.setGearboxType("manual");
        carModel.setProductionYear(2017);
        expected.add(carModel);

        carModel = new CarModel();
        carModel.setId(2);
        carModel.setBodyId(2);
        carModel.setBrandId(3);
        carModel.setName("Model-CE1");
        carModel.setEngineDisplacement(2.0);
        carModel.setFuelId(1);
        carModel.setGearboxType("automatic");
        carModel.setProductionYear(2020);
        expected.add(carModel);

        carModel = new CarModel();
        carModel.setId(3);
        carModel.setBodyId(3);
        carModel.setBrandId(1);
        carModel.setName("Type-AAA");
        carModel.setEngineDisplacement(1.6);
        carModel.setFuelId(3);
        carModel.setGearboxType("manual");
        carModel.setProductionYear(2018);
        expected.add(carModel);

        List<CarModel> actual = CarModelDAO.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            CarModelDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        CarModel expected = new CarModel();
        expected.setId(1);
        expected.setBodyId(1);
        expected.setBrandId(5);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        CarModel actual = CarModelDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        CarModel expected = new CarModel();
        expected.setId(4);
        expected.setBrandId(10);
        expected.setName("Model-CFS");
        expected.setEngineDisplacement(2.3);
        expected.setFuelId(3);
        expected.setGearboxType("automatic");
        expected.setProductionYear(2021);

        CarModel added = new CarModel();
        added.setId(4);
        added.setBrandId(10);
        added.setName("Model-CFS");
        added.setEngineDisplacement(2.3);
        added.setFuelId(3);
        added.setGearboxType("automatic");
        added.setProductionYear(2021);

        CarModel actual = CarModelDAO.add(added);
        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldThrownException_WhenUpdatedRecordNotExists() {
        CarModel updated = new CarModel();
        updated.setId(0);
        updated.setBrandId(1);
        updated.setName("Model-NEW");
        updated.setEngineDisplacement(2.0);
        updated.setFuelId(1);
        updated.setGearboxType("automatic");
        updated.setProductionYear(2020);

        assertThrows(RecordNotFoundException.class, () -> {
            CarModelDAO.updateById(updated);
        });
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        CarModel expected = new CarModel();
        expected.setId(1);
        expected.setBrandId(1);
        expected.setName("Model-NEW");
        expected.setEngineDisplacement(2.0);
        expected.setFuelId(1);
        expected.setGearboxType("automatic");
        expected.setProductionYear(2020);

        CarModel updated = new CarModel();
        updated.setId(1);
        updated.setBrandId(1);
        updated.setName("Model-NEW");
        updated.setEngineDisplacement(2.0);
        updated.setFuelId(1);
        updated.setGearboxType("automatic");
        updated.setProductionYear(2020);

        CarModel actual = CarModelDAO.updateById(updated);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = CarModelDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = CarModelDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}