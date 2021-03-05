package com.desiby.springpayroll.controller;

import com.desiby.springpayroll.model.Employee;
import com.desiby.springpayroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
public class EmployeeController extends WebMvcConfigurerAdapter{

    //setup validation in controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/employee").setViewName("employee");
    }

    //injecting EmployeeService
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("employee/new")
    public String newEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "employeeform";
    }


    @PostMapping("employee")
    public String saveEmployee(@Valid Employee employee, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "employeeform";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @RequestMapping("employee/{id}")
    public String showEmployee(@PathVariable Long id, Model model){
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employeeshow";
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("employees", employeeService.findAllEmployees());
        return "employees";
    }


    @RequestMapping("employee/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employeeform";
    }

    @RequestMapping("employee/delete/{id}")
    public String delete(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

}
