package org.crashub.bash;

import jline.Terminal;
import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

/**
 * The base repl, useful for interactive testing of the shell.
 *
 * @author Julien Viet
 */
public class Repl {

  public static void main(String[] args) throws Exception {
    Terminal term = TerminalFactory.create();
    final PrintStream out = System.out;
    PrintStream err = System.err;
    FileInputStream in = new FileInputStream(FileDescriptor.in);
    ConsoleReader reader = new ConsoleReader(null, in, out, term);
    Context context = new BaseContext(System.out) {

      @Override
      protected Callable<?> createCommand(String command, final List<String> parameters, final InputStream in, final OutputStream out) {
        final PrintWriter writer = new PrintWriter(out, true);
        if ("echo".equals(command)) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              for (String parameter : parameters) {
                writer.println(parameter);
              }
              return null;
            }
          };
        } if ("grep".equals(command)) {
          final Pattern pattern = Pattern.compile(parameters.get(0));
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              Scanner scanner = new Scanner(in);
              while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (pattern.matcher(line).find()) {
                  writer.println(line);
                }
              }
              return null;
            }
          };
        } else {
          return null;
        }
      }
    };
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
