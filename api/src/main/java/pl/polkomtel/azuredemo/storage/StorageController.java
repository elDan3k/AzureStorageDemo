package pl.polkomtel.azuredemo.storage;

import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
public class StorageController {

    private StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String getFile(@RequestParam("file") MultipartFile file, @RequestParam("type") ContainerType type) {
        return storageService.uploadFileToCloud(file, type);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable("id") String str) {
        return storageService.getFile(str, ContainerType.IMAGE);
    }

    @PostMapping(value = "/config")
    public void configure(@RequestParam("conStr") String connectionString) {
        storageService.startConfig(connectionString);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(ContainerType.class, new ContainerTypeConverter());
    }
}
