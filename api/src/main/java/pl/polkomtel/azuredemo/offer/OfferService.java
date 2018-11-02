package pl.polkomtel.azuredemo.offer;

import org.springframework.stereotype.Service;
import pl.polkomtel.azuredemo.storage.ContainerType;
import pl.polkomtel.azuredemo.storage.StorageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private OfferRepository offerRepository;
    private StorageService storageService;

    public OfferService(OfferRepository offerRepository, StorageService storageService) {
        this.storageService = storageService;
        this.offerRepository = offerRepository;
    }

    List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    Offer getById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
    }

    Offer create(Offer offer) {
        return offerRepository.save(offer);
    }

    Offer update(Offer offer) {
        Offer old = offerRepository.findById(offer.getId()).orElseThrow(() -> new OfferNotFoundException(offer.getId()));
        offer.setCreatedDate(old.getCreatedDate());
        offer.setAuthor(old.getAuthor());
        storageService.removeObsoleteFiles(findObsoleteFiles(old, offer));
        return offerRepository.save(offer);
    }


    boolean exists(Long id) {
        return offerRepository.existsById(id);
    }

    public Map<ContainerType, Set<String>> findObsoleteFiles(Offer oldOffer, Offer newOffer) {
        Set<Image> obsoleteImages = new HashSet<>(oldOffer.getImages());
        Set<Attachment> obsoleteAttachments = new HashSet<>(oldOffer.getAttachments());
        obsoleteImages.removeAll(newOffer.getImages());
        obsoleteAttachments.removeAll(newOffer.getAttachments());
        Map<ContainerType, Set<String>> result = new HashMap<>();
        result.put(ContainerType.IMAGE, obsoleteImages.stream().map(i -> i.getUrl().substring(i.getUrl().lastIndexOf("/"))).collect(Collectors.toSet()));
        result.put(ContainerType.ATTACHMENT, obsoleteAttachments.stream().map(a -> a.getUrl().substring(a.getUrl().lastIndexOf("/"))).collect(Collectors.toSet()));
        return result;
    }

    public void delete(Long id) {
        offerRepository.deleteById(id);
    }

}
