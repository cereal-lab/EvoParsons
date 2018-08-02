package evoparsons.broker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public interface Log {
    public void err(String format, Object... params);
    public default void log(String format, Object... params) {
        this.print(format + "%n", params);
    }
    public void print(String format, Object... params);

    public static final Log console = 
        new Log() {
            @Override
            public void err(String format, Object... params) {
                System.err.printf(format + "%n", params);
            }
            @Override
            public void print(String format, Object... params) {
                System.out.printf(format, params);
            }
        };  
    public static final Log suppress = 
        new Log() {
            @Override
            public void err(String format, Object... params) {
            }
            @Override
            public void print(String format, Object... params) {
            }
        };            
    public class FileLog implements Log, AutoCloseable {
        public final PrintStream out;
        public FileLog(String name) throws FileNotFoundException {
            out = new PrintStream(new File(name));
        }
        @Override
        public void close() throws Exception {
            out.close();
        }

        @Override
        public void err(String format, Object... params) {
            out.printf(format, params);                
        }
        @Override
        public void print(String format, Object... params) {
            out.printf(format, params);                
        }

    }
    public static FileLog file(String name) throws FileNotFoundException {
        return new FileLog(name);
    }            
}