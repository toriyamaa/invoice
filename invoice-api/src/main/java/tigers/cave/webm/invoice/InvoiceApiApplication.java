package tigers.cave.webm.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"tigers.cave.webm.invoice"})
public class InvoiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApiApplication.class, args);
	}
}
