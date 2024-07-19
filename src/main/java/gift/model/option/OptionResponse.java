package gift.model.option;

public record OptionResponse(Long id, String name, int quantity, Long productId) {

    public static OptionResponse from(Option option) {
        return new OptionResponse(option.getId(), option.getName(), option.getQuantity(),
            option.getProduct().getId());
    }
}
