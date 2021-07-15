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

    $ clojure -X:run-x
    Hello, Clojure!


## License

Copyright © 2021 Jesús Gómez (jgomo3)

Distributed under the Eclipse Public License version 1.0.
