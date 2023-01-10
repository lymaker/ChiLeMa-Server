package icu.agony.clm.request.convert.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import icu.agony.clm.consts.CaptchaType;

import java.io.IOException;

public class CaptchaTypeJsonConvert extends JsonDeserializer<CaptchaType> {

    @Override
    public CaptchaType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString().toUpperCase();
        return CaptchaType.valueOf(value);
    }

}
