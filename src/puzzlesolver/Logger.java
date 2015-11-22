package puzzlesolver;

import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class Logger extends FilterOutputStream {

  private final int globalLogLevel;
  private final PrintStream delegate;

  public Logger(int globalLogLevel, PrintStream out) {
    super(out);
    this.delegate = out;
    this.globalLogLevel = globalLogLevel;
  }

  public void write(int logLevel, int b) {
    if (logLevel <= globalLogLevel) {
      delegate.write(b);
    }
  }

  public void write(int logLevel, byte[] buf, int off, int len) {
    if (logLevel <= globalLogLevel) {
      delegate.write(buf, off, len);
    }
  }

  public void print(int logLevel, boolean b) {
    if (logLevel <= globalLogLevel) {
      delegate.print(b);
    }
  }

  public void print(int logLevel, char c) {
    if (logLevel <= globalLogLevel) {
      delegate.print(c);
    }
  }

  public void print(int logLevel, int i) {
    if (logLevel <= globalLogLevel) {
      delegate.print(i);
    }
  }

  public void print(int logLevel, long l) {
    if (logLevel <= globalLogLevel) {
      delegate.print(l);
    }
  }

  public void print(int logLevel, float f) {
    if (logLevel <= globalLogLevel) {
      delegate.print(f);
    }
  }

  public void print(int logLevel, double d) {
    if (logLevel <= globalLogLevel) {
      delegate.print(d);
    }
  }

  public void print(int logLevel, char[] s) {
    if (logLevel <= globalLogLevel) {
      delegate.print(s);
    }
  }

  public void print(int logLevel, String s) {
    if (logLevel <= globalLogLevel) {
      delegate.print(s);
    }
  }

  public void print(int logLevel, Object obj) {
    if (logLevel <= globalLogLevel) {
      delegate.print(obj);
    }
  }

  public void println(int logLevel) {
    if (logLevel <= globalLogLevel) {
      delegate.println();
    }
  }

  public void println(int logLevel, boolean x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, char x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, int x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, long x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, float x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, double x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, char[] x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, String x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public void println(int logLevel, Object x) {
    if (logLevel <= globalLogLevel) {
      delegate.println(x);
    }
  }

  public PrintStream printf(int logLevel, String format, Object... args) {
    return (logLevel <= globalLogLevel) ? delegate.printf(format, args) : delegate;
  }

  public PrintStream printf(int logLevel, Locale l, String format, Object... args) {
    return (logLevel <= globalLogLevel) ? delegate.printf(l, format, args) : delegate;
  }

  public PrintStream format(int logLevel, String format, Object... args) {
    return (logLevel <= globalLogLevel) ? delegate.format(format, args) : delegate;
  }

  public PrintStream format(int logLevel, Locale l, String format, Object... args) {
    return (logLevel <= globalLogLevel) ? delegate.format(l, format, args) : delegate;
  }

  public PrintStream append(int logLevel, CharSequence csq) {
    return (logLevel <= globalLogLevel) ? delegate.append(csq) : delegate;
  }

  public PrintStream append(int logLevel, CharSequence csq, int start, int end) {
    return (logLevel <= globalLogLevel) ? delegate.append(csq, start, end) : delegate;
  }

  public PrintStream append(int logLevel, char c) {
    return (logLevel <= globalLogLevel) ? delegate.append(c) : delegate;
  }
}
