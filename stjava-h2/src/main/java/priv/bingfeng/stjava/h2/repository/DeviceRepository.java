package priv.bingfeng.stjava.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.bingfeng.stjava.h2.repository.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
