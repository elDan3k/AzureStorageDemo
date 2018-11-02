package pl.polkomtel.azuredemo.offer;

public class OfferNotFoundException extends RuntimeException {

    OfferNotFoundException(Long id) {
        super("OFFER NOT FOUND: " + id);
    }
}
