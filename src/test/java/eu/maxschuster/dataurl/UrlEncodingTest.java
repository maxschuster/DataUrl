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

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test {@link DataUrlSerializer} with {@link DataUrlEncoding#URL}
 * @author Max Schuster
 */
public class UrlEncodingTest {
    
    private final String plain = "Iñtërnâtiônàlizætiøn";
    
    private final String serialized = "data:text/plain;charset=utf-8,I%C3%B1t"
            + "%C3%ABrn%C3%A2ti%C3%B4n%C3%A0liz%C3%A6ti%C3%B8n";
    
    private final IDataUrlSerializer serializer = new DataUrlSerializer();
    
    @Test
    public void serialize() throws IOException {
        DataUrl dataUrl = new DataUrlBuilder()
                .setCharset("utf-8")
                .setEncoding(DataUrlEncoding.URL)
                .setData(plain.getBytes("UTF-8"))
                .setMimeType("text/plain")
                .build();
        String _serialized = serializer.serialize(dataUrl);
        assertEquals(serialized, _serialized);
    }
    
    @Test
    public void unserialize() throws IOException {
        DataUrl dataUrl = serializer.unserialize(serialized);
        String unserialized = new String(dataUrl.getData(), "UTF-8");
        assertEquals(plain, unserialized);
    }
    
}
