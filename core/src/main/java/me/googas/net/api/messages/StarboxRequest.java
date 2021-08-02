package me.googas.net.api.messages;

import java.util.Map;
import lombok.NonNull;

/** An extension for messages to receive a {@link me.googas.net.api.messages.Response} */
public interface StarboxRequest extends Message {

  /**
   * Get the method of the receptor this request is trying to prepare
   *
   * @return the method as a string
   */
  @NonNull
  String getMethod();

  /**
   * Get the parameters that the receptor needs to give a response
   *
   * @return the parameters
   */
  @NonNull
  Map<String, ?> getParameters();
}
