package com.sap.citydata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CitydataApplicationTests {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Test
	public void testDatasourceUrl() {
		System.out.println("Datasource URL: " + datasourceUrl);
		// You can assert that the URL contains "MODE=MySQL"
		assert datasourceUrl.contains("MODE=MySQL");
	}

	@Test
	void contextLoads() {
	}

}
