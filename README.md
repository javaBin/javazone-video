Javazone video mashup site
==========================

Repo for a mashup site for the javazone videos. Our aim is to give some
value-add to the videos themselves with info from the program, speaker bios, 
links to other resources on the same topic, easy sharing of videos, rss feeds and more.

For the techno-heads
----------------------
* Play! framework 1.2.3
* MongoDB+Morphia 1.2.3
* Jackson
* SASS 1.1
* Cobertura for test coverage

Dependencies
--------------

The app uses the following play modules

* Morphia
* cobertura (just for test id)
* sass for awesome style sheets

Run 'play dependencies --sync' to install or run the appropriate rake tasks described below to fix this. 

Building and deploying
-----------------------

There is a rake script for starting play and deploying to test site (heroku) and
production site (TBD) and setting some important shell variables/heroku config in the process. This is done to keep vimeo api key and database credentials out of the public repo. In order for this to work, you need the file jzvideo-secrets.rb. Contact one of the developers if you think you should have this file. This could be done in private repo on github, but until further notice, it's not. 

Tasks:

* rake run
  * Set environment for local, sync play deps and run 'play run'
* rake test
  * same as above but run play in test mode
* deploy_test
  * set heroku config vars and push to heroku
* import_test
  * set local shell vars to point to test (heroku) database and run test (this makes it possible to run the data fetcher tests to seed the database on heroku.
* deploy_prod
  * TBD
* import_prod
  * TBD 

Design mockups
---------------

* Prelimenary page mockups are on google docs on the following urls:
  * front page: https://docs.google.com/drawings/d/1VIyzGEhxpbbk7wLqCetqQepUj7cWTr8HOhETOEBMvXg/edit?hl=en_US
  * video page: https://docs.google.com/drawings/d/1mL2RtVe02dTR2pf5MrgUT-NdtrImC-5K593PuNbdNoY/edit?hl=en_US&pli=1

Request access if you think you need it. 


Demo site
----------
Testing site deployed to javazone-video.herokuapp.com now and then. For now. Production coming soon. 


Troubleshooting mongodb
------------------
On mac if you install mongodb from homebrew you should use 127.0.0.1 and
not localhost as your host. 

Create a new mongodb database and check that it works
install mongodb and start the mongo interactive shell and create the
user 
  use jz-video
  db.addUser("jz-video", "jz-video")
  exit

check that you can do 
  mongo -u jz-video -p jz-video 127.0.0.1/jz-video

Knut Haugen & Bjarte Stien Karlsen 

