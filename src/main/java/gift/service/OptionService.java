package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.CategoryNotFoundException;
import gift.common.exception.OptionNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.model.category.Category;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.model.option.CreateOptionRequest;
import gift.model.option.CreateOptionResponse;
import gift.model.option.Option;
import gift.model.option.OptionResponse;
import gift.model.option.UpdateOptionRequest;
import gift.model.product.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateOptionResponse register(CreateOptionRequest request) {
        Product product = productRepository.findById(request.productId())
            .orElseThrow(ProductNotFoundException::new);
        Option option = optionRepository.save(request.toEntity(product));
        return CreateOptionResponse.from(option);
    }

    public PageResponse<OptionResponse> findAllOption(Pageable pageable) {
        Page<Option> optionList = optionRepository.findAll(pageable);
        List<OptionResponse> responses = optionList.getContent().stream()
            .map(OptionResponse::from).toList();
        return PageResponse.from(responses, optionList);
    }

    public OptionResponse findOption(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(OptionNotFoundException::new);
        return OptionResponse.from(option);
    }

    @Transactional
    public OptionResponse updateOption(Long id, UpdateOptionRequest request) {
        Option option = optionRepository.findById(id).orElseThrow(OptionNotFoundException::new);
        Product product = option.getProduct();
        product.checkDuplicateName(request.name());
        option.updateOption(request.name(), request.quantity());
        return OptionResponse.from(option);
    }

    @Transactional
    public void deleteOption(Long optionId) {
        if (!productRepository.existsByOptionId(optionId)) {
            optionRepository.deleteById(optionId);
            return;
        }

        Product product = productRepository.findByOptionId(optionId);
        if (product.hasOneOption()) {
            throw new IllegalArgumentException("삭제할 수 없는 옵션입니다.");
        }

        optionRepository.deleteById(optionId);
    }

    public List<OptionResponse> getAllProductOptions(Long productId) {
        List<Option> optionList = optionRepository.findAllByProductId(productId);
        List<OptionResponse> responses = optionList.stream().map(OptionResponse::from).toList();
        return responses;
    }
}