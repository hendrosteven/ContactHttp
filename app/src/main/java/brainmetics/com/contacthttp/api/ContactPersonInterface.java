package brainmetics.com.contacthttp.api;

import java.util.List;

import brainmetics.com.contacthttp.domain.ContactPerson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hendro Steven on 15/11/2017.
 */

public interface ContactPersonInterface {

    @GET("/contact")
    Call<List<ContactPerson>> findAll();

    @POST("/contact")
    Call<ContactPerson> save(@Body ContactPerson contact);

    @DELETE("/contact/{id}")
    Call<Boolean> delete(@Path("id") String id);

    @GET("/contact/{id}")
    Call<ContactPerson> getById(@Path("id") String id);

}
