package shortbus.ExampleRequest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import shortbus.Mediator;
import shortbus.Response;
import shortbus.TestSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestSetup.class, Bar.class })
public class ExampleTest {

	@Autowired
	Mediator mediator;

	@Test
	public void requestResponse() {
		Response<String> response = mediator.request(new Foo());
		assertThat(response.data).isEqualTo("Bar!");
	}

	@Test
	public void handleNoHandler() {
		Response<String> response = mediator.request(new Ding());
		assertThat(response.hasException()).isTrue();
		assertThat(response.exception).isInstanceOf(Exception.class);
		assertThat(response.exception.getMessage()).isEqualTo("Handler not found. Did you forget to register this?");
	}

	@Test
	public void requestResponseVoid() {
		Response<Void> response = mediator.request(new FooVoid());
		assertThat(response.data).isNull();
	}
}
