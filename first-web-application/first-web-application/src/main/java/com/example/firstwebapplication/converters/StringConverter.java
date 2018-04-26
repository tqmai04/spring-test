package com.example.firstwebapplication.converters;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class StringConverter extends AbstractHttpMessageConverter<Model> {

    public StringConverter() {
        super(buildMediaTypeArray());
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return Model.class.isAssignableFrom(aClass);
    }

    @Override
    protected Model readInternal(Class<? extends Model> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Model model, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        RDFDataMgr.write(httpOutputMessage.getBody(), model, Lang.NTRIPLES);
        httpOutputMessage.getBody().flush();
    }

    private static MediaType[] buildMediaTypeArray() {
        Set<MediaType> mediaTypeSet = new HashSet<MediaType>();

        MediaType type = new MediaType("text", "plain");
        mediaTypeSet.add(type);

        return mediaTypeSet.toArray(new MediaType[mediaTypeSet.size()]);
    }
}
