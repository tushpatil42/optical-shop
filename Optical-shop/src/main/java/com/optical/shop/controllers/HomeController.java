package com.optical.shop.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.optical.shop.dao.ProductRepository;
import com.optical.shop.entities.Customer;
import com.optical.shop.entities.Product;
import com.optical.shop.service.CustomerService;

@Controller
public class HomeController {

	@Autowired
	ProductRepository proRepo;

	@Autowired
	CustomerService customerService;

	@GetMapping("/")
	public String displayHome(Model model) {

		List<Product> products = proRepo.findAll();
		model.addAttribute("products", products);

		Customer customer = new Customer();
		model.addAttribute("customer", customer);


		return "main/home";

	}

	@PostMapping("/save-customer")
	public String saveCustomer(@ModelAttribute Customer customer) {
		customerService.saveCustomer(customer);
		return "main/home";
	}
	
    @GetMapping("/data")
    public String index(Model model) {
        List<Customer> dataList = customerService.findAll();
        model.addAttribute("dataList", dataList);
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
        return "/data/data";
    }
	
    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        List<Customer> dataList = customerService.findAll();

        // Create Excel Workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Customer ID");
        headerRow.createCell(1).setCellValue("Customer Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Mobile Number");
        headerRow.createCell(4).setCellValue("Message From Customer");

        // Populate Rows
        int rowNum = 1;
        for (Customer data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getCutomerId());
            row.createCell(1).setCellValue(data.getCustomerName());
            row.createCell(2).setCellValue(data.getEmail());
            row.createCell(3).setCellValue(data.getMobileNum());
            row.createCell(4).setCellValue(data.getCustomerMessage());
        }

        // Write to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

}	
