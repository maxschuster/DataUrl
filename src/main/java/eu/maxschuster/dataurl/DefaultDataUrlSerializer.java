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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author Max Schuster
 */
public class DefaultDataUrlSerializer implements DataUrlSerializer {
    
    /**
     * Default instance
     */
    private static final DefaultDataUrlSerializer INSTANCE =
            new DefaultDataUrlSerializer();
    
    /**
     * Pattern used to split header fields;
     */
    private static final Pattern PATTERN_META_SPLIT
            = Pattern.compile(";");
    
    /**
     * Pattern used to check MIME-Types
     */
    private static final Pattern PATTERN_MIMETYPE
            = Pattern.compile("^[a-z\\-0-9]+\\/[a-z\\-0-9]+$");
    
    /**
     * Encoder for {@link DataUrlEncoding#BASE64} encoded {@link DataUrl}s
     */
    private final Base64Encoder base64Encoder = new Base64Encoder();
    
    /**
     * Encoder for {@link DataUrlEncoding#URL} encoded {@link DataUrl}s
     */
    private final URLEncodedEncoder urlEncodedEncoder = new URLEncodedEncoder();
    
    /**
     * Gets the default instance
     * @return Default instance
     */
    public static DefaultDataUrlSerializer get() {
        return INSTANCE;
    }

    @Override
    public String serialize(DataUrl dataURL) throws MalformedURLException {
        DataUrlEncoding encoding = dataURL.getEncoding();
        Encoder encoder = getAppliedEncoder(encoding);
        Map<String, String> headers = dataURL.getHeaders();
        int headerSize = headers != null ? headers.size() : 0;
        StringBuilder sb = new StringBuilder("data:");
        String mimeType = dataURL.getMimeType();

        if (mimeType != null) {
            sb.append(mimeType);
            if (headerSize > 0 || encoding != DataUrlEncoding.URL) {
                sb.append(";");
            }
        }

        if (headers != null && headerSize > 0) {
            int i = 0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String value;
                try {
                    value = URLEncoding.encode(entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError();
                }
                sb.append(entry.getKey()).append('=').append(value);
                i++;
                if (i < headerSize || encoding != DataUrlEncoding.URL) {
                    sb.append(';');
                }
            }
        }
        
        String encodingName = encoding.getEncodingName();

        if (!encodingName.isEmpty()) {
            sb.append(encodingName);
        }

        sb.append(',');
        
        String appliedCharset = getAppliedCharset(headers);
                
        try {
            sb.append(encoder.encode(appliedCharset, dataURL.getData()));
        } catch (Exception e) {
            throw new MalformedURLException("");
        }

        return sb.toString();
    }

    @Override
    public DataUrl unserialize(String urlString) throws MalformedURLException {
        if (urlString == null) {
            throw new NullPointerException();
        }

        byte[] data = null;
        String mimeType = null;
        HashMap<String, String> headers = new HashMap<String, String>();
        

        if (!urlString.startsWith("data:")) {
            throw new MalformedURLException("Wrong protocol");
        }

        int colon = urlString.indexOf(':');
        int comma = urlString.indexOf(',');

        String metaString = urlString.substring(colon + 1, comma);
        String dataString = urlString.substring(comma + 1);
        String encodingName = "";

        String[] metaArray = PATTERN_META_SPLIT.split(metaString);
        for (int i = 0; i < metaArray.length; i++) {
            String meta = metaArray[i];
            if (i == 0) {
                Matcher m = PATTERN_MIMETYPE.matcher(meta);
                if (m.matches()) {
                    mimeType = meta;
                    continue;
                }
            }

            if (i + 1 == metaArray.length) {
                if (meta.indexOf('=') == -1) {
                    encodingName = meta;
                    continue;
                }
            }

            int equals = meta.indexOf('=');
            if (equals < 1) {
                throw new MalformedURLException();
            }

            String name = meta.substring(0, equals);
            String value = meta.substring(equals + 1);

            try {
                headers.put(name, URLEncoding.decode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        
        DataUrlEncoding encoding =
                DataUrlEncoding.valueOfEncodingName(encodingName);
        Encoder encoder = getAppliedEncoder(encoding);
        String appliedCharset = getAppliedCharset(headers);
        
        try {
            data = encoder.decode(appliedCharset, dataString);
        } catch (Exception e) {
            throw new MalformedURLException("");
        }

        DataUrl dataUrl = new DataUrl(data, encoding, mimeType, headers);

        return dataUrl;
    }
    
    /**
     * Gets the charset that should be used to encode the {@link DataUrl}
     * @param headers Headers map
     * @return Applied charset, never {@code null}
     */
    protected String getAppliedCharset(Map<String, String> headers) {
        String encoding;
        if (headers != null && (encoding = headers.get("charset")) != null) {
            return encoding;
        }
        return "US-ASCII"; 
    }
    
    /**
     * Get the matching encoder for the given encoding
     * @param encoding Encoding
     * @return Matching encoder
     */
    protected Encoder getAppliedEncoder(DataUrlEncoding encoding) {
        switch (encoding) {
            case BASE64:
                return base64Encoder;
            case URL:
                return urlEncodedEncoder;
        }
        throw new IllegalArgumentException();
    }
    
    /**
     * A encoder for {@link DataUrl}s
     *
     * @author Max Schuster
     */
    protected interface Encoder {

        /**
         * Decodes the given {@link String}
         *
         * @param charset Charset
         * @param string String to decode
         * @return Decoded data
         * @throws Exception If something goes wrong
         */
        public byte[] decode(String charset, String string) throws Exception;

        /**
         * Encodes the given byte[] of data
         *
         * @param charset Charset
         * @param data String to encode
         * @return Encoded String
         * @throws Exception If something goes wrong
         */
        public String encode(String charset, byte[] data) throws Exception;

    }

    /**
     * Base64 implementation of {@link Encoder}
     *
     * @author Max Schuster
     */
    protected class Base64Encoder implements Encoder {

        @Override
        public byte[] decode(String charset, String string) {
            return DatatypeConverter.parseBase64Binary(string);
        }

        @Override
        public String encode(String charset, byte[] data) {
            return DatatypeConverter.printBase64Binary(data);
        }

    }

    /**
     * URL Encoded implemention of {@link Encoder}
     *
     * @author Max Schuster
     */
    protected class URLEncodedEncoder implements Encoder {

        @Override
        public byte[] decode(String charset, String string) throws Exception {
            return URLEncoding.decode(string, charset).getBytes(charset);
        }

        @Override
        public String encode(String charset, byte[] data) throws Exception {
            return URLEncoding.encode(new String(data, charset), charset);
        }

    }

    /**
     * Encodes/Decodes a {@link String} RFC 3986 compatible
     *
     * @author Max Schuster
     */
    private static class URLEncoding {

        /**
         * Encodes a {@link String}
         *
         * @param s String to encode
         * @param enc Encoding to use
         * @return Encoded {@link String}
         * @throws UnsupportedEncodingException If the named encoding is not
         * supported
         */
        public static String encode(String s, String enc) throws UnsupportedEncodingException {
            return URLEncoder.encode(s, enc).replace("%20", "+");
        }

        /**
         * Decodes a {@link String}
         *
         * @param s String to decode
         * @param enc Encoding to use
         * @return Decoded {@link String}
         * @throws UnsupportedEncodingException If the named encoding is not
         * supported
         */
        public static String decode(String s, String enc) throws UnsupportedEncodingException {
            return URLDecoder.decode(s.replace("+", "%20"), enc);
        }

    }
    
}
