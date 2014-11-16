DataUrl
==============
A java library to create and parse RFC 2397 data urls.

## Warning
This Add-on is still under development. The API may change.

## Licence
Apache 2.0

## Usage
### Create/Serialize
``` java
DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
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
DataUrlSerializer serializer = DefaultDataUrlSerializer.get();
String serialized = "data:image/png;base64,...";
DataUrl unserialized = serializer.unserialize(serialized);
byte[] redDotData = unserialized.getData();
```