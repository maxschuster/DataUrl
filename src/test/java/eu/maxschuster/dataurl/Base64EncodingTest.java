/*
 * Copyright 2015 Max Schuster.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.maxschuster.dataurl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

/**
 * Test {@link DataUrlSerializer} with {@link DataUrlEncoding#BASE64}
 * @author Max Schuster
 */
public class Base64EncodingTest {
    
    private final IDataUrlSerializer serializer = new DataUrlSerializer();
    
    private final byte[] reddotBinaryData;
    
    private final String reddotTextData;
    
    private final byte[] checkedBinaryData;
    
    private final String checkedTextData;

    public Base64EncodingTest() throws IOException {
        this.reddotBinaryData = loadBinaryData("reddot.png");
        this.reddotTextData = loadTextData("reddot.txt");
        this.checkedBinaryData = loadBinaryData("checked.png");
        this.checkedTextData = loadTextData("checked.txt");
    }
    
    private byte[] loadBinaryData(String name) throws IOException {
        InputStream is = getClass().getResourceAsStream(name);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int r;
        while ((r = is.read(data)) > 0) {
            os.write(data, 0, r);
        }
        return os.toByteArray();
    }
    
    private String loadTextData(String name) throws IOException {
        byte[] data =  loadBinaryData(name);
        return new String(data, "UTF-8");
    }
    
    @Test
    public void reddotSerialize() throws MalformedURLException {
        DataUrl dataUrl = new DataUrlBuilder()
                .setEncoding(DataUrlEncoding.BASE64)
                .setData(reddotBinaryData)
                .setMimeType("image/png")
                .build();
        String serialized = serializer.serialize(dataUrl);
        assertEquals(reddotTextData, serialized);
    }
    
    @Test
    public void reddotUnserialize() throws MalformedURLException {
        DataUrl unserialized = serializer.unserialize(reddotTextData);
        assertArrayEquals(reddotBinaryData, unserialized.getData());
    }
    
    @Test
    public void checkedSerialize() throws MalformedURLException {
        DataUrl dataUrl = new DataUrlBuilder()
                .setEncoding(DataUrlEncoding.BASE64)
                .setData(checkedBinaryData)
                .setMimeType("image/png")
                .build();
        String serialized = serializer.serialize(dataUrl);
        assertEquals(checkedTextData, serialized);
    }
    
    @Test
    public void checkedUnserialize() throws MalformedURLException {
        DataUrl unserialized = serializer.unserialize(checkedTextData);
        assertArrayEquals(checkedBinaryData, unserialized.getData());
    }
    
    @Test(expected = MalformedURLException.class)
    public void wrongProtocol() throws MalformedURLException {
        String corruptedData = reddotTextData.replace("data:", "blabla:");
        serializer.unserialize(corruptedData);
    }
    
    @Test(expected = MalformedURLException.class)
    public void wrongEncoding() throws MalformedURLException {
        String corruptedData = reddotTextData.replace(";base64,", ";phase10,");
        serializer.unserialize(corruptedData);
    }
    
    @Test
    public void compareUnserialized() throws MalformedURLException {
        DataUrl reddotDataUrl = serializer.unserialize(reddotTextData);
        DataUrl checkedDataUrl = serializer.unserialize(checkedTextData);
        assertThat(reddotDataUrl, is(not(checkedDataUrl)));
    }
    
    @Test
    public void compareUnserializedData() throws MalformedURLException {
        DataUrl reddotDataUrl = serializer.unserialize(reddotTextData);
        DataUrl checkedDataUrl = serializer.unserialize(checkedTextData);
        assertThat(reddotDataUrl.getData(), is(not(checkedDataUrl.getData())));
    }
    
    @Test
    public void compareBuild() throws MalformedURLException {
        DataUrlBuilder builder = new DataUrlBuilder()
                .setEncoding(DataUrlEncoding.BASE64)
                .setMimeType("image/png");
        
        builder.setData(reddotBinaryData);
        DataUrl reddotDataUrl = builder.build();
        
        builder.setData(checkedBinaryData);
        DataUrl checkedDataUrl = builder.build();
        assertThat(reddotDataUrl, is(not(checkedDataUrl)));
    }
    
}
