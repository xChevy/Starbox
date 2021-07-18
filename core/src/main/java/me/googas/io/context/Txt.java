package me.googas.io.context;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import lombok.NonNull;
import me.googas.io.StarboxFile;

/**
 * Reads {@link String} from files. {@link #read(StarboxFile, Class)} and {@link #write(StarboxFile,
 * Object)} are not supported yet as there's no way to deserialize or serialize objects for plaint
 * text files
 */
public class Txt implements FileContext<String> {

  /**
   * Reads an String from a {@link BufferedReader}
   *
   * @param reader the reader to read the string from
   * @return the read string or null if it could not be read
   */
  public String read(@NonNull BufferedReader reader) {
    StringBuilder builder = new StringBuilder();
    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.length() == 0 ? null : builder.toString();
  }

  /**
   * Write the {@link String} to the given file
   *
   * @param file the file to write the string on
   * @param string the string to write on the file
   * @param append whether to append the new content to the previous
   * @return whether the string was written in the file successfully
   */
  public boolean write(@NonNull StarboxFile file, @NonNull String string, boolean append) {
    FileWriter writer = file.getPreparedWriter(append);
    try {
      writer.write(string);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    try {
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public @NonNull String read(@NonNull StarboxFile file) {
    if (!file.exists()) return null;
    return this.read(file.getBufferedReader());
  }

  @Override
  public String read(@NonNull URL resource) {
    try {
      return this.read(new BufferedReader(new InputStreamReader(resource.openStream())));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public <T> T read(@NonNull StarboxFile file, @NonNull Class<T> type) {
    if (type.equals(String.class)) {
      return type.cast(this.read(file));
    }
    throw new UnsupportedOperationException("Read has not been implemented for '.txt' files");
  }

  @Override
  public boolean write(@NonNull StarboxFile file, @NonNull Object object) {
    if (object instanceof String) {
      return this.write(file, (String) object, false);
    } else if (object == null) {
      return this.write(file, "null");
    }
    throw new UnsupportedOperationException("Write has not been implemented for '.txt' files");
  }

  @Override
  public <T> T read(@NonNull URL stream, @NonNull Class<T> type) {
    if (type.equals(String.class)) {
      return type.cast(this.read(stream));
    }
    throw new UnsupportedOperationException("Read has not been implemented for '.txt' files");
  }
}