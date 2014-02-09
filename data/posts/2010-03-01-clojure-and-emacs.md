---
title: Clojure + Emacs
date: 2010-03-01T00:00:00
tags: clojure, emacs
---

Clojure + Emacs
===============

There are many different ways of providing Emacs with a Clojure REPL for a project.  A few people have asked me how to do this recently, so I thought I'd mention the different methods I've used.

Shell script
------------

You can create a command-line script that runs java, executing the `clojure.main` class with an appropriate classpath set up.  Then add something like [this](http://gist.github.com/318021) to your Emacs lisp.

<script src="https://gist.github.com/319031.js"> </script>

Then from within Emacs you can start up slime by doing `M-- M-x slime`, and then selecting the name for the repl to start up.  I used this method for a while, but it's not very portable, and it requires changing your .emacs setup every time you want to add a repl.  I would not recommend this way.

Build tool
----------

With the advent of [Leiningen](http://github.com/technomancy/leiningen) and the [clojure-maven-plugin](http://github.com/talios/clojure-maven-plugin), it has become much easier to start up a swank server on the command line and then `M-x slime-connect` to it from within Emacs.  If your project uses Leiningen, just type `lein swank` at the command line (you'll need a :dev-dependency on swank-clojure for this to work).  If you're using the Maven plugin, run `mvn clojure:swank`.  Then in Emacs, run `M-x slime-connect`.  Hit enter through the host and port prompts, and it should connect you to the swank server you started up on the command line.

Embedded
--------

If you're working on a long-running application, you may want to consider embedding a swank server in your program itself.  It's remarkably [easy](http://gist.github.com/319031).

<script src="http://gist.github.com/319031.js"> </script>

It took me a long time to realize how great this ability is, but I just had an experience that convinced me of its greatness.  I'm working on a Compojure application that I'm using to help with some performance testing of some web services.  Today I had some runtime errors in the Compojure application.  It had been running for a while and had built up some state that I didn't want to lose by restarting it.  So I just logged into the machine, fired up Emacs and connected to the swank server, diagnosed the problem, and fixed it, all without restarting the server or losing any state.  Now, you obviously need to be careful not to expose a swank server on a port open to the world.  But assuming you take a few safety measures, your application's embedded swank server can save the day.