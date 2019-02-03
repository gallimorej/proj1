#
# entry points
#

autotest:
	lein test-refresh :growl

slack:
	lein run

ring:
	#lein ring server-headless
	lein ring server

# run from Heroku

web:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.web

worker:
	java $(JVM_OPTS) -cp target/proj1.jar clojure.main -m proj1.worker


#
# utilities
# 

uberjar:
	lein uberjar

push-heroku:
	# usually you'll want to use this...
	#git push heroku master
	#
	# but when you need to push from non-master branch, use this...
	# 
	git push heroku add-master:master

open-heroku:
	heroku open

config-heroku:
	heroku addons:create scheduler:standard	

worker-heroku:
	heroku run worker
