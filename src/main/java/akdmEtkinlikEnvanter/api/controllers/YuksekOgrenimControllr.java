package akdmEtkinlikEnvanter.api.controllers;

//import java.util.HashMap;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;

//import akdmEtkinlikEnvanter.business.abstracts.YuksekOgrenimService;
//import akdmEtkinlikEnvanter.entities.concretes.YuksekOgrenim;
//import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;

//@RestController
//@RequestMapping("/api/yuksekOgrenim")
public class YuksekOgrenimControllr {
	
//	@Autowired
//	YuksekOgrenimService yuksekOgrenimService;
//	/*
//	@PostMapping("/add")
//	public ResponseEntity<?> add(@Valid @RequestBody YuksekOgrenim yuksekOgrenim){
//		return ResponseEntity.ok(this.yuksekOgrenimService.add(yuksekOgrenim));
//	}*/
////	@PostMapping("/add")
//	public YuksekOgrenim add(@Valid @RequestBody YuksekOgrenim yuksekOgrenim){
//		return this.yuksekOgrenimService.add(yuksekOgrenim).getData();
//	}
//	
////	@GetMapping("/getAll")
//	public ResponseEntity<?> getAll(){
//		return ResponseEntity.ok(this.yuksekOgrenimService.getAll());
//	}
//	@GetMapping("/getById/{id}")
//	public ResponseEntity<?> getById(@PathVariable(name = "id") int id){
//		return ResponseEntity.ok(this.yuksekOgrenimService.getById(id).getData());
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)//hatalar geldğinde bu mesaj çalışsın
//	@ResponseStatus(HttpStatus.BAD_REQUEST)//bu metod DEFAULT olarak BAD(500) döndürsün
//	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
//		Map<String, String> validationError = new HashMap<>();
//		for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
//			validationError.put(fieldError.getField(), fieldError.getDefaultMessage());
//		}
//		ErrorDataResult<Object> errors = new ErrorDataResult<>(validationError, "Doğrulama Hataları");
//		return errors;
//	}
}
