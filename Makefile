autotest:
	lein test-refresh :growl

web:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.web

worker:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.worker


uberjar:
	lein uberjar

push-heroku:
	#git push heroku master
	git push heroku add-master:master

open-heroku:
	heroku open

config-heroku:
	heroku addons:create scheduler:standard	

worker-heroku:
	heroku run worker
