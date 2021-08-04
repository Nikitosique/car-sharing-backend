package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.DiscountCardDAO;
import dev.andrylat.carsharing.models.DiscountCard;
import dev.andrylat.carsharing.services.DiscountCardService;
import dev.andrylat.carsharing.services.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscountCardServiceImpl implements DiscountCardService {
    private final DiscountCardDAO discountCardDAO;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardDAO discountCardDAO) {
        this.discountCardDAO = discountCardDAO;
    }

    @Override
    public long getRecordsNumber() {
        return discountCardDAO.getRecordsNumber();
    }

    @Override
    public List<DiscountCard> getAll(int pageNumber, int pageSize) {
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return discountCardDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public DiscountCard getById(long id) {
        Validator.validateRecordId(id);

        return discountCardDAO.getById(id);
    }

    @Override
    public DiscountCard add(DiscountCard objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(DiscountCard updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
