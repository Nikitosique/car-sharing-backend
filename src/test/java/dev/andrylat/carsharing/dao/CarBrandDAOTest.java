package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.CarBrand;
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
@Sql({"classpath:initalscripts/dao/carbrand/CarBrandTableCreation.sql",
        "classpath:initalscripts/dao/carbrand/CarBrandDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/carbrand/CarBrandTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CarBrandDAOTest {
    @Autowired
    private CarBrandDAO carBrandDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/carbrand/CarBrandDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<CarBrand> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<CarBrand> actual = carBrandDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() {
        List<CarBrand> expected = new ArrayList<>(List.of(
                new CarBrand(1, "nissan"),
                new CarBrand(2, "audi")));

        int pageNumber = 0;
        int pageSize = 2;
        List<CarBrand> actual = carBrandDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            carBrandDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        CarBrand expected = new CarBrand(1, "nissan");
        CarBrand actual = carBrandDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        CarBrand expected = new CarBrand(4, "BMW");
        CarBrand actual = carBrandDAO.add(new CarBrand(4, "BMW"));
        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() {
        boolean expected = false;

        CarBrand updatedCarBrand = new CarBrand(0, "New Type");
        boolean actual = carBrandDAO.updateById(updatedCarBrand);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        boolean expected = true;

        CarBrand updatedCarBrand = new CarBrand(1, "New Type");
        boolean actual = carBrandDAO.updateById(updatedCarBrand);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = carBrandDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = carBrandDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}