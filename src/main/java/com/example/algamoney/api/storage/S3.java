package com.example.algamoney.api.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3 {

	private static final Logger logger = LoggerFactory.getLogger(S3.class);
	private final AlgamoneyApiProperty property;
	private final AmazonS3 amazonS3;

	public String salvarTemporariamente(MultipartFile arquivo) {
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());

		String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());

		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(property.getS3().getBucket(), nomeUnico,
					arquivo.getInputStream(), objectMetadata).withAccessControlList(acl);

			putObjectRequest.setTagging(new ObjectTagging(List.of(new Tag("expirar", "true"))));

			this.amazonS3.putObject(putObjectRequest);

			if (logger.isDebugEnabled()) {
				logger.debug("Arquivo {} enviado com sucesso para o S3.", arquivo.getOriginalFilename());
			}

			return nomeUnico;
		} catch (IOException e) {
			throw new RuntimeException("Problemas ao tentar o arquivo para o S3.", e);
		}
	}

	public void salvar(String objeto) {
		SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(property.getS3().getBucket(),
				objeto, new ObjectTagging(Collections.emptyList()));

		this.amazonS3.setObjectTagging(setObjectTaggingRequest);
	}

	public void remover(String objeto) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(property.getS3().getBucket(), objeto);

		this.amazonS3.deleteObject(deleteObjectRequest);
	}

	public String configurarURL(String objeto) {
		return "\\\\" + property.getS3().getBucket() + ".s3.amazonaws.com/" + objeto;
	}

	public void substituir(String objetoAntigo, String objetoNovo) {
		if (StringUtils.hasText(objetoAntigo)) {
			this.remover(objetoAntigo);
		}

		salvar(objetoNovo);
	}

	private String gerarNomeUnico(String originalFilename) {
		return UUID.randomUUID() + "_" + originalFilename;
	}

}
