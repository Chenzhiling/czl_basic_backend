package com.czl.console.backend.base.privacy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/11/23
 * Description: 数据脱敏序列化器
 */
@NoArgsConstructor
@AllArgsConstructor
public class PrivacySerializer extends JsonSerializer<String> implements ContextualSerializer {

    // 脱敏类型
    private PrivacyColumnEnum privacyTypeEnum;
    // 前几位不脱敏
    private Integer prefixNoMaskLen;
    // 最后几位不脱敏
    private Integer suffixNoMaskLen;
    // 用什么打码
    private String symbol;

    @Override
    public void serialize(String origin, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (ObjectUtils.isEmpty(origin)) {
            origin = null;
        }
        switch (privacyTypeEnum) {
            case CUSTOM:
                jsonGenerator.writeString(PrivacyUtil.hideValue(origin, prefixNoMaskLen, suffixNoMaskLen, symbol));
                break;
            case PHONE:
                jsonGenerator.writeString(PrivacyUtil.hidePhone(origin));
                break;
            case EMAIL:
                jsonGenerator.writeString(PrivacyUtil.hideEmail(origin));
                break;
            default:
                throw new IllegalArgumentException("unknown privacy type enum " + privacyTypeEnum);
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (null != beanProperty) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Privacy privacyEncrypt = beanProperty.getAnnotation(Privacy.class);
                if (null == privacyEncrypt) {
                    privacyEncrypt = beanProperty.getContextAnnotation(Privacy.class);
                }
                if (null != privacyEncrypt) {
                    return new PrivacySerializer(privacyEncrypt.type(), privacyEncrypt.prefixNoMaskLen(),
                            privacyEncrypt.suffixNoMaskLen(), privacyEncrypt.symbol());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
