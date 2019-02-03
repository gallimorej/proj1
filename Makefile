autotest:
	lein test-refresh :growl

web:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.web

worker:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.worker


uberjar:
	lein uberjar
