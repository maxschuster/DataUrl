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

/**
 * Supported encodings to serialize/unserialize {@link DataUrl}
 * @author Max Schuster
 */
public enum DataUrlEncoding {

    /**
     * Base64 encoded
     */
    BASE64("base64"),
    
    /**
     * Url encoded
     */
    URL("");

    /**
     * Name of the encoding in the data url
     */
    private final String encodingName;

    DataUrlEncoding(String encodingName) {
        this.encodingName = encodingName;
    }

    /**
     * Gets the name of the encoding in the data url
     * @return Name of the encoding in the data url
     */
    public String getEncodingName() {
        return encodingName;
    }

    /**
     * Gets the matching enum constant of the given enconding
     * @param encodingName Name of the encoding in a data url
     * @return Matching enum constant
     * @throws IllegalArgumentException if this enum type has no constant with
     * the specified encodingName
     * @throws NullPointerException if encodingName is null
     */
    public static final DataUrlEncoding valueOfEncodingName(String encodingName) 
            throws IllegalArgumentException, NullPointerException {
        if (encodingName == null) {
            throw new NullPointerException("encodingName must not be null!");
        }
        DataUrlEncoding[] values = values();
        int size = values.length;
        for (int i = 0; i < size; i++) {
            DataUrlEncoding value = values[i];
            String name = value.getEncodingName();
            if (name.equals(encodingName)) {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }

}
