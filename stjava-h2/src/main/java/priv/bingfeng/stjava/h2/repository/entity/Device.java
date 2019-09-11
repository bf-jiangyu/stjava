package priv.bingfeng.stjava.h2.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phone_device")
@Data
public class Device {
    @Id
    private String deviceId;
}
