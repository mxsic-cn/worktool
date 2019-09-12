package cn.mxsic.mq.topic.util;

public class SampleUtilities {

	static public class DoneLatch {
		boolean done = false;

		public void waitTillDone() {
			synchronized (this) {
				while (!done) {
					try {
						this.wait();
					} catch (InterruptedException ie) {
					}
				}
			}
		}

		public void allDone() {
			synchronized (this) {
				done = true;
				this.notify();
			}
		}

	}
}
