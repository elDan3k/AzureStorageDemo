package pl.polkomtel.azuredemo.storage;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.polkomtel.azuredemo.storage.exceptions.StorageUploadException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/*
 * W przykładzie wykorzystujemy 'stare' API do obsługi BLOBow, aktualnie dostępna wersja całkowicie zmienia całkowicie
 * poniższy kod począwszy od sposobu logowania, przez budowanie kontenerów, po silne wykorzystanie programowania reaktywnego do obsługi API
 * przykład: https://github.com/Azure-Samples/storage-blobs-java-v10-quickstart
 * */

@Component
@Slf4j
public class StorageService {

    public String storageConnectionString;
    private Map<Integer, CloudBlobContainer> containers;

    public void startConfig(String connectionString) {
        System.out.println(connectionString);
        this.storageConnectionString = connectionString;
        System.out.println(this.storageConnectionString);
        configureBlobHandler();
    }

    private void configureBlobHandler() {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            //TODO wszystko do jakiegoś zewnętrznego serwisu do tworzenia/zarządzania kontenerem
            containers = new HashMap<>();
            containers.put(0, serviceClient.getContainerReference("images"));
            containers.put(1, serviceClient.getContainerReference("thumbs"));
            containers.put(2, serviceClient.getContainerReference("attachments"));

            for (Map.Entry<Integer, CloudBlobContainer> entry : containers.entrySet()) {
                entry.getValue().createIfNotExists();
                if (entry.getKey() == 2) {
                    containerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);
                } else {
                    containerPermissions.setPublicAccess(BlobContainerPublicAccessType.BLOB);
                }
                entry.getValue().uploadPermissions(containerPermissions);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String uploadFileToCloud(MultipartFile sourceFile, ContainerType containerType) {
        return uploadFileToCloud(sourceFile, containerType, null);
    }

    public String uploadFileToCloud(MultipartFile sourceFile, ContainerType containerType, HashMap<String, String> metadata) {
        try {
            CloudBlockBlob blob = containers.get(containerType.ordinal()).getBlockBlobReference(RandomStringUtils.randomAlphanumeric(16) + ".jpg");
            if (metadata != null && !metadata.isEmpty()) {
                blob.setMetadata(metadata);
            }
            blob.getProperties().setContentType(sourceFile.getContentType());
            blob.upload(sourceFile.getInputStream(), sourceFile.getSize());
            return blob.getName(); //getUri().toString();
        } catch (StorageException | URISyntaxException | IOException | RuntimeException e) {
            throw new StorageUploadException(sourceFile.getOriginalFilename(), e.getMessage());
        }
    }

    public List<String> getAllLinks(ContainerType containerType) {
        List<String> blobList = new ArrayList<>();
        for (ListBlobItem b : containers.get(containerType.ordinal()).listBlobs()) {
            blobList.add(b.getUri().toString());
        }
        return blobList;
    }

    public void removeObsoleteFiles(Map<ContainerType, Set<String>> obsoleteFiles) {
        for (ContainerType ct : obsoleteFiles.keySet()) {
            for (String s : obsoleteFiles.get(ct)) {
                delete(s, ct);
            }
        }
    }

    public void delete(String path, ContainerType containerType) {
        CloudBlockBlob blob;
        try {
            blob = containers.get(containerType.ordinal()).getBlockBlobReference(path);
            blob.deleteIfExists();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // podawac do wyszukiwania pliki z rozszerzeniem (fileNameOld)
    public void renameFile(String fileNameOld, String fileNameNew, ContainerType containerType) {
        try {
            CloudBlockBlob blobOld = containers.get(containerType.ordinal()).getBlockBlobReference(fileNameOld);
            String extension = blobOld.getName().substring(blobOld.getName().lastIndexOf("."));
            CloudBlockBlob blobNew = containers.get(containerType.ordinal()).getBlockBlobReference(fileNameNew + extension);
            blobNew.startCopy(blobOld);
            blobOld.deleteIfExists();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public byte[] getFile(String fileName, ContainerType containerType) {
        try {
            CloudBlockBlob blob = containers.get(containerType.ordinal()).getBlockBlobReference(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.download(outputStream);
            return outputStream.toByteArray();
        } catch (StorageException | URISyntaxException e) {
            throw new StorageUploadException(fileName, e.getMessage());
        }
    }

}
