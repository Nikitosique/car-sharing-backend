package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.CarModel;
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
@Sql({"classpath:initalscripts/dao/carmodel/CarModelTableCreation.sql",
        "classpath:initalscripts/dao/carmodel/CarModelDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/carmodel/CarModelTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CarModelDAOTest {
    @Autowired
    private CarModelDAO carModelDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/carmodel/CarModelDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<CarModel> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<CarModel> actual = carModelDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() {
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

        int pageNumber = 0;
        int pageSize = 2;
        List<CarModel> actual = carModelDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            carModelDAO.getById(0);
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

        CarModel actual = carModelDAO.getById(1);

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
        added.setBrandId(10);
        added.setName("Model-CFS");
        added.setEngineDisplacement(2.3);
        added.setFuelId(3);
        added.setGearboxType("automatic");
        added.setProductionYear(2021);

        CarModel actual = carModelDAO.add(added);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() {
        boolean expected = false;

        CarModel updatedCarModel = new CarModel();
        updatedCarModel.setId(0);
        updatedCarModel.setBrandId(1);
        updatedCarModel.setName("Model-NEW");
        updatedCarModel.setEngineDisplacement(2.0);
        updatedCarModel.setFuelId(1);
        updatedCarModel.setGearboxType("automatic");
        updatedCarModel.setProductionYear(2020);

        boolean actual = carModelDAO.updateById(updatedCarModel);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        boolean expected = true;

        CarModel updatedCarModel = new CarModel();
        updatedCarModel.setId(1);
        updatedCarModel.setBrandId(1);
        updatedCarModel.setName("Model-NEW");
        updatedCarModel.setEngineDisplacement(2.0);
        updatedCarModel.setFuelId(1);
        updatedCarModel.setGearboxType("automatic");
        updatedCarModel.setProductionYear(2020);

        boolean actual = carModelDAO.updateById(updatedCarModel);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = carModelDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = carModelDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}