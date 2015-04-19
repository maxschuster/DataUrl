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

import java.util.HashMap;
import java.util.Map;

/**
 * Builder to ceate {@link DataUrl} instances
 * @author Max Schuster
 */
public class DataUrlBuilder {
    
    /**
     * Payload of this data url
     */
    private byte[] data;

    /**
     * MIME-Type of this data urls content
     */
    private String mimeType;
    
    /**
     * Encoding method
     */
    private DataUrlEncoding encoding;

    /**
     * Headers/parameters of this data url
     */
    private Map<String, String> headers;
    
    /**
     * Creates a new {@link DataUrl} instance
     * @return New {@link DataUrl} instance
     * @throws NullPointerException if data or encoding is {@code null}
     */
    public DataUrl build() throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("data is null!");
        } else if (encoding == null) {
            throw new NullPointerException("encoding is null!");
        }
        return new DataUrl(data, encoding, mimeType, headers);
    }

    /**
     * Gets the payload of the data url
     * @return Payload of the data url
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the payload of the data url
     * @param data Payload of the data url
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Gets the MIME-Type of the data urls content
     * @return MIME-Type of the data urls content
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the MIME-Type of the data urls content
     * @param mimeType MIME-Type of the data urls content
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    /**
     * Sets the encoding method
     * @return Encoding method
     */
    public DataUrlEncoding getEncoding() {
        return encoding;
    }

    /**
     * Gets the encoding method
     * @param encoding Encoding method
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setEncoding(DataUrlEncoding encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * Get the headers/parameters of the data url
     * @return Headers/parameters of the data url
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Set the headers/parameters of the data url
     * @param headers Headers/parameters of the data url
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    
    /**
     * Shorthand to set a header/parameter
     * @param name Name of the header/parameter
     * @param value Value of the header/parameter
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setHeader(String name, String value) {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put(name, value);
        return this;
    }
    
    /**
     * Shorthand to set the charset header
     * @param charset Charset name
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setCharset(String charset) {
        setHeader("charset", charset);
        return this;
    }
    
    /**
     * Fills this {@link DataUrlBuilder} with the contents of the given
     * {@link DataUrl} template
     * @param template {@link DataUrl} template
     * @return This {@link DataUrlBuilder} instance
     */
    public DataUrlBuilder setDataUrl(DataUrl template) {
        setData(template.getData());
        setEncoding(template.getEncoding());
        setHeaders(template.getHeaders());
        setMimeType(template.getMimeType());
        return this;
    }    
}
