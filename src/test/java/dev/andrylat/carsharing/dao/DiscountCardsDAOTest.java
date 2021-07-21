package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.DiscountCard;
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
@Sql({"classpath:initalscripts/dao/discountcard/DiscountCardTableCreation.sql",
        "classpath:initalscripts/dao/discountcard/DiscountCardDataInsertion.sql"})
@Sql(scripts = "classpath:initalscripts/dao/discountcard/DiscountCardTableDropping.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DiscountCardsDAOTest {
    @Autowired
    private DiscountCardDAO discountCardDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql({"classpath:initalscripts/dao/discountcard/DiscountCardDataDeletion.sql"})
    public void getAll_ShouldReturnZeroRecords_WhenTableIsEmpty() {
        List<DiscountCard> expected = new ArrayList<>();

        int pageNumber = 0;
        int pageSize = 2;
        List<DiscountCard> actual = discountCardDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getAll_ShouldReturnFirstPageWithRecords_WhenTableIsNotEmpty() {
        List<DiscountCard> expected = new ArrayList<>();

        DiscountCard card = new DiscountCard();
        card.setId(1);
        card.setCardNumber("8267bacb06d2");
        card.setDiscountValue(6);
        expected.add(card);

        card = new DiscountCard();
        card.setId(2);
        card.setCardNumber("96853b88d930");
        card.setDiscountValue(4);
        expected.add(card);

        int pageNumber = 0;
        int pageSize = 2;
        List<DiscountCard> actual = discountCardDAO.getAll(pageNumber, pageSize);

        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldThrownException_WhenRecordWithSuchIdNotExists() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            discountCardDAO.getById(0);
        });
    }

    @Test
    public void getById_ShouldReturnOneRecord_WhenRecordWithSuchIdExists() {
        DiscountCard expected = new DiscountCard();
        expected.setId(1);
        expected.setCardNumber("8267bacb06d2");
        expected.setDiscountValue(6);

        DiscountCard actual = discountCardDAO.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldAddRecord_WhenDataAreInserted() {
        DiscountCard expected = new DiscountCard();
        expected.setId(4);
        expected.setCardNumber("e05932ac8e97");
        expected.setDiscountValue(14);

        DiscountCard added = new DiscountCard();
        added.setCardNumber("e05932ac8e97");
        added.setDiscountValue(14);

        DiscountCard actual = discountCardDAO.add(added);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldNotUpdateRecord_WhenUpdatedRecordNotExists() {
        boolean expected = false;

        DiscountCard updatedDiscountCard = new DiscountCard();
        updatedDiscountCard.setId(0);
        updatedDiscountCard.setCardNumber("d72d17f7609c");
        updatedDiscountCard.setDiscountValue(12);

        boolean actual = discountCardDAO.updateById(updatedDiscountCard);

        assertEquals(expected, actual);
    }

    @Test
    public void updateById_ShouldUpdateRecord_WhenUpdatedRecordExists() {
        boolean expected = true;

        DiscountCard updatedDiscountCard = new DiscountCard();
        updatedDiscountCard.setId(1);
        updatedDiscountCard.setCardNumber("d72d17f7609c");
        updatedDiscountCard.setDiscountValue(12);

        boolean actual = discountCardDAO.updateById(updatedDiscountCard);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteNoRecords_WhenDeletedRecordNotExists() {
        boolean expected = false;
        boolean actual = discountCardDAO.deleteById(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenDeletedRecordExists() {
        boolean expected = true;
        boolean actual = discountCardDAO.deleteById(1);
        assertEquals(expected, actual);
    }

}