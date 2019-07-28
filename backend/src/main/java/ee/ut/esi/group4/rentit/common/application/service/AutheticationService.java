package ee.ut.esi.group4.rentit.common.application.service;

import ee.ut.esi.group4.rentit.common.domain.ClientAccount;
import ee.ut.esi.group4.rentit.common.domain.ClientAccountRepository;
import org.apache.commons.net.util.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class AutheticationService {


    @Autowired
    ClientAccountRepository clientAccountRepository;

//    public HttpHeaders createHeaders(String username, String password){
//        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(
//                    auth.getBytes(Charset.forName("US-ASCII")) );
//            String authHeader = "Basic " + new String( encodedAuth );
//            set( "Authorization", authHeader );
//        }};
//    }

    public String createBasicAuthString(String username, String password){
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            return "Basic " + new String( encodedAuth );
    }

    public HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String username, String password)
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient(username, password));

        return clientHttpRequestFactory;
    }

    private HttpClient httpClient(String username, String password)
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        HttpClient client = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }

    public ClientAccount getClientIdByName(String currentPrincipalName) {
          return clientAccountRepository.findByUserName(currentPrincipalName.toLowerCase()).orElse(null);
    }


}
