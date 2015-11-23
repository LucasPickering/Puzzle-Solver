package puzzlesolver;

import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class Logger extends FilterOutputStream {

  private final PrintStream delegate;
  private int globalVerbosity;

  public Logger(PrintStream out) {
    this(0, out);
  }

  public Logger(int globalVerbosity, PrintStream out) {
    super(out);
    this.delegate = out;
    this.globalVerbosity = globalVerbosity;
  }

  public int getGlobalVerbosity() {
    return globalVerbosity;
  }

  public void setVerbosity(int verbosity) {
    globalVerbosity = verbosity;
  }

  public void write(int logLevel, int b) {
    if (logLevel <= globalVerbosity) {
      delegate.write(b);
    }
  }

  public void write(int logLevel, byte[] buf, int off, int len) {
    if (logLevel <= globalVerbosity) {
      delegate.write(buf, off, len);
    }
  }

  public void print(int logLevel, boolean b) {
    if (logLevel <= globalVerbosity) {
      delegate.print(b);
    }
  }

  public void print(int logLevel, char c) {
    if (logLevel <= globalVerbosity) {
      delegate.print(c);
    }
  }

  public void print(int logLevel, int i) {
    if (logLevel <= globalVerbosity) {
      delegate.print(i);
    }
  }

  public void print(int logLevel, long l) {
    if (logLevel <= globalVerbosity) {
      delegate.print(l);
    }
  }

  public void print(int logLevel, float f) {
    if (logLevel <= globalVerbosity) {
      delegate.print(f);
    }
  }

  public void print(int logLevel, double d) {
    if (logLevel <= globalVerbosity) {
      delegate.print(d);
    }
  }

  public void print(int logLevel, char[] s) {
    if (logLevel <= globalVerbosity) {
      delegate.print(s);
    }
  }

  public void print(int logLevel, String s) {
    if (logLevel <= globalVerbosity) {
      delegate.print(s);
    }
  }

  public void print(int logLevel, Object obj) {
    if (logLevel <= globalVerbosity) {
      delegate.print(obj);
    }
  }

  public void println(int logLevel) {
    if (logLevel <= globalVerbosity) {
      delegate.println();
    }
  }

  public void println(int logLevel, boolean x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, char x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, int x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, long x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, float x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, double x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, char[] x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, String x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, Object x) {
    if (logLevel <= globalVerbosity) {
      delegate.println(x);
    }
  }

  public PrintStream printf(int logLevel, String format, Object... args) {
    return (logLevel <= globalVerbosity) ? delegate.printf(format, args) : delegate;
  }

  public PrintStream printf(int logLevel, Locale l, String format, Object... args) {
    return (logLevel <= globalVerbosity) ? delegate.printf(l, format, args) : delegate;
  }

  public PrintStream format(int logLevel, String format, Object... args) {
    return (logLevel <= globalVerbosity) ? delegate.format(format, args) : delegate;
  }

  public PrintStream format(int logLevel, Locale l, String format, Object... args) {
    return (logLevel <= globalVerbosity) ? delegate.format(l, format, args) : delegate;
  }

  public PrintStream append(int logLevel, CharSequence csq) {
    return (logLevel <= globalVerbosity) ? delegate.append(csq) : delegate;
  }

  public PrintStream append(int logLevel, CharSequence csq, int start, int end) {
    return (logLevel <= globalVerbosity) ? delegate.append(csq, start, end) : delegate;
  }

  public PrintStream append(int logLevel, char c) {
    return (logLevel <= globalVerbosity) ? delegate.append(c) : delegate;
  }
}
