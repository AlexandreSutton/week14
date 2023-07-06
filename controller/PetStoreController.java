package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.entity.Employee;
import pet.store.service.PetStoreService;

@RestController
@Slf4j
public class PetStoreController<PetStoreCustomer, PetStoreEmployee> {
	
	@Autowired
	private PetStoreService petStoreService;
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	@PutMapping("/pet_store/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addEmployee(@PathVariable Long petStoreId,
		@RequestBody PetStoreEmployee employeeId) {
		log.info("Creating an employee for pet store {}", petStoreId);
		return (PetStoreEmployee) petStoreService.saveEmployee(petStoreId, (Employee) employeeId);
	}
	@PostMapping("{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertPetStoreCustomer(@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Creating a customer for pet store {}", petStoreId);
		return (PetStoreCustomer) petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	@GetMapping("/petStore")
	public List<PetStoreData> retrieveAllPetStores() {
	log.info("retrieving all pet stores");
	return petStoreService.retrieveAllPetStores();
	}
	@GetMapping("/pet_store/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store by ID={}", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
@DeleteMapping("{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
	log.info("Deleting pet store with id ={}", petStoreId);
	petStoreService.deletePetStoreById(petStoreId);
	return Map.of("Message", "Deletion of contributor with ID=" + petStoreId + " was successfull.");
}
	
}