package com.example.luyen_tap_1.controller;

import com.example.luyen_tap_1.entity.People;
import com.example.luyen_tap_1.repository.PeopleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping("/view")
    public String viewPeople(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(name = "searchUserName", required = false) String searchUserName) {
        Page<People> pagePeople;
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, 100);
        if (searchUserName == null || searchUserName.isBlank()) {
            pagePeople = peopleRepository.findAll(pageable);
        } else {
            pagePeople = peopleRepository.findPeopleByUsernameContains(searchUserName, pageable);
        }
        model.addAttribute("listPeople", pagePeople);
        return "view/index";
    }
//    @GetMapping("/view")
//    public String viewPeople(Model model, @RequestParam(defaultValue = "1") int page) throws IOException {
//        File file = ResourceUtils.getFile("classpath:static/people_0.json");
//        ObjectMapper mapper = new ObjectMapper();
//        List<People> listPeople = mapper.readValue(file, new TypeReference<List<People>>() {
//        });
//        if (page < 1) page = 1;
//        Pageable pageable = PageRequest.of(page - 1, 100);
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min((startIndex + pageable.getPageSize()), listPeople.size());
//        System.out.println(startIndex);
//        System.out.println(endIndex);
//        List<People> sublistPeople = listPeople.subList(startIndex, endIndex);
//        Page<People> peoplePage = new PageImpl<>(sublistPeople, pageable, listPeople.size());
//        model.addAttribute("listPeople", peoplePage);
//        return "view/index";
//    }

    @GetMapping("/readfile")
    public String readFile() throws IOException {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            files.add(ResourceUtils.getFile("classpath:static/people_" + i + ".json"));
        }
        ObjectMapper mapper = new ObjectMapper();
        List<People> listPeople = new ArrayList<>();
        for (File file : files) {
            List<People> peopleFromFile = mapper.readValue(file, new TypeReference<List<People>>() {
            });
            listPeople.addAll(peopleFromFile);
        }
        String json = mapper.writeValueAsString(listPeople);
        File outputFile = new File("E:/LAP_TRINH_JAVA_TUAN_PH20022/LAP_TRINH_JAVA_6/LUYEN_TAP/LUYEN_TAP_1/src/main/resources/static/people_tong.json");
        if (outputFile.exists()) {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write("");
            fileWriter.close();
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, listPeople);
        mapper.writeValue(new File("E:/LAP_TRINH_JAVA_TUAN_PH20022/LAP_TRINH_JAVA_6/LUYEN_TAP/LUYEN_TAP_1/src/main/resources/static/people_tong.json"), listPeople);
        return "redirect:/people/view";
    }

    @GetMapping("/save_db")
    public String saveDB() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:static/people_tong.json");
        List<People> listPeople = mapper.readValue(file, new TypeReference<List<People>>() {
        });
        System.out.println(listPeople.size());
        listPeople.stream().forEach(peopleRepository :: save);
        return "redirect:/people/view";
    }
}
