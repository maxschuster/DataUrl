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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a data url
 * @author Max Schuster
 */
public final class DataUrl implements Serializable {
    
    /**
     * serialVersionUID
     */
    private final static long serialVersionUID = 1L;
    
    /**
     * Payload of this data url
     */
    private final byte[] data;
    
    /**
     * Encoding method
     */
    private final DataUrlEncoding encoding;

    /**
     * MIME-Type of this data urls content
     */
    private final String mimeType;

    /**
     * Headers/parameters of this data url
     */
    private final Map<String, String> headers;

    /**
     * Consturcts a new DataUrl
     * @param data Payload of this data url. Must not be {@code null}
     * @param encoding Encoding method. Must not be {@code null}
     * @param mimeType MIME-Type of this data urls content
     * @param headers Headers/parameters of this data url
     * @throws NullPointerException if data or encoding is {@code null}
     */
    public DataUrl(byte[] data, DataUrlEncoding encoding, String mimeType,
            Map<String, String> headers) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("data is null!");
        } else if (encoding == null) {
            throw new NullPointerException("encoding is null!");
        }
        this.data = data;
        this.encoding = encoding;
        this.mimeType = mimeType;
        if (headers != null) {
            this.headers = Collections.unmodifiableMap(
                    new LinkedHashMap<String, String>(headers));
        } else {
            this.headers = Collections.emptyMap();
        }
    }
    
    /**
     * Consturcts a new DataUrl without headers/parameters
     * @param data Payload of this data url. Must not be {@code null}
     * @param encoding Encoding method. Must not be {@code null}
     * @param mimeType MIME-Type of this data urls content
     * @throws NullPointerException if data or encoding is {@code null}
     */
    public DataUrl(byte[] data, DataUrlEncoding encoding, String mimeType) 
            throws NullPointerException {
        this(data, encoding, mimeType, null);
    }
    
    /**
     * Consturcts a new DataUrl without headers/parameters and MIME-Type
     * @param data Payload of this data url. Must not be {@code null}
     * @param encoding Encoding method. Must not be {@code null}
     * @throws NullPointerException if data or encoding is {@code null}
     */
    public DataUrl(byte[] data, DataUrlEncoding encoding) 
            throws NullPointerException {
        this(data, encoding, null);
    }
    
    /**
     * Consturcts a new DataUrl with URL encoding and without
     * headers/parameters and MIME-Type
     * @param data Payload of this data url. Must not be {@code null}
     * @throws NullPointerException if data is {@code null}
     */
    public DataUrl(byte[] data) 
            throws NullPointerException {
        this(data, DataUrlEncoding.URL);
    }

    /**
     * Gets the payload of this data url
     * @return Payload of this data url
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Gets the MIME-Type of this data urls content.
     * @return MIME-Type of this data urls content or {@code null}.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Gets the encoding method
     * @return Encoding method
     */
    public DataUrlEncoding getEncoding() {
        return encoding;
    }

    /**
     * Gets the headers/parameters of this data url
     * @return Headers/parameters of this data url or {@code null}
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Arrays.hashCode(this.data);
        hash = 97 * hash + (this.mimeType != null ? this.mimeType.hashCode() : 0);
        hash = 97 * hash + (this.encoding != null ? this.encoding.hashCode() : 0);
        hash = 97 * hash + (this.headers != null ? this.headers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataUrl other = (DataUrl) obj;
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        } else if ((this.mimeType == null) ? (other.mimeType != null) : 
                !this.mimeType.equals(other.mimeType)) {
            return false;
        } else if (this.encoding != other.encoding) {
            return false;
        } else if (this.headers != other.headers && (this.headers == null || 
                !this.headers.equals(other.headers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataUrl{ "
                + "mimeType = \"" + mimeType + "\", "
                + "encoding = \"" + encoding + "\", "
                + "headers = \"" + headers + "\", "
                + "data.length = \"" + data.length + " bytes\" }";
    }
    
    
    
}
