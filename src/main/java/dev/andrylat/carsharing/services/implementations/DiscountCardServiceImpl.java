package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.DiscountCardDAO;
import dev.andrylat.carsharing.models.DiscountCard;
import dev.andrylat.carsharing.services.DiscountCardService;
import dev.andrylat.carsharing.services.validators.DiscountCardValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
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
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return discountCardDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public DiscountCard getById(long id) {
        ParametersValidator.validateRecordId(id);

        return discountCardDAO.getById(id);
    }

    @Override
    public DiscountCard add(DiscountCard objectToAdd) {
        ObjectValidator<DiscountCard> validator = new DiscountCardValidator();
        validator.validate(objectToAdd);

        return discountCardDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(DiscountCard updatedObject) {
        ObjectValidator<DiscountCard> validator = new DiscountCardValidator();
        validator.validate(updatedObject);

        return discountCardDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return discountCardDAO.deleteById(id);
    }

}
