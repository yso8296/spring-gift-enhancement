package gift.model.option;

import gift.model.product.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CreateOptionRequest(

    @NotBlank
    @Length(min = 1, max = 50, message = "옵션 이름은 공백 포함 최대 50자까지 입력할 수 있습니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$",
        message = "옵션 이름에 허용되지 않은 특수 문자가 포함되어 있습니다. 허용되는 특수 문자: ( ), [ ], +, -, &, /, _"
    )
    String name,
    @Min(1)
    @Max(999_999_99)
    int quantity,
    Long productId
) {

    public Option toEntity(Product product) {
        return new Option(null, name, quantity, product);
    }
}