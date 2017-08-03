package com.techticz.dietchart.backend;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.cloud.sql.jdbc.internal.Exceptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gssirohi on 17/7/17.
 */
public class BlobUploadServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("file");
        if(blobKeys == null || blobKeys.isEmpty()) throw new IllegalArgumentException("No blobs given");

        BlobKey blobKey = blobKeys.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);


            String servingUrl = imagesService.getServingUrl(servingOptions);

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");

            // send simple json response (ImageUploadResponse is a POJO)
            ImageUploadResponse result = new ImageUploadResponse();
            result.setBlobKey(blobKey.getKeyString());
            result.setServingUrl(servingUrl);

            PrintWriter out = res.getWriter();
            out.print(new Gson().toJson(result));
            out.flush();
            out.close();

    }
}
