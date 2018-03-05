package rfbujan.backend.task3.token_rest_service.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import rfbujan.backend.task3.token_rest_service.TokenServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TokenServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenServiceControllerIT
{

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRequestToken() throws JSONException
    {
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);

	ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/token?username=house&password=HOUSE"), HttpMethod.GET, entity,
		String.class);

	String body = response.getBody();
	HttpStatus code = response.getStatusCode();
	
	String body_usertoken = "{\"token\":\"house_";
	String body_tokenStatus= "\"valid\":true}";
	
	assertTrue(body.startsWith(body_usertoken));
	assertTrue(body.endsWith(body_tokenStatus));
	
	checkTimeStampFormat(body, body_usertoken);
	
	
	assertTrue(code.compareTo(HttpStatus.OK)==0);
	
    }

    @Test
    public void testRequestInvalidToken() throws JSONException
    {
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);

	ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/token?username=house&password=house"), HttpMethod.GET, entity,
		String.class);

	String body = response.getBody();
	HttpStatus code = response.getStatusCode();
	
	String body_usertoken = "{\"token\":\"invalid_";
	String body_tokenStatus= "\"valid\":false}";
	
	assertTrue(body.startsWith(body_usertoken));
	assertTrue(body.endsWith(body_tokenStatus));
	
	checkTimeStampFormat(body, body_usertoken);
	
	
	assertTrue(code.compareTo(HttpStatus.OK)==0);
	
    }
    
    private void checkTimeStampFormat(String body, String body_usertoken)
    {
	String dummy_date = "2018-03-05T18:54:34.351Z";
	String token_date= body.substring(body_usertoken.length(), body_usertoken.length()+dummy_date.length());
	try
	{
	    DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;
	    FORMATTER.parse(token_date);

	} catch (DateTimeParseException e)
	{
	    fail("The timestamp used in the usertoken is contains the wrong format");
	}
	
    }

    private String createURLWithPort(String uri)
    {
	return "http://localhost:" + port + uri;
    }
}
