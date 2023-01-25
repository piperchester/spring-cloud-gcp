/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.orchestration.airflow.service.v1.spring;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.orchestration.airflow.service.v1.ImageVersionsClient;
import com.google.cloud.orchestration.airflow.service.v1.ImageVersionsSettings;
import com.google.cloud.spring.autoconfigure.core.GcpContextAutoConfiguration;
import com.google.cloud.spring.core.DefaultCredentialsProvider;
import com.google.cloud.spring.core.Retry;
import com.google.cloud.spring.core.util.RetryUtil;
import java.io.IOException;
import java.util.Collections;
import javax.annotation.Generated;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Auto-configuration for {@link ImageVersionsClient}.
 *
 * <p>Provides auto-configuration for Spring Boot
 *
 * <p>The default instance has everything set to sensible defaults:
 *
 * <ul>
 *   <li>The default transport provider is used.
 *   <li>Credentials are acquired automatically through Application Default Credentials.
 *   <li>Retries are configured for idempotent methods but not for non-idempotent methods.
 * </ul>
 */
@Generated("by google-cloud-spring-generator")
@BetaApi("Autogenerated Spring autoconfiguration is not yet stable")
@AutoConfiguration
@AutoConfigureAfter(GcpContextAutoConfiguration.class)
@ConditionalOnClass(ImageVersionsClient.class)
@ConditionalOnProperty(
    value = "com.google.cloud.orchestration.airflow.service.v1.image-versions.enabled",
    matchIfMissing = true)
@EnableConfigurationProperties(ImageVersionsSpringProperties.class)
public class ImageVersionsSpringAutoConfiguration {
  private final ImageVersionsSpringProperties clientProperties;
  private final CredentialsProvider credentialsProvider;
  private static final Log LOGGER = LogFactory.getLog(ImageVersionsSpringAutoConfiguration.class);

  protected ImageVersionsSpringAutoConfiguration(
      ImageVersionsSpringProperties clientProperties, CredentialsProvider credentialsProvider)
      throws IOException {
    this.clientProperties = clientProperties;
    if (this.clientProperties.getCredentials().hasKey()) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Using credentials from ImageVersions-specific configuration");
      }
      this.credentialsProvider =
          ((CredentialsProvider) new DefaultCredentialsProvider(this.clientProperties));
    } else {
      this.credentialsProvider = credentialsProvider;
    }
  }

  /**
   * Provides a default transport channel provider bean. The default is gRPC and will default to it
   * unless the useRest option is provided to use HTTP transport instead
   *
   * @return a default transport channel provider.
   */
  @Bean
  @ConditionalOnMissingBean(name = "defaultImageVersionsTransportChannelProvider")
  public TransportChannelProvider defaultImageVersionsTransportChannelProvider() {
    if (this.clientProperties.getUseRest()) {
      return ImageVersionsSettings.defaultHttpJsonTransportProviderBuilder().build();
    }
    return ImageVersionsSettings.defaultTransportChannelProvider();
  }

  /**
   * Provides a ImageVersionsSettings bean configured to use the default credentials provider
   * (obtained with imageVersionsCredentials()) and its default transport channel provider
   * (defaultImageVersionsTransportChannelProvider()). It also configures the quota project ID if
   * provided. It will configure an executor provider in case there is more than one thread
   * configured in the client
   *
   * <p>Retry settings are also configured from service-level and method-level properties specified
   * in ImageVersionsSpringProperties. Method-level properties will take precedence over
   * service-level properties if available, and client library defaults will be used if neither are
   * specified.
   *
   * @param defaultTransportChannelProvider TransportChannelProvider to use in the settings.
   * @return a {@link ImageVersionsSettings} bean configured with {@link TransportChannelProvider}
   *     bean.
   */
  @Bean
  @ConditionalOnMissingBean
  public ImageVersionsSettings imageVersionsSettings(
      @Qualifier("defaultImageVersionsTransportChannelProvider")
          TransportChannelProvider defaultTransportChannelProvider)
      throws IOException {
    ImageVersionsSettings.Builder clientSettingsBuilder;
    if (this.clientProperties.getUseRest()) {
      clientSettingsBuilder = ImageVersionsSettings.newHttpJsonBuilder();
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Using REST (HTTP/JSON) transport.");
      }
    } else {
      clientSettingsBuilder = ImageVersionsSettings.newBuilder();
    }
    clientSettingsBuilder
        .setCredentialsProvider(this.credentialsProvider)
        .setTransportChannelProvider(defaultTransportChannelProvider)
        .setHeaderProvider(this.userAgentHeaderProvider());
    if (this.clientProperties.getQuotaProjectId() != null) {
      clientSettingsBuilder.setQuotaProjectId(this.clientProperties.getQuotaProjectId());
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(
            "Quota project id set to "
                + this.clientProperties.getQuotaProjectId()
                + ", this overrides project id from credentials.");
      }
    }
    if (this.clientProperties.getExecutorThreadCount() != null) {
      ExecutorProvider executorProvider =
          ImageVersionsSettings.defaultExecutorProviderBuilder()
              .setExecutorThreadCount(this.clientProperties.getExecutorThreadCount())
              .build();
      clientSettingsBuilder.setBackgroundExecutorProvider(executorProvider);
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(
            "Background executor thread count is "
                + this.clientProperties.getExecutorThreadCount());
      }
    }
    Retry serviceRetry = clientProperties.getRetry();
    if (serviceRetry != null) {
      RetrySettings listImageVersionsRetrySettings =
          RetryUtil.updateRetrySettings(
              clientSettingsBuilder.listImageVersionsSettings().getRetrySettings(), serviceRetry);
      clientSettingsBuilder
          .listImageVersionsSettings()
          .setRetrySettings(listImageVersionsRetrySettings);

      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Configured service-level retry settings from properties.");
      }
    }
    Retry listImageVersionsRetry = clientProperties.getListImageVersionsRetry();
    if (listImageVersionsRetry != null) {
      RetrySettings listImageVersionsRetrySettings =
          RetryUtil.updateRetrySettings(
              clientSettingsBuilder.listImageVersionsSettings().getRetrySettings(),
              listImageVersionsRetry);
      clientSettingsBuilder
          .listImageVersionsSettings()
          .setRetrySettings(listImageVersionsRetrySettings);
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(
            "Configured method-level retry settings for listImageVersions from properties.");
      }
    }
    return clientSettingsBuilder.build();
  }

  /**
   * Provides a ImageVersionsClient bean configured with ImageVersionsSettings.
   *
   * @param imageVersionsSettings settings to configure an instance of client bean.
   * @return a {@link ImageVersionsClient} bean configured with {@link ImageVersionsSettings}
   */
  @Bean
  @ConditionalOnMissingBean
  public ImageVersionsClient imageVersionsClient(ImageVersionsSettings imageVersionsSettings)
      throws IOException {
    return ImageVersionsClient.create(imageVersionsSettings);
  }

  private HeaderProvider userAgentHeaderProvider() {
    String springLibrary = "spring-autogen-image-versions";
    String version = this.getClass().getPackage().getImplementationVersion();
    return () -> Collections.singletonMap("user-agent", springLibrary + "/" + version);
  }
}