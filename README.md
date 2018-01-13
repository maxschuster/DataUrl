DataUrl
==============
A java library to create and parse RFC 2397 data urls.

[![Javadocs](https://www.javadoc.io/badge/eu.maxschuster/dataurl.svg)](https://www.javadoc.io/doc/eu.maxschuster/dataurl)

## Licence
Apache 2.0

## Requirements
Requires java 8

## Usage
### Create/Serialize
``` java
IDataUrlSerializer serializer = new DataUrlSerializer();
byte[] redDotData = new byte[]{ /* DATA */ };
DataUrl unserialized = new DataUrlBuilder()
    .setMimeType("image/png")
    .setEncoding(DataUrlEncoding.BASE64)
    .setData(redDotData)
    .build();
String serialized = serializer.serialize(unserialized);
System.out.println(serialized);
```

### Parse/Unserialize
``` java
IDataUrlSerializer serializer = new DataUrlSerializer();
String serialized = "data:image/png;base64,...";
DataUrl unserialized = serializer.unserialize(serialized);
byte[] redDotData = unserialized.getData();
```

## Maven
``` xml
<dependency>
    <groupId>eu.maxschuster</groupId>
    <artifactId>dataurl</artifactId>
    <version>2.0.0</version>
</dependency>
```