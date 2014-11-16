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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class DefaultDataUrlSerializerRedDotImageTest {
    
    private final String redDot = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUg"
            + "AAAAoAAAAKCAYAAACNMs+9AAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAALEwAACx"
            + "MBAJqcGAAAAAd0SU1FB9YGARc5KB0XV+IAAAAddEVYdENvbW1lbnQAQ3JlYXRlZC"
            + "B3aXRoIFRoZSBHSU1Q72QlbgAAAF1JREFUGNO9zL0NglAAxPEfdLTs4BZM4DIO4C"
            + "7OwQg2JoQ9LE1exdlYvBBeZ7jqch9//q1uH4TLzw4d6+ErXMMcXuHWxId3KOETnn"
            + "XXV6MJpcq2MLaI97CER3N0vr4MkhoXe0rZigAAAABJRU5ErkJggg==";
    
    public DefaultDataUrlSerializerRedDotImageTest() {
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
    public void serialize() throws MalformedURLException {
        InputStream redDotInputStream =
                getClass().getResourceAsStream("red_dot.png");
        ByteArrayOutputStream redDotOutputStream =
                new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        byte[] redDotData;
        int read;
        try {
            while ((read = redDotInputStream.read(buffer)) != -1) {
                redDotOutputStream.write(buffer, 0, read);
            }
            redDotData = redDotOutputStream.toByteArray();
        } catch (IOException e) {
            fail(e.toString());
            return;
        } finally {
            try {
                redDotOutputStream.close();
                if (redDotInputStream != null) {
                    redDotInputStream.close();
                }
            } catch (IOException e2) {
                // Ignore
            }
        }
        
        DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
        DataUrl unserialized = new DataUrlBuilder()
                .setMimeType("image/png")
                .setEncoding(DataUrlEncoding.BASE64)
                .setData(redDotData)
                .build();
        String serialized = serializer.serialize(unserialized);
        
        assertEquals(redDot, serialized);
    }
    
    @Test
    public void unserialize() throws MalformedURLException {
        DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
        DataUrl unserialized = serializer.unserialize(redDot);
        String reserialized = serializer.serialize(unserialized);
        assertEquals(redDot, reserialized);
    }
}
