package com.techticz.app.domain.repository.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.techticz.app.constant.AppAPIMethods;
import com.techticz.app.constant.AppConstants;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.interactor.LoginInteractor;
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
import com.techticz.dietchart.backend.appUserApi.AppUserApi;
import com.techticz.dietchart.backend.appUserApi.model.AppUser;
import com.techticz.dietchart.backend.appUserApi.model.UserLoginResponse;
import com.techticz.dietchart.backend.blobApi.BlobApi;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.foodEntityApi.FoodEntityApi;
import com.techticz.dietchart.backend.foodEntityApi.model.CollectionResponseFoodEntity;
import com.techticz.dietchart.backend.foodEntityApi.model.FoodEntity;
import com.techticz.dietchart.backend.mealEntityApi.MealEntityApi;
import com.techticz.dietchart.backend.mealEntityApi.model.CollectionResponseMealEntity;
import com.techticz.dietchart.backend.mealEntityApi.model.MealEntity;

import com.techticz.dietchart.backend.mealPlanEntityApi.MealPlanEntityApi;
import com.techticz.dietchart.backend.mealPlanEntityApi.model.CollectionResponseMealPlanEntityItem;
import com.techticz.dietchart.backend.mealPlanEntityApi.model.MealPlanEntityItem;
import com.techticz.dietchart.backend.myApi.MyApi;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gssirohi on 5/7/16.
 */
public class APIRepository implements IAppRepository {
    private final APIResponseMapper responseMapper;
    private final FoodEntityApi foodAPI;
    private final BlobApi blobApi;
    private final MealEntityApi mealAPI;
    private final MealPlanEntityApi mealPlanAPI;
    private final AppUserApi appUserApi;
    private AppRestClient restClient;
    private MyApi myApiService;

