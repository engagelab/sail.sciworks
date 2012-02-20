import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Ignore;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

	protected HttpPost post;


	private void setup() throws URISyntaxException {
		

	}
    @Ignore
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    
    @Ignore
    public void testThatRenderSceneWorks() {
        Response response = GET("/scenes/renderscene?sceneId=1");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
    
    @Test
    public void playJSONPostick() {
//    	Gson gson = new Gson();
        //MyResourceModel comp = new MyResourceModel(2l,"mycomp",new
//        String body = gson.toJson(comp);
        Response response = POST("/Posticks","application/json","{ author: 'tony' }"); 
        assertIsOk(response);
    }
    
    @Ignore
    public void createJSONPostick() {
    	  try {
        HttpClient httpclient= new DefaultHttpClient();

		post = new HttpPost(new URI("http://localhost:9000/Posticks" ));
		StringEntity se=new StringEntity ("{author: tony}");
        post.setEntity(se);

        System.out.print(se);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

      
		HttpResponse response = httpclient.execute(post);
		System.out.println(response.getStatusLine());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    }
}

//http://stackoverflow.com/questions/5965193/put-method-in-playframework-functionaltest
//private static Map<String, Http.Cookie> lastCookies;
//
//public void login(){
//  String postUrl = Router.reverse("GAEActions.doLogin").url;
//  Map<String, String> map = Maps.newHashMap();
//  map.put("email", "as@gmail.com");
//  map.put("url", "/");
//  map.put("isAdmin", "true");
//  Map<String, File> fileMap = Maps.newHashMap();
//  Response post = POST(postUrl, map, fileMap);
//  lastCookies = post.cookies;
//}
//public void test(){
//  Request request = newRequest();
//  request.cookies = lastCookies;
//  Response post = PUT(request, url,"application/json",json);
//}