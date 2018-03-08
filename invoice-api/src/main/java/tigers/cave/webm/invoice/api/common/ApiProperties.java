package tigers.cave.webm.invoice.api.common;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;





@Component
@Getter
@PropertySource("classpath:application-api.properties")
public class ApiProperties {

  @Value("${api.InvoiceListResourceQuery.start.default.value}")
  private String startDefaultValue;

  @Value("${api.InvoiceListResourceQuery.maxCount.default.value}")
  private String maxCountDefaultValue;

  @Value("${api.date.format}")
  private String dateFormat;

  @Value("${api.dateTime.format}")
  private String dateTimeFormat;

  @Value("${api.consumptionTax}")
  private String consumptionTax;
}
