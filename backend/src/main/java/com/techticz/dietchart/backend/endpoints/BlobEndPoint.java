package com.techticz.dietchart.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.techticz.dietchart.backend.model.ImageDownloadRequest;
import com.techticz.dietchart.backend.model.ImageDownloadResponse;
import com.techticz.dietchart.backend.model.ImageUploadRequest;
import com.techticz.dietchart.backend.model.ImageUploadResponse;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "blobApi",
        version = "v1",
        resource = "imageUploadResponse",
        namespace = @ApiNamespace(
                ownerDomain = "backend.dietchart.techticz.com",
                ownerName = "backend.dietchart.techticz.com",
                packagePath = ""
        )
)
public class BlobEndPoint {

    private static final Logger logger = Logger.getLogger(BlobEndPoint.class.getName());

    @ApiMethod(name = "uploadImage",
            path = "uploadImage",
            httpMethod = ApiMethod.HttpMethod.POST)
    public ImageUploadResponse uploadImageToBlobStore(ImageUploadRequest imageRequest) throws IOException {
        // TODO: Implement this function

        logger.info("Calling getImageUploadRequest method");

        // create blob url
        BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
        String uploadUrl = blobService.createUploadUrl("/blob/upload");

        // create multipart body containing file
        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("file", imageRequest.getImageData().getBytes(), ContentType.create(imageRequest.getMimeType()), imageRequest.getImageName())
                .build();

        // Post request to BlobstorageService
        // Note: We cannot use Apache HttpClient, since AppEngine only supports Url-Fetch
        //  See: https://cloud.google.com/appengine/docs/java/sockets/
        URL url = new URL(uploadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-length", requestEntity.getContentLength() + "");
        connection.addRequestProperty(requestEntity.getContentType().getName(), requestEntity.getContentType().getValue());
        requestEntity.writeTo(connection.getOutputStream());

        // BlobstorageService will forward to /blob/upload, which returns our json
        String responseBody = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

        if(connection.getResponseCode() < 200 || connection.getResponseCode() >= 400) {
            throw new IOException("HTTP Status " + connection.getResponseCode() + ": " + connection.getHeaderFields() + "\n" + responseBody);
        }

        // parse BlopUploadServlet's Json response
        ImageUploadResponse response = new Gson().fromJson(responseBody, ImageUploadResponse.class);

        return response;
    }

    @ApiMethod(name = "downloadImage",
            path = "downloadImage",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ImageDownloadResponse downloadImageFromBlobStore(ImageDownloadRequest imageRequest) throws IOException {
        String uploadUrl = "serve?blob-key="+imageRequest.getBlobKey();
        URL url = new URL(uploadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        String responseBody = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        ImageDownloadResponse res = new ImageDownloadResponse();
        res.setBlobKey(imageRequest.getBlobKey());
        res.setData(responseBody);
        return res;
    }

}