package pl.polkomtel.azuredemo.offer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offer")
public class OfferController {

    private OfferService offerService;


    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    public Offer getById(@PathVariable("id") Long id) {
        return offerService.getById(id);
    }

    @PostMapping
    public Offer saveOffer(@RequestBody Offer offer) {
        if (offer.getId() == null) {
            return offerService.create(offer);
        } else {
            throw new IllegalArgumentException("Cannot POST with id, try PUT to update");
        }
    }

    @PutMapping
    public Offer updateOffer(@RequestBody Offer offer) {
        return offerService.update(offer);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable("id") Long id) {
        offerService.delete(id);
    }

}
