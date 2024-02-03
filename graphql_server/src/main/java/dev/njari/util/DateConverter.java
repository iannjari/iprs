package dev.njari.util;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * @author njari_mathenge
 * on 03/02/2024.
 * github.com/iannjari
 */
public class DateConverter {

    @Mapper
    public interface StringToProtoDate {

        StringToProtoDate INSTANCE = Mappers.getMapper(StringToProtoDate.class);

        default Timestamp convert(String str) {

            try {
                return Timestamp.newBuilder()
                        .setSeconds(Instant.parse(str).getEpochSecond())
                        .setNanos(Instant.parse(str).getNano())
                        .build();
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Bad timestamp: ".concat(str));
            }
        }

    }

    @Mapper
    public interface ProtoDateToString {

        ProtoDateToString INSTANCE = Mappers.getMapper(ProtoDateToString.class);

        default String convert(Timestamp time) {

            if (time == time.getDefaultInstanceForType()) return "";

            return Instant.ofEpochMilli(time.getNanos()/1000).toString();
        }

    }
}
