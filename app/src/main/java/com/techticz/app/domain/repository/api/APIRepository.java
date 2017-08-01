package com.techticz.app.domain.repository.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.techticz.app.constant.AppAPIMethods;
import com.techticz.app.constant.AppConstants;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.network.AppRestClient;
import com.techticz.app.network.Request;
import com.techticz.app.network.RequestMethod;
import com.techticz.app.network.ResponseContainer;
import com.techticz.app.network.ServiceRequest;
import com.techticz.app.utility.CommonUtils;
import com.techticz.dietchart.backend.blobApi.BlobApi;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadRequest;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.foodEntityApi.FoodEntityApi;
import com.techticz.dietchart.backend.foodEntityApi.model.CollectionResponseFoodEntity;
import com.techticz.dietchart.backend.foodEntityApi.model.FoodEntity;
import com.techticz.dietchart.backend.myApi.MyApi;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



/**
 * Created by gssirohi on 5/7/16.
 */
public class APIRepository implements IAppRepository {
    private final APIResponseMapper responseMapper;
    private final FoodEntityApi foodAPI;
    private final BlobApi blobApi;
    private AppRestClient restClient;
    private MyApi myApiService;

    public APIRepository() {
        responseMapper = new APIResponseMapper();
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("http://diet-chart-app.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        // end options for devappserver

        myApiService = builder.build();

        FoodEntityApi.Builder foodBuilder = new FoodEntityApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("https://diet-chart-app.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        foodAPI = foodBuilder.build();

        BlobApi.Builder blobApiBuilder = new BlobApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("https://diet-chart-app.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        blobApi = blobApiBuilder.build();
        //TODO: modify constructor API repository constructor
    }


    private ResponseContainer executeRestClient(ServiceRequest serviceRequest) {
        ResponseContainer container = null;
        try {
            restClient = new AppRestClient(serviceRequest);
            container = restClient.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return container;
        }
    }


    private ServiceRequest buildProductServiceRequest(Context context, String id, Products type) {
        Request request = buildRequest();
        request.getRequestParams().put("id", id);
        ServiceRequest serviceRequest = new ServiceRequest(request, context);
        serviceRequest.setConnectionTimeout(AppConstants.MAX_CONNECTION_TIMEOUT);
        serviceRequest.setMethod(AppAPIMethods.getProductRequestMethod(type));
        serviceRequest.setSecureConnectionRequest(false);
        serviceRequest.setPath("");
        serviceRequest.setResponsibleClass(AppAPIMethods.getProductClass(type));
        serviceRequest.setRequestCode(null);
        return serviceRequest;
    }

    public ServiceRequest buildProductListServiceRequest(Context context, int offset, int limit, Products productType) {
        Request request = buildRequest();
        request.getRequestParams().put("offset", "" + offset);
        request.getRequestParams().put("limit", "" + limit);
        ServiceRequest serviceRequest = new ServiceRequest(request, context);
        serviceRequest.setConnectionTimeout(AppConstants.MAX_CONNECTION_TIMEOUT);
        serviceRequest.setMethod(AppAPIMethods.getProductRequestMethod(productType));
        serviceRequest.setSecureConnectionRequest(false);
        serviceRequest.setPath("");
        serviceRequest.setResponsibleClass(AppAPIMethods.getProductClass(productType));
        serviceRequest.setRequestCode(null);
        return serviceRequest;
    }

    public Request buildRequest() {
        String authValue = AppConstants.MARVEL_PUBLIC_KEY;
        String md5hash = AppConstants.MARVEL_MD5_HASH;
        String timeStamp = AppConstants.MARVEL_TS;
        Request request = new Request();
        request.setRequestMethod(RequestMethod.GET);
        HashMap<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("apikey", authValue);
        requestParams.put("hash", md5hash);
        requestParams.put("ts", timeStamp);
        request.setRequestParams(requestParams);

        return request;
    }

    @Override
    public List<Meal> getMealList(BaseInteractor interactor, int dayIndex, String searchKey, int[] mealIds) {
        return null;
    }

    @Override
    public Meal getMealById(BaseInteractor interactor, Long id) {
        return null;
    }

    @Override
    public Meal getMealbyName(BaseInteractor interactor, String name) {
        return null;
    }

    @Override
    public List<MealRoutine> getMealRoutinesByDay(BaseInteractor interactor, int day) {
        return null;
    }

    @Override
    public int createMeal(BaseInteractor interactor, Meal meal) {
        return 0;
    }

    @Override
    public int addMealToRoutineWeek(BaseInteractor interactor, Integer id, int routineId, int day) {
        return 0;
    }

    @Override
    public long createMealPlan(BaseInteractor interactor, MealPlan plan) {
        return 0;
    }

    @Override
    public int updateMealPlan(BaseInteractor interactor, MealPlan plan) {
        return 0;
    }

    @Override
    public MealPlan getMealPlan(BaseInteractor interactor, Long id) {
        return null;
    }

    @Override
    public List<Meal> getDayMealList(BaseInteractor interactor, int day, DayMeals dayMeals) {
        return null;
    }

    @Override
    public List<Food> getFoodList(BaseInteractor interactor, String searchKey, Long[] foodIds) {
        List<Food> foods = new ArrayList<>();
        try {
            CollectionResponseFoodEntity entities;
            if(foodIds != null && foodIds.length>0) {
                ArrayList<Long> l = new ArrayList<Long>();
                Collections.addAll(l, foodIds);
                entities = foodAPI.foodListForIds(l).execute();
            } else {
                entities = foodAPI.foodListWhereNameContains(searchKey).execute();
            }
            List<FoodEntity> list = entities.getItems();
            foods = responseMapper.getFoodsFromEntity(list);
            return foods;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return foods;
    }

    @Override
    public Long createFood(BaseInteractor interactor, Food food) {
        try {
            FoodEntity r = foodAPI.insert(responseMapper.getEntityFromFood(food)).execute();
            return r.getUid();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    @Override
    public List<MealPlan> getMealPlans(BaseInteractor interactor, String searchKey, boolean isMyPlan) {
        return null;
    }

    @Override
    public SystemHealth checkSystemHealth(BaseInteractor interactor) {
        try {

            SystemHealth health = myApiService.checkSystemHealth().execute();
            return health;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBlobUploadUrl(BaseInteractor interactor) {
        //return blobApi.blobUploadUrl().execute();
        return null;
    }

    @Override
    public ImageUploadResponse uploadImage(BaseInteractor interactor, Bitmap bitmap, String imageName) {
/*// Important! you wanna rescale your bitmap (e.g. with Bitmap.createScaledBitmap)
        // as with full-size pictures the base64 representation would not fit in memory

        // encode bitmap into byte array (very resource-wasteful!)
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        bitmap = null;
        stream = null;

        // Note: We encode ourselves, instead of using image.encodeImageData, as this would throw
        //       an 'Illegal character '_' in base64 content' exception
        // See: http://stackoverflow.com/questions/22029170/upload-photos-from-android-app-to-google-cloud-storage-app-engine-illegal-char
        String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        byteArray = null;

        // Upload via AppEngine Endpoint (ImageUploadRequest is a generated model)
        ImageUploadRequest image = new ImageUploadRequest();
        image.setImageData(base64);
        image.setImageName(imageName);
        image.setMimeType("image/jpeg");
        ImageUploadResponse response = null;
        try {
            response = blobApi.uploadImage(image).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;*/

        // Upload via AppEngine Endpoint (ImageUploadRequest is a generated model)
        ImageUploadRequest image = new ImageUploadRequest();
        image.setImageData(CommonUtils.getByteArrayFromBitmap(bitmap,true).toString());
        image.setImageName(imageName);
        image.setMimeType("image/png");
        ImageUploadResponse response = null;
        try {
            response = blobApi.uploadImage(image).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    @Override
    public long updateFood(CreateFoodInteractor createFoodInteractor, Food food) {
        try {
            FoodEntity r = foodAPI.update(food.getUid(),responseMapper.getEntityFromFood(food)).execute();
            return r.getUid();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
