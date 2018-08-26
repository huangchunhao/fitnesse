package fitnesse.reporting.history;

import fitnesse.FitNesseContext;
import fitnesse.reporting.BaseFormatter;
import fitnesse.wiki.WikiPage;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.xml.sax.SAXException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * add by hch 20161228
 * Format test results as Json  report. This responder returns an alternate
 * format of the test history.
 */
public class JsonReFormatter extends BaseFormatter implements Closeable {
  private final FitNesseContext context;
  private final Writer writer;
  private final SuiteHistoryFormatter historyFormatter;

  public JsonReFormatter(FitNesseContext context, WikiPage page, Writer writer, SuiteHistoryFormatter historyFormatter) {
    super(page);
    this.context = context;
    this.writer = writer;
    this.historyFormatter = historyFormatter;
  }

  @Override
  public void close() throws IOException {
    historyFormatter.close();

    // read file based on historyFormatter time-stamp
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("formatter", this);
    velocityContext.put("suiteExecutionReport", historyFormatter.getSuiteExecutionReport());
    VelocityEngine velocityEngine = context.pageFactory.getVelocityEngine();
    Template template = velocityEngine.getTemplate("suiteJson.vm");
    template.merge(velocityContext, writer);
    writer.close();
  }

  @Override
  public int getErrorCount() {
    return historyFormatter.getErrorCount();
  }

  TestExecutionReport makeTestExecutionReport(File file) throws IOException, SAXException, InvalidReportException {
    return new TestExecutionReport(file);
  }


}

