package orderMyFood.RestaurantSearchService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import orderMyFood.RestaurantSearchService.Exception.ID_NOT_FOUND_EXCEPTION;


@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = ID_NOT_FOUND_EXCEPTION.class)
	public ResponseEntity<String> idNotFound(){
		return new ResponseEntity<String>("Id you are looking is not found in the database.",HttpStatus.NOT_FOUND);
	}
}
