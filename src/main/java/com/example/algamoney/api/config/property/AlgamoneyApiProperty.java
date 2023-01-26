package com.example.algamoney.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("algamoney")
@Getter
@Setter
public class AlgamoneyApiProperty {

	private String originPermitida;

	private final Seguranca seguranca = new Seguranca();

	private final S3 s3 = new S3();

	public S3 getS3() {
		return s3;
	}

	private final Mail mail = new Mail();

	@Getter
	@Setter
	public static class S3 {

		private String accessKeyId;

		private String secretAccessKey;

		private String bucket = "aw-algamoney-arquivos";

	}

	@Getter
	@Setter
	public static class Seguranca {

		private List<String> redirectsPermitidos;
		private String authServerUrl;

	}

	@Getter
	@Setter
	public static class Mail {

		private String host;

		private Integer port;

		private String username;

		private String password;

	}
}