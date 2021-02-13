package org.apache.camel.component.google.storage.integration;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.storage.GoogleCloudStorageComponentOperations;
import org.apache.camel.component.google.storage.GoogleCloudStorageConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProducerIntegrationTest extends CamelTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerIntegrationTest.class);

    @EndpointInject
    private ProducerTemplate template;

    @EndpointInject("mock:result")
    private MockEndpoint result;

    private final String bucketName = "rafa_test_bucket";
    final String serviceAccountKey = "C:\\Users\\rmarc\\Desktop\\LABS\\GCP_Storage\\FunctionExampleProject-c59c4a999d8a.json";

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {

                String endpoint = String.format("google-storage://%s?serviceAccountKey=%s&autoCreateBucket=true", bucketName,
                        serviceAccountKey);
                //String endpoint = String.format("google-storage://%s?autoCreateBucket=true", bucketName);

                from("direct:addObject").to(endpoint);
                from("direct:getObject").to(endpoint);
                from("direct:listBucket").log("-> ${body}").to(endpoint).log("--> ${body}");
                from("direct:listObjects").to(endpoint).log("--> ${body}");
                from("direct:downloadLink").to(endpoint);
                from("direct:deleteObject").to(endpoint);
                from("direct:deleteBucket").to(endpoint).to("mock:result");

            }
        };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void sendIn() throws Exception {
        LOG.info("start sending something");
        result.expectedMessageCount(1);

        final String FILENAME_1 = "just_a_file.txt";

        //upload a file
        byte[] payload = "Hi, How are you ?".getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(payload);
        Exchange addObjectExchange = template.request("direct:addObject", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OBJECT_NAME, FILENAME_1);
            exchange.getIn().setHeader(GoogleCloudStorageConstants.CONTENT_ENCODING, "text/plain");
            exchange.getIn().setBody(bais);
        });
        Blob addObject = addObjectExchange.getMessage().getBody(Blob.class);
        LOG.info("addObject {}", addObject);
        assertNotNull(addObject);
        assertEquals(FILENAME_1, addObject.getName());

        Exchange listBucketsExchange = template.request("direct:listBucket", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION,
                    GoogleCloudStorageComponentOperations.listBuckets);
        });
        List<Bucket> bucketsList = listBucketsExchange.getMessage().getBody(List.class);
        LOG.info("bucketsList {}", bucketsList);
        assertEquals(1, bucketsList.size());

        Exchange listObjectsExchange = template.request("direct:listObjects", exchange -> {
            // exchange.getIn().setHeader(GoogleCloudStorageConstants.BUCKET_NAME, "myBucket"); not needed
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION,
                    GoogleCloudStorageComponentOperations.listObjects);
        });
        LOG.info("listObjectsExchange.body={}", listObjectsExchange.getMessage().getBody());
        List<Blob> resp = listObjectsExchange.getMessage().getBody(List.class);
        assertEquals(1, resp.size());
        assertEquals(FILENAME_1, resp.get(0).getName());
        /*
        Exchange getObjectExchange = template.request("direct:getObject", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION,
                    GoogleCloudStorageComponentOperations.getObject);
            // exchange.getIn().setHeader(GoogleCloudStorageConstants.BUCKET_NAME, "myBucket"); not needed
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OBJECT_NAME, FILENAME_1);
        });
        Blob getObject = getObjectExchange.getMessage().getBody(Blob.class);
        LOG.info("getObject: {}", getObject);
        assertNotNull(getObject);
        assertEquals(FILENAME_1, getObject.getName());
        
        /*
        //sign url
        Exchange downloadLinkExchange = template.request( "direct:downloadLink", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION, GoogleCloudStorageComponentOperations.createDownloadLink);
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OBJECT_NAME, "readme.txt" );
            exchange.getIn().setHeader(GoogleCloudStorageConstants.DOWNLOAD_LINK_EXPIRATION_TIME, 86400000L); //1 day
        });
        URL downloadLink = downloadLinkExchange.getMessage().getBody(URL.class);
        LOG.info("downloadLink {}", downloadLink );
        assertNotNull( downloadLink );
        */
        /*
        Exchange deleteObjectExchange = template.send("direct:deleteObject", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION,
                    GoogleCloudStorageComponentOperations.deleteObject);
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OBJECT_NAME, FILENAME_1);
        });
        
        boolean deleteObject = deleteObjectExchange.getMessage().getBody(Boolean.class).booleanValue();
        LOG.info("deleteObject {}", deleteObject);
        assertTrue(deleteObject);
        
        Exchange deleteBucketExchange = template.send("direct:deleteBucket", exchange -> {
            exchange.getIn().setHeader(GoogleCloudStorageConstants.OPERATION,
                    GoogleCloudStorageComponentOperations.deleteBucket);
        });
        boolean deleteBucket = deleteBucketExchange.getMessage().getBody(Boolean.class).booleanValue();
        LOG.info("deleteBucket {}", deleteBucket);
        assertTrue(deleteBucket);
        
        assertMockEndpointsSatisfied();
        */
    }

}