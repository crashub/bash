package org.crashub.bash;

import org.crashub.bash.spi.BaseContext;

import java.io.ByteArrayOutputStream;

/**
 * @author Julien Viet
 */
public class TestableContext extends BaseContext {

  public TestableContext() {
    super(new ByteArrayOutputStream());
  }
}
