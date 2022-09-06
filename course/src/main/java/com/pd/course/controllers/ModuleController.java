package com.pd.course.controllers;

import com.pd.course.dtos.CourseDto;
import com.pd.course.dtos.ModuleDto;
import com.pd.course.models.CourseModel;
import com.pd.course.models.ModuleModel;
import com.pd.course.services.ModuleService;
import com.pd.course.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModulesByCourse(@PathVariable("courseId") UUID courseId,
                                                                   SpecificationTemplate.ModuleSpec spec,
                                                                   @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleModel> getOneModuleByCourse(@PathVariable("courseId") UUID courseId,
                                                                  @PathVariable("moduleId") UUID moduleId) {
        return ResponseEntity.ok(moduleService.findOneByCourse(courseId, moduleId));
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ModuleModel> saveModule(@PathVariable("courseId") UUID courseId,
                                                  @RequestBody @Valid ModuleDto moduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(courseId, moduleDto));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable("courseId") UUID courseId,
                                               @PathVariable("moduleId") UUID moduleId) {
        moduleService.delete(courseId, moduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Module deleted successfully.");
    }

    @PutMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> updateModule(@PathVariable("courseId") UUID courseId,
                                               @RequestBody @Valid ModuleDto moduleDto) {
        return ResponseEntity.ok(moduleService.update(courseId, moduleDto));
    }
}
