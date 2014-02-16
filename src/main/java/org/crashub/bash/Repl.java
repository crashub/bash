package org.crashub.bash;

import jline.Terminal;
import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Julien Viet
 */
public class Repl {

  public static void main(String[] args) throws Exception {
    Terminal term = TerminalFactory.create();
    final PrintStream out = System.out;
    PrintStream err = System.err;
    FileInputStream in = new FileInputStream(FileDescriptor.in);
    ConsoleReader reader = new ConsoleReader(null, in, out, term);
    Context context = new Context(new CommandInvoker() {
      @Override
      public Object invoke(String command, List<String> parameters) {
        if ("echo".equals(command)) {
          for (String parameter : parameters) {
            out.println(parameter);
          }
          return null;
        } else {
          throw new UnsupportedOperationException("Command " + command + " not implemented");
        }
      }
    });
    while (true) {
      String s = reader.readLine("> ");
      Script script = new Script(s);
      try {
        Object ret = script.execute(context);
        if (ret != null) {
          out.print("-> ");
          out.println(ret);
        }
      }
      catch (Exception e) {
        e.printStackTrace(err);
      }
    }
  }

}
