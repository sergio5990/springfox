/*
 *
 *  Copyright 2015-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.swagger.common;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import static org.springframework.util.StringUtils.*;

public class HostNameProvider {

  public HostNameProvider() {
    throw new UnsupportedOperationException();
  }

  public static UriComponents componentsFrom(
          ServerHttpRequest request,
          String basePath) {

    UriComponentsBuilder builder = fromServletMapping(request, basePath);

    UriComponents components = UriComponentsBuilder.fromPath(request.getPath().value())
        .build();

    String host = components.getHost();
    if (!hasText(host)) {
      return builder.build();
    }

    builder.host(host);
    builder.port(components.getPort());

    return builder.build();
  }

  private static UriComponentsBuilder fromServletMapping(
          ServerHttpRequest request,
          String basePath) {

    UriComponentsBuilder builder = UriComponentsBuilder.fromPath(request.getPath().value());

    XForwardPrefixPathAdjuster adjuster = new XForwardPrefixPathAdjuster(request);
    builder.replacePath(adjuster.adjustedPath(basePath));
    if (hasText(request.getPath().pathWithinApplication().value())) {
      builder.path(request.getPath().contextPath().value());
    }

    return builder;
  }
}
