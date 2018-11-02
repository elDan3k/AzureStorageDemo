package pl.polkomtel.azuredemo.storage.exceptions;

public class StorageUploadException extends RuntimeException{

    public StorageUploadException(String fileName, String message) {
        super(String.format("Failed to upload file %s. Error message: %s", fileName, message));
    }
}
