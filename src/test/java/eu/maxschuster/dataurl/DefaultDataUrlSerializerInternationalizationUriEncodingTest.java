/*
 * Copyright 2014 Max Schuster
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.maxschuster.dataurl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Max
 */
public class DefaultDataUrlSerializerInternationalizationUriEncodingTest {
    
    private final String testString = "Iñtërnâtiônàlizætiøn";
    
    private final String testStringDataURL = "data:text/plain;charset=utf-8,I%C"
            + "3%B1t%C3%ABrn%C3%A2ti%C3%B4n%C3%A0liz%C3%A6ti%C3%B8n";
    
    public DefaultDataUrlSerializerInternationalizationUriEncodingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void serialize() throws UnsupportedEncodingException, MalformedURLException {
        byte[] data = testString.getBytes("utf-8");
        DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
        DataUrl unserialized = new DataUrlBuilder()
                .setMimeType("text/plain")
                .setCharset("utf-8")
                .setEncoding(DataUrlEncoding.URL)
                .setData(data)
                .build();
        String serialized = serializer.serialize(unserialized);
        assertEquals(testStringDataURL, serialized);
    }
    
    @Test
    public void unserialize() throws MalformedURLException {
        DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
        DataUrl unserialized = serializer.unserialize(testStringDataURL);
        String reserialized;
        reserialized = serializer.serialize(unserialized);
        assertEquals(testStringDataURL, reserialized);
    }
}
