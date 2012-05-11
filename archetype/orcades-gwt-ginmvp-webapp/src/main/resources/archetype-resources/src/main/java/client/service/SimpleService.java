/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ${package}.client.service;

/**
 * An interface for a service that can return random messages and errors.
 */
public interface SimpleService {

  /**
   * Returns a random message string.
   *
   * @return random message
   */
  String getRandomMessage();

  /**
   * Returns a random error string.
   *
   * @return random error
   */
  String getRandomError();
}
