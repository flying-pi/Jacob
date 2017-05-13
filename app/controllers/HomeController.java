package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        try {
            URL url = new URL("httlps://brightreads.com/bring-back-home-ec-f72cd8090d12?source=userActivityShare-f166dc4b8883-1494671781");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return badRequest(e.getMessage());
        }

        return ok("Jacob is Cool");
    }


}
