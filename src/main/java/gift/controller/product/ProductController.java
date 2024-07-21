package gift.controller.product;

import gift.common.dto.PageResponse;
import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URL;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final OptionService optionService;

    ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    @PostMapping("")
    public ResponseEntity<Void> registerProduct(@Valid @RequestBody ProductRequest.Create request) {
        Long id = productService.addProduct(request);
        return ResponseEntity.created(URI.create("/api/v1/products/" + id)).build();
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ProductResponse> response = productService.findAllProduct(pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productService.findProduct(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,
                                                         @Valid @RequestBody ProductRequest.Update request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

