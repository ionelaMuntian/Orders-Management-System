package BusinessLogic.Validators;

import Presentation.OrderPageController;

public interface Validator<T> {

    public void validate(T t);

}
