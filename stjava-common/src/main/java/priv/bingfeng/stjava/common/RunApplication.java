package priv.bingfeng.stjava.common;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public interface RunApplication {

    void execute();

    static void run(Class<? extends RunApplication> t, String[] args) {
        try (ConfigurableApplicationContext app = new SpringApplicationBuilder(t).web(WebApplicationType.NONE).run(args)) {
             app.getBean(t).execute();
        }
    }

}
