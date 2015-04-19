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

import java.net.MalformedURLException;

/**
 * An interface that indicates that a class is able to serialize/unserialize 
 * RFC 2397 data urls.
 * @author Max Schuster
 */
public interface IDataUrlSerializer {
    
    /**
     * Serialize the given {@link DataUrl} to an RFC 2397 data url 
     * {@link String}.
     * @param dataURL {@link DataUrl} to serialize.
     * @return The serialized RFC 2397 data url {@link String}.
     * @throws MalformedURLException If the given {@link DataUrl} can't be 
     * serialized.
     */
    public String serialize(DataUrl dataURL) throws MalformedURLException;
    
    /**
     * Unserialize the given RFC 2397 data url {@link String} to a 
     * {@link DataUrl}.
     * @param urlString RFC 2397 data url {@link String} to unserialize.
     * @return The unserialized {@link DataUrl}
     * @throws MalformedURLException If the given RFC 2397 data url 
     * {@link String} can't be unserialized.
     */
    public DataUrl unserialize(String urlString) throws MalformedURLException;
    
}
