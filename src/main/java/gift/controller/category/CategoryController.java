package gift.controller.category;

import gift.common.dto.PageResponse;
import gift.controller.category.dto.CategoryRequest;
import gift.controller.category.dto.CategoryResponse;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<Void> registerCategory(@Valid @RequestBody CategoryRequest.Create request) {
        Long id = categoryService.register(request);
        return ResponseEntity.created(URI.create("/api/v1/category/" + id)).build();
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategory(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<CategoryResponse> response = categoryService.findAllCategory(pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Long id) {
        CategoryResponse response = categoryService.findCategory(id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
        @Valid @RequestBody CategoryRequest.Update request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
