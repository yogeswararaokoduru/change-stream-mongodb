package com.org.yogi.rempository;

import com.org.yogi.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee,String> {

     Employee findByEmpAddress(String empId);
}
