package com.techticz.dietchart.backend.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.repackaged.com.google.gson.Gson;

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
public class BlobDownloadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobService.serve(blobKey, res);
    }
}
