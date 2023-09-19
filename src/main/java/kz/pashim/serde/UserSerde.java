package kz.pashim.serde;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserSerde {

    private Integer id;
    private String fullName;
    private String kKey;
    private Long createdDate;
    private Long keyCount;

    public static Serde<UserSerde> userSerde() {
        JsonSerializer<UserSerde> serializer = new JsonSerializer<>();
        JsonDeserializer<UserSerde> deserializer = new JsonDeserializer<>(UserSerde.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static UserSerde capitalizeName(UserSerde userSerde) {
        userSerde.fullName = userSerde.fullName.toUpperCase();
        return userSerde;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getkKey() {
        return kKey;
    }

    public void setkKey(String kKey) {
        this.kKey = kKey;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(Long keyCount) {
        this.keyCount = keyCount;
    }
}
