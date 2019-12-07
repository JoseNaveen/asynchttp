package com.joey.testrestasync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.AsyncResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {
	private App myself;
	private BlockingQueue<AsyncResponse> queue = new LinkedBlockingQueue<AsyncResponse>();
	private Thread processorThread;
	private Object msgMonitor = new Object();

	public App() {
		this.myself = this;

	}

	public void enqueueResponse(AsyncResponse resp) {
		synchronized (msgMonitor) {
			this.queue.add(resp);
			msgMonitor.notifyAll();
		}
	}

	public void requestProcessor() {
		class RequestProcessor implements Runnable {

			@Override
			public void run() {
				while (true) {
					if (queue.isEmpty()) {
						synchronized (msgMonitor) {
							try {
								msgMonitor.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						AsyncResponse resp = queue.remove();
						System.out.println(Thread.currentThread().getId() + " processing the response");
						resp.resume("hello world");
					}
					

				}

			}

		}
		this.processorThread = new Thread(new RequestProcessor(), "Processor Thread");
		this.processorThread.start();

	}

	class Binder extends AbstractBinder {

		@Override
		protected void configure() {
			// TODO Auto-generated method stub
			bind(myself).to(App.class);
		}

	}

	public void startServer() {
		Server server = new Server(8080);
		this.requestProcessor();

		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);
		final ResourceConfig application = new ResourceConfig()
				.packages("jersey.config.server.provider.packages", "com.joey.res").register(new Binder());
		ServletHolder serHol = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer(application));
		serHol.setInitOrder(1);
		ctx.addServlet(serHol, "/*");

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.INFO, null, ex);
		} finally {

			server.destroy();
		}
	}

	public static void main(String[] args) {
		App app = new App();
		app.startServer();

	}
}