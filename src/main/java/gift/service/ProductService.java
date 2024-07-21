package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.CategoryNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.controller.product.dto.ProductRequest;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.controller.product.dto.ProductResponse;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, WishRepository wishRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Long addProduct(ProductRequest.Create request) {
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
            CategoryNotFoundException::new);
        Option option = new Option(request.optionName(), request.quantity());
        Product product = request.toEntity(category);

        product.addOption(option);
        productRepository.save(product);

        return product.getId();
    }

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return ProductResponse.from(product);
    }

    public PageResponse<ProductResponse> findAllProduct(Pageable pageable) {
        Page<Product> productList = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = productList.getContent().stream()
            .map(ProductResponse::from)
            .toList();

        return PageResponse.from(productResponses, productList);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest.Update request) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        product.updateProduct(request.name(), request.price(), request.imageUrl());
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        wishRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }
}
