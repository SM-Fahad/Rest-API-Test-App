package com.fahad.restapitestapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.fahad.restapitestapp.Config.APIClient;
import com.fahad.restapitestapp.Config.EmployeeAPIService;
import com.fahad.restapitestapp.Entity.Employee;

import java.util.List;

import retrofit2.*;

public class MainActivity extends AppCompatActivity {

    Button btnLoad;
    Button btnAdd;
    TextView tvResult;
    Button btnDelete;
    EditText etDeleteId;
    private EmployeeAPIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        apiService = APIClient.getClient().create(EmployeeAPIService.class);
        btnLoad = findViewById(R.id.btnLoad);
        btnAdd = findViewById(R.id.btnAdd);
        tvResult = findViewById(R.id.tvResult);
        etDeleteId = findViewById(R.id.etDeleteId);
        btnDelete = findViewById(R.id.btnDelete);
        btnLoad.setOnClickListener(v -> loadProducts());
        btnAdd.setOnClickListener(v -> addEmployee());

        btnDelete.setOnClickListener(v -> {
            String idStr = etDeleteId.getText().toString();
            if (!idStr.isEmpty()) {
                deleteEmployee(Long.parseLong(idStr));
            } else {
                Toast.makeText(this, "Please enter an ID ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void loadProducts() {
//        apiService.getAllEmployees().enqueue(new Callback<List<Employee>>() {
//            @Override
//            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    for (Employee employee : response.body()) {
//                        Log.d("Employee", "Name: " + employee.getName() + ", Salary: " + employee.getSalary());
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Failed to fetch products ❌", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Employee>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void loadProducts() {
        apiService.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder builder = new StringBuilder();
                    for (Employee employee : response.body()) {

                        builder.append("ID: ").append(employee.getId())
                                .append("\nName: ").append(employee.getName())
                                .append("\nDesignation: ").append(employee.getDesignation())
                                .append("\nSalary: ").append(employee.getSalary())
                                .append("\nEmail: ").append(employee.getEmail())
                                .append("\n------------------------------------\n");
                    }
                    tvResult.setText(builder.toString());
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch employees ❌", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEmployee() {
        Employee employee = new Employee();
        employee.setName("Hafiz");
        employee.setDesignation("Developer");
        employee.setSalary(120000.0);
        employee.setEmail("hafiz@gmail.com");
        employee.setAddress("Dhaak");


        apiService.addEmployee(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Employee Added ✅", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to Add Employee ❌", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEmployee(Long id) {
        apiService.deleteEmployee(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Employee Deleted ✅", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to Delete Employee ❌", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}