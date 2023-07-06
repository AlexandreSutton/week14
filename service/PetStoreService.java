package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.dao.customerDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	
	@Autowired
	private PetStoreDao petStoreDao;
	
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
		copyPetStoreFields(petStore, petStoreData);
		PetStore dbPetStore = petStoreDao.save(petStore);
		return new PetStoreData(dbPetStore);
	}
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}
	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " does not exist."));
	}
	
	@Transactional(readOnly = false)
	public Employee saveEmployee(Long petStoreId, Employee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = new Employee();
		return new Employee(dbEmployee);
	}
	private void copyEmployeeFields(Employee employee, Employee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		petStoreEmployee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		petStoreEmployee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		Employee.setEmployeePhone(petStoreEmployee.getEmployeeId());
	}
	
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;
		
			if (Objects.isNull(employeeId)) {
				employee = new Employee();
			} else {
				employee = findEmployeeById(employeeId);
			}
			return employee;
	}
	private Employee findEmployeeById(Long employeeId) throws NoSuchElementException {
		return EmployeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee with employee ID=" + employeeId + " does not exist."));
			
		}
	
	@Transactional(readOnly = false)
	public <PetStoreCustomer> PetStoreData saveCustomer(Long petStoreId, PetStoreCustomer petStorecustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = ((Customer) petStorecustomer).getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);
		setFieldsInCustomer(customer, petStorecustomer);
		customer.getPetStore().add(petStore);
		petStore.getCustomers().add(customer);
		Customer dbCustomer = new Customer();
		return new PetStoreData(dbCustomer);
	}
	
	private <PetStoreCustomer> void setFieldsInCustomer(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerFirstName(((Customer) petStoreCustomer).getCustomerFirstName());
		customer.setCustomerLastName(((Customer) petStoreCustomer).getCustomerLastName());
		customer.setCustomerEmail(((Customer) petStoreCustomer).getCustomerEmail());
		
	}
	
	private Customer findOrCreateCustomer(Long petStoreId, Long CustomerId) {
		Customer customer = null;
		  if(Objects.isNull(customer)) {
			  customer = new Customer();
		  } else {
			  customer = findCustomerById(petStoreId, customer);
		  }
	return customer;
	}
	
	private Customer findCustomerById(Long petStoreId, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	private Customer findCustomerById(Long petStoreId, Long customer2) {
		Customer customer = new Customer();
		
boolean found = false;

for (PetStore petStore : customer.getPetStore()) {
	if (petStore.getPetStoreId() == petStoreId) {
		found = true;
	  break;
} else {
	if(!found) {
		throw new IllegalArgumentException("Customer with ID =" + customer2 + " is not a member of the pet store with ID =" + petStoreId);
	}
  }
}
return customer;
}
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData pad = new PetStoreData(petStore);
			pad.getCustomers().clear();
			pad.getEmployees().clear();
			
			result.add(pad);
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}
	
	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
	}


