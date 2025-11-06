package com.fahad.restapitestapp.Config;


import com.fahad.restapitestapp.Entity.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmployeeAPIService {
    @GET("api/employees")
    Call<List<Employee>> getAllEmployees();

    @POST("api/employees")
    Call<Employee> addEmployee(@Body Employee employee);

    @DELETE("api/employees/{id}")
    Call<Void> deleteEmployee(@Path("id") Long id);

}
