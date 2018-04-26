package com.example.firstwebapplication.converters;

import org.apache.jena.atlas.web.ContentType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class RdfModelConverter extends AbstractHttpMessageConverter<Model> {
    public RdfModelConverter() {
        super(buildMediaTypeArray());
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return Model.class.isAssignableFrom(aClass);
    }

    @Override
    protected Model readInternal(Class<? extends Model> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        Model model = ModelFactory.createDefaultModel();
        Lang rdfLanguage = mimeTypeToJenaLanguage(httpInputMessage
                .getHeaders().getContentType(), Lang.TURTLE);
        RDFDataMgr.read(model, httpInputMessage.getBody(), "", rdfLanguage);
        return model;
    }

    @Override
    protected void writeInternal(Model model, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Lang rdfLanguage = mimeTypeToJenaLanguage(httpOutputMessage
                .getHeaders().getContentType(), Lang.N3);
        RDFDataMgr.write(httpOutputMessage.getBody(), model, rdfLanguage);
        httpOutputMessage.getBody().flush();
    }

    private static Lang mimeTypeToJenaLanguage(MediaType mediaType,
                                               Lang defaultLanguage) {
        Lang lang = RDFLanguages.contentTypeToLang(mediaType.toString());
        if (lang == null) {
            return defaultLanguage;
        }
        return lang;
    }

    private static MediaType[] buildMediaTypeArray() {
        // now register the media types this converter can handle
        Iterator<Lang> it = RDFLanguages.getRegisteredLanguages().iterator();

        Set<MediaType> mediaTypeSet = new HashSet<MediaType>();

        while (it.hasNext()) {
            Lang lang = it.next();

            ContentType ct = lang.getContentType();
            MediaType mt = new MediaType(ct.getType(), ct.getSubType());
            mediaTypeSet.add(mt);
        }

        return mediaTypeSet.toArray(new MediaType[mediaTypeSet.size()]);
    }
}
