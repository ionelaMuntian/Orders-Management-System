package Model;

import java.time.LocalDateTime;

public record Bill(
        int clientId,
        int productId,
        int quantity,
        LocalDateTime dateTime
) {}
