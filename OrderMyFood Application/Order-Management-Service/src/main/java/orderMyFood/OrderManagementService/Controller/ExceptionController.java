package orderMyFood.OrderManagementService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import orderMyFood.OrderManagementService.Exception.ID_NOT_FOUND_EXCEPTION;
import orderMyFood.OrderManagementService.Exception.ITEM_OUT_OF_STOCK_EXCEPTION;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = ID_NOT_FOUND_EXCEPTION.class)
	public ResponseEntity<String> idNotFound(){
		return new ResponseEntity<String>("Id you are looking is not found in the database.",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ITEM_OUT_OF_STOCK_EXCEPTION.class)
	public ResponseEntity<String> itemOutOfStock(){
		return new ResponseEntity<String>("The items you are looking are not in stock, Please try later.",HttpStatus.NOT_FOUND);
	}
}
