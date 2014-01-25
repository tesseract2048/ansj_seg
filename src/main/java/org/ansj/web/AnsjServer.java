package org.ansj.web;

import javax.servlet.Servlet;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * @author tess3ract <hty0807@gmail.com>
 */
public class AnsjServer {

    private final static String WARM_UP_WORD = "你好";

    private final static int SERVER_MAX_THREADS = 100;

    private final static int SERVER_MIN_THREADS = 10;

    public void startServer(int serverPort, String contextPath)
            throws Exception {
        MyStaticValue.LIBRARYLOG.info("starting ansj http server");

        /* setup server thread pool */
        Server server = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(SERVER_MAX_THREADS);
        threadPool.setMinThreads(SERVER_MIN_THREADS);
        threadPool.setName("AnsjHttpServer");
        server.setThreadPool(threadPool);

        /* setup channel connector */
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(serverPort);
        server.addConnector(connector);

        /* setup servlet and context */
        ServletHolder servletHolder = new ServletHolder(
                (Servlet) new AnsjServlet());
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        ServletContextHandler context = new ServletContextHandler(contexts,
                contextPath);
        context.addServlet(servletHolder, "/segment");

        /* setup handler */
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] {
            contexts
        });
        server.setHandler(handlers);

        /* start up server */
        server.start();
        MyStaticValue.LIBRARYLOG.info("ansj http server started");
        server.join();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: AnsjServer <serverPort> [contextPath]");
            return;
        }
        String contextPath = "";
        if (args.length > 1) {
            contextPath = args[1];
        }
        /* warm up ansj engine */
        /* FIXME: dirty hack here... */
        ToAnalysis.parse(WARM_UP_WORD);
        /* set up server */
        int serverPort = Integer.valueOf(args[0]);
        new AnsjServer().startServer(serverPort, contextPath);
    }
}
