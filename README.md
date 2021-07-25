# ftp-hog

Inspired by [MailHog](https://github.com/mailhog/MailHog) (which was
inspired by [MailCatcher](https://mailcatcher.me/)) ftp-hog runs a
super simple FTP server suited only for developing (NEVER USE IN
PRODUCTION).

Once running, it will be listening the port 2221 and serving the
folder `/tmp`. Connect to the server as the user `anonymous` with
empty string as the password.

Note: Beside all said before, this project is merly begining to be
developed so no more functionallity is provided.

I'm planning on giving options to the command line.

But my main goal is still pending: I want to provide an excecutable
exactly as MailHog does. To use MailHog you could simply download the
executable and execute it directly: one single file. A security risk,
but super simple. Let's see.

## Usage


Run the project directly, via `:exec-fn`:

    $ clojure -X:run
    [main] INFO org.apache.ftpserver.impl.DefaultFtpServer - FTP server started

Then, from an FTP client you can connect to the server on port 2221:

    $ ftp localhost 2221
	Connected to localhost.
    Service ready for new user.
     (localhost:jgomo3): anonymous # <--- THE USERNAME IS anonymous
    Guest login okay, send your complete e-mail address as password.
    word: # <---------------------------- JUST HIT ENTER (nothing as password)
    User logged in, proceed.
    te system type is UNIX.
     ls
    Command PORT okay.
    File status okay; about to open data connection.
    ------   3 user group            0 Jul 14 20:32 Temp-7a82a302-bb48-4be6-abae-4623cbfedf19
    ------   3 user group            0 Jul 14 22:30 z1024739842342342344
    ------   3 user group            0 Jul 14 22:47 temp.txt
    ------   3 user group            0 Jul 13 12:16 text.tmp


You can specify the port with the `:port` option:

    $ clojure -X:run :port 2222

You can also build the `.jar` and then use plain Java to run it.

To create the file `ftp-hog.jar` run:

    $ clojure -X:uberjar
	
Then, run it with:

    $ java -jar ftp-hog.jar

Or passing the port as first argument (Notice you don't have to say `:port`)

    $ java -jar frp-hog.jar 2222

## License

Copyright © 2021 Jesús Gómez (jgomo3)

Distributed under the Eclipse Public License version 1.0.
