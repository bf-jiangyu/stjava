package priv.bingfeng.stjava.h2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.bingfeng.stjava.common.RunApplication;
import priv.bingfeng.stjava.h2.repository.DeviceRepository;
import priv.bingfeng.stjava.h2.repository.entity.Device;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class Start implements RunApplication {

	@Resource
	private DeviceRepository deviceDao;

	@Override
	public void execute() {
		Device device = new Device();
		device.setDeviceId("123");

		deviceDao.save(device);

		long count = deviceDao.count();
		List<Device> all = deviceDao.findAll();
		System.out.println(all.size());
		for (Device d : all) {
			System.out.println(d.getDeviceId());
		}
	}

	public static void main(String[] args) {
		RunApplication.run(Start.class, args);
	}

}