    public APIRepository() {
        responseMapper = new APIResponseMapper();

        AppUserApi.Builder appUserBuilder = new AppUserApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        // end options for devappserver

        appUserApi = appUserBuilder.build();

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        // end options for devappserver

        myApiService = builder.build();

        MealPlanEntityApi.Builder mealPlanBuilder = new MealPlanEntityApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        mealPlanAPI = mealPlanBuilder.build();
        MealEntityApi.Builder mealBuilder = new MealEntityApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        mealAPI = mealBuilder.build();
        FoodEntityApi.Builder foodBuilder = new FoodEntityApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
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
                .setRootUrl(AppConstants.SERVER_ROOT_URL)
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
            throw new AppException(AppErrors.REPOSITORY);
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
    public List<Meal> getMealList(BaseInteractor interactor, int dayIndex, String searchKey, long[] mealIds) {
        List<Meal> meals = new ArrayList<>();
        try {
            CollectionResponseMealEntity entities;
            if(mealIds != null && mealIds.length>0) {
                List<Long> l;
                l = CommonUtils.getLongList(mealIds);
                entities = mealAPI.mealListForIds(l).execute();
            } else {
                entities = mealAPI.mealListWhereNameContains(searchKey).execute();
            }
            List<MealEntity> list = entities.getItems();
            meals = responseMapper.getMealsFromEntity(list);
            return meals;

        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public Meal getMealById(BaseInteractor interactor, Long id) {
        Meal meal = null;
        try {
            MealEntity e = mealAPI.get(id).execute();
            List<MealEntity> list = new ArrayList<>();
            list.add(e);
            List<Meal> l = responseMapper.getMealsFromEntity(list);
            meal  = l.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
        return meal;
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
    public long createMeal(BaseInteractor interactor, Meal meal) {
        try {
            MealEntity r = mealAPI.insert(responseMapper.getEntityFromMeal(meal)).execute();
            return r.getUid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public int addMealToRoutineWeek(BaseInteractor interactor, Integer id, int routineId, int day) {
        return 0;
    }

    @Override
    public long createMealPlan(BaseInteractor interactor, MealPlan plan,Boolean autoLoad,List<Integer> prefRoutines) {
        try {
            MealPlanEntityItem e = mealPlanAPI.insert(autoLoad,prefRoutines,responseMapper.getEntitiyFromMealPlan(plan)).execute();
            return e.getUid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public MealPlan updateMealPlan(BaseInteractor interactor, MealPlan plan, boolean autoLoad, List<Integer> prefRoutines) {
        try {
            if(prefRoutines == null){
                prefRoutines = new ArrayList<>();
                prefRoutines.add(1);
            }
            MealPlanEntityItem e = responseMapper.getEntitiyFromMealPlan(plan);
            MealPlanEntityItem p = mealPlanAPI.update(plan.getUid(),autoLoad,prefRoutines, e).execute();
            return responseMapper.getMealPlanFromEntity(p);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public MealPlan getMealPlan(BaseInteractor interactor, Long id) {
        try {
            MealPlanEntityItem e = mealPlanAPI.get(id).execute();
            return responseMapper.getMealPlanFromEntity(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public List<Meal> getDayMealList(BaseInteractor interactor, int day, DayMeals dayMeals) {
        List<Meal> list = new ArrayList<>();
        long[] ids = dayMeals.getAllIds();
        for(long id: ids){
            Meal meal = null;
            if(id != 0)
            meal = getMealById(interactor,id);
            else
                meal = null;
            list.add(meal);
        }
        return list;
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
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public Long createFood(BaseInteractor interactor, Food food) {
        try {
            FoodEntity r = foodAPI.insert(responseMapper.getEntityFromFood(food)).execute();
            return r.getUid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public List<MealPlan> getMealPlans(BaseInteractor interactor, String searchKey, boolean isMyPlan) {
        try {
            CollectionResponseMealPlanEntityItem resp = mealPlanAPI.list().execute();
            List<MealPlanEntityItem> list = resp.getItems();
            List<MealPlan> plans = new ArrayList<>();
            if(list != null) {
                for(MealPlanEntityItem e: list){
                    plans.add(responseMapper.getMealPlanFromEntity(e));
                }
            }
            return plans;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public SystemHealth checkSystemHealth(BaseInteractor interactor) {
        try {

            SystemHealth health = myApiService.checkSystemHealth().execute();
            return health;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public String getBlobUploadUrl(BaseInteractor interactor) {
        //return blobApi.blobUploadUrl().execute();
        return null;
    }

    @Override
    public ImageUploadResponse uploadImage(BaseInteractor interactor, Bitmap bitmap, String imageName) {
        if(bitmap == null) return null;
        Map config = new HashMap();
        config.put("cloud_name", AppConstants.CLOUDINARY_CLOUD_NAME);
        config.put("api_key", AppConstants.CLOUDINARY_API_KEY);
        config.put("api_secret", AppConstants.CLOUDINARY_API_SECRET);
        Cloudinary cloudinary = new Cloudinary(config);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

        try {
            cloudinary.uploader().upload(bs, ObjectUtils.asMap("public_id", imageName));
            String imageUrl = cloudinary.url().generate(imageName + ".jpg");
            ImageUploadResponse resp  = new ImageUploadResponse();
            resp.setBlobKey("");
            resp.setServingUrl(imageUrl);
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
//----------------------------------

        // Upload via AppEngine Endpoint (ImageUploadRequest is a generated model)
        /*ImageUploadRequest image = new ImageUploadRequest();
        image.setImageData(CommonUtils.getByteArrayFromBitmap(bitmap,true).toString());
        image.setImageName(imageName);
        image.setMimeType("image/png");
        ImageUploadResponse response = null;
        try {
            response = blobApi.uploadImage(image).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;*/


    }

    @Override
    public long updateFood(CreateFoodInteractor createFoodInteractor, Food food) {
        try {
            FoodEntity r = foodAPI.update(food.getUid(),responseMapper.getEntityFromFood(food)).execute();
            return r.getUid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public Bitmap fetchBlob(BaseInteractor interactor, String blobKey,String servingUrl) throws MalformedURLException {
        URL imageURL;
        if(!TextUtils.isEmpty(servingUrl)){
            imageURL = new URL(servingUrl);
        } else {
            imageURL = new URL("https://diet-chart-app.appspot.com/blob/serve");
        }

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) imageURL.openConnection();
            connection.setRequestProperty("blob-key",blobKey);
            connection.setRequestProperty("serving-url",servingUrl);
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            Bitmap bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        } finally {

        }
    }

    @Override
    public long updateMeal(BaseInteractor interactor, Meal meal) {
        try {
            mealAPI.update(meal.getUid(),responseMapper.getEntityFromMeal(meal)).execute();
            return meal.getUid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }
    }

    @Override
    public Meal getMealDetails(BaseInteractor interactor, long mealId) {
        return getMealById(interactor,mealId);
    }

    @Override
    public UserLoginResponse login(LoginInteractor loginInteractor, AppUser appUser) {
        try {
            return appUserApi.login(appUser).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(AppErrors.REPOSITORY);
        }

    }

    @Override
    public void insertFoods(BaseInteractor interactor, List<Food> foods) {

    }

    @Override
    public void insertMeals(BaseInteractor interactor, List<Meal> mealList) {

    }

    @Override
    public void insertMealPlans(LoginInteractor loginInteractor, List<MealPlan> mealPlans) {

    }
}
